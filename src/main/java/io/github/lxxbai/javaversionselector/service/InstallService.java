package io.github.lxxbai.javaversionselector.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.exception.ClientException;
import io.github.lxxbai.javaversionselector.common.util.AlertUtil;
import io.github.lxxbai.javaversionselector.common.util.DownloadUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import io.github.lxxbai.javaversionselector.component.DownloadProgressBar;
import io.github.lxxbai.javaversionselector.datasource.entity.InstallRecordDO;
import io.github.lxxbai.javaversionselector.manager.InstallRecordManager;
import io.github.lxxbai.javaversionselector.manager.UserConfigInfoManager;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import io.github.lxxbai.javaversionselector.model.InstallRecordVO;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author lxxbai
 */
@Service
public class InstallService {

    @Resource
    private InstallRecordManager installRecordManager;

    @Resource
    private UserConfigInfoManager userConfigInfoManager;


    /**
     * 添加下载记录
     *
     * @param jdkVersionVO 版本信息
     */
    public void addDownloadRecord(JdkVersionVO jdkVersionVO) {
        //先查询是否已经存在，存在则更新，不存在则插入
        boolean exists = installRecordManager.lambdaQuery()
                .eq(InstallRecordDO::getUkVersion, jdkVersionVO.getUkVersion())
                .eq(InstallRecordDO::getDownloadStatus, InstallStatusEnum.DOWNLOADING.getStatus())
                .exists();
        if (exists) {
            return;
        }
        DownloadConfig downloadConfig = userConfigInfoManager.queryOneConfig(Constants.DOWNLOAD_CONFIG_KEY, DownloadConfig.class);
        if (Objects.isNull(downloadConfig)) {
            throw new ClientException("下载配置不存在");
        }
        InstallRecordDO record = new InstallRecordDO();
        record.setFileName(jdkVersionVO.getFileName());
        record.setFileSize(jdkVersionVO.getFileSize());
        record.setFileType(jdkVersionVO.getFileType());
        record.setDownloadStatus(InstallStatusEnum.DOWNLOADING.getStatus());
        record.setDownloadUrl(jdkVersionVO.getDownloadUrl());
        record.setDownloadFileUrl(downloadConfig.getDownloadPath());
        record.setJdkPathUrl(downloadConfig.getJdkPathUrl().concat(File.separator).concat(jdkVersionVO.getFileName()));
        record.setDownloadProgress(0.0D);
        record.setCreatedAt(DateUtil.now());
        record.setVmVendor(jdkVersionVO.getVmVendor());
        record.setMainVersion(jdkVersionVO.getMainVersion());
        record.setJavaVersion(jdkVersionVO.getJavaVersion());
        record.setUkVersion(jdkVersionVO.getUkVersion());
        installRecordManager.save(record);
    }


    /**
     * 查询所有下载数据
     *
     * @return 所有版本信息
     */
    public List<InstallRecordVO> queryAll() {
        List<InstallRecordDO> list = installRecordManager.lambdaQuery().list();
        //数据转换，下载中的排在前面
        return list.stream().map(record -> {
            InstallRecordVO vo = new InstallRecordVO();
            vo.setId(record.getId());
            vo.setVmVendor(record.getVmVendor());
            vo.setMainVersion(record.getMainVersion());
            vo.setJavaVersion(record.getJavaVersion());
            vo.setUkVersion(record.getUkVersion());
            vo.setFileName(record.getFileName());
            vo.setFileSize(record.getFileSize());
            vo.setDownloadUrl(record.getDownloadUrl());
            vo.setDownloadStatus(InstallStatusEnum.getByStatus(record.getDownloadStatus()));
            vo.setDownloadProgress(record.getDownloadProgress());
            vo.setJdkPathUrl(record.getJdkPathUrl());
            vo.setCreatedAt(record.getCreatedAt());
            vo.setDownloadEndAt(record.getDownloadEndAt());
            vo.setDownloadFileUrl(record.getDownloadFileUrl());
            return vo;
        }).sorted(Comparator.comparing(InstallRecordVO::getCreatedAt).thenComparing((p1) ->
                p1.getDownloadStatus().equals(InstallStatusEnum.DOWNLOADING) ? -1 : 0
        )).toList();
    }




    /**
     * 更新下载状态
     *
     * @param installRecord 版本信息
     */
    public void updateDownloadStatus(InstallRecordVO installRecord) {
        InstallRecordDO record = installRecordManager.getById(installRecord.getId());
        record.setDownloadStatus(installRecord.getDownloadStatus().getStatus());
        if (installRecord.getDownloadStatus().equals(InstallStatusEnum.DOWNLOADED)) {
            record.setDownloadEndAt(DateUtil.now());
        }
        installRecordManager.updateById(record);
    }
}
