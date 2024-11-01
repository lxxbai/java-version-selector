package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.model.DownloadVO;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import io.github.lxxbai.javaversionselector.service.DownloadService;
import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;


/**
 * @author lxxbai
 */
@Component
public class DownloadViewModel {

    @Resource
    private DownloadService downloadService;


    private final ObservableList<DownloadVO> downLoadList = FXCollections.observableArrayList();

    /**
     * 获取所有下载信息
     *
     * @return 所有版本信息
     */
    public ObservableList<DownloadVO> getDownLoadList() {
        downLoadList.addAll(downloadService.queryAll());
        return downLoadList;
    }

    /**
     * 获取所有下载信息
     */
    public void refresh() {
        downLoadList.clear();
        downLoadList.addAll(downloadService.queryAll());
    }

    /**
     * 下载
     *
     * @param vo 版本信息
     */
    public void download(JdkVersionVO vo) {
        downloadService.addDownloadRecord(vo);
        refresh();
    }
}