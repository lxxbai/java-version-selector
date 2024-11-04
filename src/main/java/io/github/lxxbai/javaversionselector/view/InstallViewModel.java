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
import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


    private final ObservableList<InstallRecordVO> downLoadList = FXCollections.observableArrayList();


    /**
     * 获取所有下载信息
     *
     * @return 所有版本信息
     */
    public ObservableList<InstallRecordVO> getDownLoadList() {
        downLoadList.addAll(queryAll());
        return downLoadList;
    }


    /**
     * 加载进度条
     *
     * @return 所有版本信息
     */
    public List<InstallRecordVO> queryAll() {
        List<InstallRecordVO> installRecordList = installService.queryAll();
        //添加进度信息
        installRecordList.forEach(vo -> {
            //下载中的
            if (InstallStatusEnum.DOWNLOADING.equals(vo.getInstallStatus()) || InstallStatusEnum.DOWNLOAD_PAUSE.equals(vo.getInstallStatus())) {
                DownloadProgressBar downloadProgressBar = DownloadUtil.get(vo.getUkVersion());
                if (Objects.isNull(downloadProgressBar)) {
                    // 构建下载进度条
                    downloadProgressBar = buildDownloadProgressBar(vo);
                    DownloadUtil.put(vo.getUkVersion(), downloadProgressBar);
                }
                if (InstallStatusEnum.DOWNLOADING.equals(vo.getInstallStatus())) {
                    //判断是否已经开始
                    downloadProgressBar.start();
                }
                long downloadBytes = FileUtil.size(new File(vo.getJdkPathUrl()));
                try {
                    URL url = new URL(vo.getDownloadUrl());
                    long contentLength = URLUtil.getContentLength(url);
                    downloadProgressBar.updateProgress(downloadBytes, contentLength);
                } catch (Exception e) {
                    downloadProgressBar.updateProgress(0, 1);
                }
                vo.setDownloadProgressBar(downloadProgressBar);
            } else {
                //移除
                vo.setDownloadProgressBar(null);
                DownloadUtil.remove(vo.getUkVersion());
            }
        });
        //添加进度等信息
        return installRecordList;
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
        installService.addDownloadRecord(vo);
        refresh();
    }

    /**
     * 修改状态
     *
     * @param installRecordVO 版本信息
     */
    public void changeStatus(InstallRecordVO installRecordVO) {
        installService.updateDownloadStatus(installRecordVO);
        refresh();
        //发送事件
        PublishUtil.publishEvent(new InstallStatusEvent(installRecordVO.getUkVersion(), installRecordVO.getInstallStatus()));
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
        if (FileUtil.exist(installRecordVO.getJdkPathUrl())) {
            FileUtil.del(installRecordVO.getJdkPathUrl());
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
        installService.updateDownloadStatus(installRecordVO);
        refresh();
        //执行解压等操作,需要异步
        ThreadPoolUtil.execute(() -> {
            if (StrUtil.equalsIgnoreCase(installRecordVO.getFileType(), "zip")) {
                zipInstall(installRecordVO);
            } else {
                exeInstall(installRecordVO);
            }
        });
    }

    /**
     * zip安装
     *
     * @param installRecordVO 版本信息
     */
    private void zipInstall(InstallRecordVO installRecordVO) {
        try {
            File zipFile = ZipUtil.unzip(installRecordVO.getDownloadFileUrl(), installRecordVO.getJdkPathUrl());
            //设置解压路径
            String javaHomePath = JdkUtil.getJavaHomePath(zipFile);
            PublishUtil.publishEvent(new InstallEndEvent(javaHomePath, installRecordVO));
        } catch (Exception e) {
            installRecordVO.setInstallStatus(InstallStatusEnum.INSTALLED_FAILURE);
            changeStatus(installRecordVO);
        }
    }

    /**
     * exe安装
     *
     * @param installRecordVO 版本信息
     */
    private void exeInstall(InstallRecordVO installRecordVO) {
        Process exec = RuntimeUtil.exec(installRecordVO.getDownloadFileUrl());
        try {
            int i = exec.waitFor();
            installRecordVO.setInstallStatus(i == 0 ? InstallStatusEnum.INSTALLED : InstallStatusEnum.INSTALLED_FAILURE);
        } catch (Exception e) {
            installRecordVO.setInstallStatus(InstallStatusEnum.INSTALLED_FAILURE);
        }
        changeStatus(installRecordVO);
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
                installRecordVO.getDownloadUrl(), installRecordVO.getDownloadFileUrl(), "");
        //失败
        downloadProgressBar.setOnFailed(event -> {
            AlertUtil.showInfo(StageUtil.getPrimaryStage(), "下载失败", "下载失败", "下载失败");
            installRecordVO.setInstallStatus(InstallStatusEnum.DOWNLOAD_FAILURE);
            //修改状态
            changeStatus(installRecordVO);
        });
        //下载成功
        downloadProgressBar.setOnSucceeded(event -> {
            installRecordVO.setInstallStatus(InstallStatusEnum.DOWNLOADED);
            //修改状态
            changeStatus(installRecordVO);
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