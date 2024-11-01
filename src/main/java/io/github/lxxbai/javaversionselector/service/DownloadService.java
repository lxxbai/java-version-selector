package io.github.lxxbai.javaversionselector.service;

import cn.hutool.core.date.DateUtil;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.enums.DownloadStatusEnum;
import io.github.lxxbai.javaversionselector.common.exception.ClientException;
import io.github.lxxbai.javaversionselector.datasource.entity.DownloadRecordDO;
import io.github.lxxbai.javaversionselector.manager.DownloadRecordManager;
import io.github.lxxbai.javaversionselector.manager.UserConfigInfoManager;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import io.github.lxxbai.javaversionselector.model.DownloadVO;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author lxxbai
 */
@Service
public class DownloadService {

    @Resource
    private DownloadRecordManager downloadRecordManager;

    @Resource
    private UserConfigInfoManager userConfigInfoManager;


    /**
     * 添加下载记录
     *
     * @param jdkVersionVO 版本信息
     */
    public void addDownloadRecord(JdkVersionVO jdkVersionVO) {
        //先查询是否已经存在，存在则更新，不存在则插入
        boolean exists = downloadRecordManager.lambdaQuery()
                .eq(DownloadRecordDO::getUkVersion, jdkVersionVO.getUkVersion())
                .eq(DownloadRecordDO::getDownloadStatus, DownloadStatusEnum.DOWNLOADING.getStatus())
                .exists();
        if (exists) {
            return;
        }
        DownloadConfig downloadConfig = userConfigInfoManager.queryOneConfig(Constants.DOWNLOAD_CONFIG_KEY, DownloadConfig.class);
        if (Objects.isNull(downloadConfig)) {
            throw new ClientException("下载配置不存在");
        }
        DownloadRecordDO record = new DownloadRecordDO();
        record.setFileName(jdkVersionVO.getFileName());
        record.setFileSize(jdkVersionVO.getFileSize());
        record.setFileType(jdkVersionVO.getFileType());
        record.setDownloadStatus(DownloadStatusEnum.DOWNLOADING.getStatus());
        record.setDownloadUrl(jdkVersionVO.getDownloadUrl());
        record.setDownloadFileUrl(downloadConfig.getDownloadPath());
        record.setJdkPathUrl(downloadConfig.getJdkPathUrl().concat(jdkVersionVO.getFileName()));
        record.setDownloadProgress(0.0D);
        record.setCreatedAt(DateUtil.now());
        record.setVmVendor(jdkVersionVO.getVmVendor());
        record.setMainVersion(jdkVersionVO.getMainVersion());
        record.setJavaVersion(jdkVersionVO.getJavaVersion());
        record.setUkVersion(jdkVersionVO.getUkVersion());
        downloadRecordManager.save(record);
    }


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
