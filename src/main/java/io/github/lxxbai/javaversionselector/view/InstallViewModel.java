package io.github.lxxbai.javaversionselector.view;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.AlertUtil;
import io.github.lxxbai.javaversionselector.common.util.DownloadUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import io.github.lxxbai.javaversionselector.component.DownloadProgressBar;
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
            if (InstallStatusEnum.DOWNLOADING.equals(vo.getDownloadStatus()) || InstallStatusEnum.DOWNLOAD_PAUSE.equals(vo.getDownloadStatus())) {
                DownloadProgressBar downloadProgressBar = DownloadUtil.get(vo.getUkVersion());
                if (Objects.isNull(downloadProgressBar)) {
                    // 构建下载进度条
                    downloadProgressBar = buildDownloadProgressBar(vo);
                    DownloadUtil.put(vo.getUkVersion(), downloadProgressBar);
                }
                if (InstallStatusEnum.DOWNLOADING.equals(vo.getDownloadStatus())) {
                    downloadProgressBar.start();
                }
                long downloadBytes = FileUtil.size(new File(vo.getDownloadFileUrl()));
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
        //通知版本列表完成刷新 todo
        refresh();
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
            installRecordVO.setDownloadStatus(InstallStatusEnum.DOWNLOAD_FAILURE);
            //修改状态
            changeStatus(installRecordVO);
        });
        //下载成功
        downloadProgressBar.setOnSucceeded(event -> {
            installRecordVO.setDownloadStatus(InstallStatusEnum.DOWNLOADED);
            //修改状态
            changeStatus(installRecordVO);
        });
        //当做暂停
        downloadProgressBar.setOnCancelled(event -> {
            installRecordVO.setDownloadStatus(InstallStatusEnum.DOWNLOAD_PAUSE);
            //修改状态
            changeStatus(installRecordVO);
        });
        return downloadProgressBar;
    }
}