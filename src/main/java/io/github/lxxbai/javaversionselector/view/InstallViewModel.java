package io.github.lxxbai.javaversionselector.view;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.core.util.ZipUtil;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.*;
import io.github.lxxbai.javaversionselector.component.DownloadProgressBar;
import io.github.lxxbai.javaversionselector.event.InstallEndEvent;
import io.github.lxxbai.javaversionselector.event.InstallStatusEvent;
import io.github.lxxbai.javaversionselector.model.InstallRecordVO;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import io.github.lxxbai.javaversionselector.service.InstallService;
import io.github.lxxbai.javaversionselector.service.UserJdkVersionService;
import jakarta.annotation.Resource;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;


/**
 * @author lxxbai
 */
@Component
public class InstallViewModel {

    @Resource
    private InstallService installService;

    @Resource
    private UserJdkVersionService userJdkVersionService;


    @Getter
    private final StringProperty filterJavaVersion = new SimpleStringProperty();

    private final ObservableList<InstallRecordVO> downLoadList = FXCollections.observableArrayList();

    private final SimpleIntegerProperty downloadCount = new SimpleIntegerProperty();

    // 创建 FilteredList
    private final FilteredList<InstallRecordVO> filteredData = new FilteredList<>(downLoadList, p -> true);


    /**
     * 获取所有下载信息
     *
     * @return 所有版本信息
     */
    public ObservableList<InstallRecordVO> getDownLoadList() {
        downLoadList.addListener((ListChangeListener<InstallRecordVO>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    List<? extends InstallRecordVO> addedSubList = change.getAddedSubList();
                    addedSubList.forEach(this::dealInstallProcess);
                }
            }
            refreshDownloadCount();
        });
        downLoadList.addAll(queryAll());
        return filteredData;
    }

    public void refreshDownloadCount() {
        long count = downLoadList.stream().filter(InstallRecordVO::isDownloading).count();
        downloadCount.set((int) count);
    }

    public SimpleIntegerProperty downloadCountProperty() {
        return downloadCount;
    }

    /**
     * 过滤
     */
    public void filter() {
        //过滤的值
        String javaVersionValue = filterJavaVersion.getValue();
        if (StrUtil.isBlank(javaVersionValue)) {
            filteredData.setPredicate(record -> true);
            return;
        }
        filteredData.setPredicate(record -> StrUtil.containsIgnoreCase(record.getJavaVersion(), javaVersionValue));
    }


    /**
     * 处理安装进度
     *
     * @param vo 版本信息
     */
    private void dealInstallProcess(InstallRecordVO vo) {
        if (Objects.isNull(vo)) {
            return;
        }
        InstallStatusEnum installStatus = vo.getInstallStatus();
        switch (installStatus) {
            //下载
            case DOWNLOADING, DOWNLOAD_PAUSE -> dealDownloadProgressBar(vo);
            //安装
            case INSTALLING -> dealInstallingProcess(vo);
        }
    }

    /**
     * 构建下载进度条
     *
     * @param vo 版本信息
     */
    private void dealDownloadProgressBar(InstallRecordVO vo) {
        DownloadProgressBar downloadProgressBar = LocalCacheUtil.getDownloadCache(vo.getUkInstallCode());
        if (Objects.isNull(downloadProgressBar)) {
            // 构建下载进度条
            downloadProgressBar = buildDownloadProgressBar(vo);
            LocalCacheUtil.putDownloadCache(vo.getUkVersion(), downloadProgressBar);
        }
        if (InstallStatusEnum.DOWNLOADING.equals(vo.getInstallStatus())) {
            //判断是否已经开始
            downloadProgressBar.start();
        } else {
            long downloadBytes = FileUtil.size(new File(vo.getJdkPackageUrl()));
            try {
                URL url = new URL(vo.getDownloadUrl());
                long contentLength = URLUtil.getContentLength(url);
                downloadProgressBar.updateProgress(downloadBytes, contentLength);
            } catch (Exception e) {
                downloadProgressBar.updateProgress(0, 1);
            }
        }
        vo.setDownloadProgressBar(downloadProgressBar);
    }


    /**
     * 构建安装进度条
     *
     * @param installRecordVO 版本信息
     */
    private void dealInstallingProcess(InstallRecordVO installRecordVO) {
        Task<Void> task = LocalCacheUtil.getInstallingCache(installRecordVO.getUkInstallCode());
        if (Objects.isNull(task)) {
            task = buildInstallTask(installRecordVO);
        }
        installRecordVO.setInstallTask(task);
        if (task.isRunning()) {
            return;
        }
        //启动
        ThreadPoolUtil.execute(task);
    }


    /**
     * 加载进度条
     *
     * @return 所有版本信息
     */
    public List<InstallRecordVO> queryAll() {
        return installService.queryAll();
    }

    /**
     * 获取所有下载信息
     */
    public void refresh() {
        downLoadList.clear();
        downLoadList.addAll(queryAll());
    }

    /**
     * 下载
     *
     * @param vo 版本信息
     */
    public void download(JdkVersionVO vo) {
        InstallRecordVO installRecordVO = installService.addDownloadRecord(vo);
        if (Objects.isNull(installRecordVO)) {
            JFXAlertUtil.showWarning(StageUtil.getPrimaryStage(), "告警", "当前版本下载中，请勿重新下载！");
            return;
        }
        downLoadList.add(0, installRecordVO);
    }

    /**
     * 修改状态
     *
     * @param installRecordVO 版本信息
     */
    public void changeStatus(InstallRecordVO installRecordVO) {
        installService.updateDownloadStatus(installRecordVO);
        for (int i = 0; i < downLoadList.size(); i++) {
            InstallRecordVO curVo = downLoadList.get(i);
            if (StrUtil.equals(curVo.getUkInstallCode(), installRecordVO.getUkInstallCode())) {
                refreshColumn(i, installRecordVO);
                break;
            }
        }
        refreshDownloadCount();
        //发送事件
        PublishUtil.publishEvent(new InstallStatusEvent(installRecordVO.getUkVersion(),
                installRecordVO.getInstallStatus(), installRecordVO.getInstalledJavaHome()));
    }


    /**
     * 修改状态
     *
     * @param installRecordVO 版本信息
     */
    public void changeStatus(int index, InstallRecordVO installRecordVO) {
        installService.updateDownloadStatus(installRecordVO);
        refreshColumn(index, installRecordVO);
    }

    /**
     * 更新表格数据
     *
     * @param index           下标
     * @param installRecordVO 版本信息
     */
    private void refreshColumn(int index, InstallRecordVO installRecordVO) {
        this.downLoadList.set(index, installRecordVO);
    }

    /**
     * 修改状态
     *
     * @param installRecordVO 版本信息
     */
    public void deleteRecord(int index, InstallRecordVO installRecordVO) {
        installService.deleteRecord(installRecordVO.getId());
        //移除
        if (FileUtil.exist(installRecordVO.getJdkPackageUrl())) {
            FileUtil.del(installRecordVO.getJdkPackageUrl());
        }
        this.downLoadList.remove(index);
    }


    /**
     * 开始安装
     *
     * @param installRecordVO 版本信息
     */
    public void install(InstallRecordVO installRecordVO) {
        installRecordVO.setInstallStatus(InstallStatusEnum.INSTALLING);
        changeStatus(installRecordVO);
    }

    /**
     * zip安装
     *
     * @param installRecordVO 版本信息
     */
    private void zipInstall(InstallRecordVO installRecordVO) {
        try {
            //设置解压路径
            String installPath = installRecordVO.getInstalledFolder() + File.separator
                    + installRecordVO.getVmVendor() + File.separator + installRecordVO.getJavaVersion();
            File zipFile = ZipUtil.unzip(installRecordVO.getJdkPackageUrl(), installPath);
            //设置解压路径
            String javaHomePath = JdkUtil.getJavaHomePath(zipFile);
            installRecordVO.setInstalledJavaHome(javaHomePath);
            installRecordVO.setInstallStatus(InstallStatusEnum.INSTALLED);
            //发送事件,通知已安装
            PublishUtil.publishEvent(new InstallEndEvent(javaHomePath, installRecordVO));
        } catch (Exception e) {
            installRecordVO.setInstallStatus(InstallStatusEnum.INSTALLED_FAILURE);
        }
        changeStatus(installRecordVO);
    }

    /**
     * exe安装
     *
     * @param installRecordVO 版本信息
     */
    private void exeInstall(InstallRecordVO installRecordVO) {
        //校验是否安装成功 todo
        Process exec = RuntimeUtil.exec(installRecordVO.getJdkPackageUrl());
        try {
            int i = exec.waitFor();
            installRecordVO.setInstallStatus(i == 0 ? InstallStatusEnum.INSTALLED : InstallStatusEnum.INSTALLED_FAILURE);
        } catch (Exception e) {
            installRecordVO.setInstallStatus(InstallStatusEnum.INSTALLED_FAILURE);
        }
        changeStatus(installRecordVO);
    }


    /**
     * 构建安装任务
     *
     * @param vo 版本信息
     */
    private Task<Void> buildInstallTask(InstallRecordVO vo) {
        return new Task<>() {
            @Override
            protected Void call() {
                if (StrUtil.equalsIgnoreCase(vo.getFileType(), "zip")) {
                    zipInstall(vo);
                } else {
                    exeInstall(vo);
                }
                return null;
            }
        };
    }


    /**
     * 构建下载进度条
     *
     * @param installRecordVO 版本信息
     * @return 下载进度条
     */
    private DownloadProgressBar buildDownloadProgressBar(InstallRecordVO installRecordVO) {
        //获取下载地址
        DownloadProgressBar downloadProgressBar = new DownloadProgressBar(80, 2,
                installRecordVO.getDownloadUrl(), installRecordVO.getJdkPackageUrl(), "");
        //失败
        downloadProgressBar.setOnFailed(event -> {
            JFXAlertUtil.showError(StageUtil.getPrimaryStage(), "失败", "下载失败！");
            installRecordVO.setInstallStatus(InstallStatusEnum.DOWNLOAD_FAILURE);
            //修改状态
            changeStatus(installRecordVO);
        });
        //下载成功
        downloadProgressBar.setOnSucceeded(event -> {
            installRecordVO.setInstallStatus(InstallStatusEnum.DOWNLOADED);
            //安装
            install(installRecordVO);
        });
        //当做暂停
        downloadProgressBar.setOnCancelled(event -> {
            installRecordVO.setInstallStatus(InstallStatusEnum.DOWNLOAD_PAUSE);
            //修改状态
            changeStatus(installRecordVO);
        });
        return downloadProgressBar;
    }
}