package io.github.lxxbai.javaversionselector.service;

import io.github.lxxbai.javaversionselector.common.enums.DownloadStatusEnum;
import io.github.lxxbai.javaversionselector.datasource.entity.DownloadRecordDO;
import io.github.lxxbai.javaversionselector.manager.DownloadRecordManager;
import io.github.lxxbai.javaversionselector.model.DownloadVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @author lxxbai
 */
@Service
public class DownloadService {

    @Resource
    private DownloadRecordManager downloadRecordManager;


    /**
     * 查询所有下载数据
     *
     * @return 所有版本信息
     */
    public List<DownloadVO> queryAll() {
        List<DownloadRecordDO> list = downloadRecordManager.lambdaQuery().list();
        //数据转换，下载中的排在前面
        return list.stream().map(record -> {
            DownloadVO vo = new DownloadVO();
            vo.setVmVendor(record.getVmVendor());
            vo.setMainVersion(record.getMainVersion());
            vo.setJavaVersion(record.getJavaVersion());
            vo.setUkVersion(record.getUkVersion());
            vo.setFileName(record.getFileName());
            vo.setFileSize(record.getFileSize());
            vo.setDownloadUrl(record.getDownloadUrl());
            vo.setDownloadStatus(DownloadStatusEnum.getByStatus(record.getDownloadStatus()));
            vo.setDownloadProgress(record.getDownloadProgress());
            vo.setJdkPathUrl(record.getJdkPathUrl());
            vo.setCreatedAt(record.getCreatedAt());
            vo.setDownloadEndAt(record.getDownloadEndAt());
            vo.setDownloadFileUrl(record.getDownloadFileUrl());
            return vo;
        }).sorted(Comparator.comparing(DownloadVO::getCreatedAt).thenComparing((p1) ->
                p1.getDownloadStatus().equals(DownloadStatusEnum.DOWNLOADING) ? -1 : 0
        )).toList();
    }
}
