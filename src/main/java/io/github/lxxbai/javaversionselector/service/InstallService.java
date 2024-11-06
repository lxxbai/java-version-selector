package io.github.lxxbai.javaversionselector.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.exception.ClientException;
import io.github.lxxbai.javaversionselector.datasource.entity.InstallRecordDO;
import io.github.lxxbai.javaversionselector.manager.InstallRecordManager;
import io.github.lxxbai.javaversionselector.manager.UserConfigInfoManager;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import io.github.lxxbai.javaversionselector.model.InstallRecordVO;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
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
        //创建下载路径
        FileUtil.mkdir(downloadConfig.getDownloadPath());
        FileUtil.mkdir(downloadConfig.getJdkPathUrl());
        InstallRecordDO record = new InstallRecordDO();
        record.setFileName(jdkVersionVO.getFileName());
        record.setFileSize(jdkVersionVO.getFileSize());
        record.setFileType(jdkVersionVO.getFileType());
        record.setDownloadStatus(InstallStatusEnum.DOWNLOADING.getStatus());
        record.setDownloadUrl(jdkVersionVO.getDownloadUrl());
        record.setDownloadFileFolder(downloadConfig.getDownloadPath());
        record.setInstalledFolder(downloadConfig.getJdkPathUrl());
        //判断文件是否已经存在
        record.setJdkPackageUrl(buildFileName(downloadConfig.getDownloadPath(), jdkVersionVO.getFileName(), 0));
        record.setDownloadProgress(0.0D);
        record.setCreatedAt(DateUtil.now());
        record.setVmVendor(jdkVersionVO.getVmVendor());
        record.setMainVersion(jdkVersionVO.getMainVersion());
        record.setJavaVersion(jdkVersionVO.getJavaVersion());
        record.setUkVersion(jdkVersionVO.getUkVersion());
        installRecordManager.save(record);
    }

    /**
     * 构建文件名
     *
     * @param downPath 下载路径
     * @param fileName 文件名
     * @param times    次数
     * @return 文件名
     */
    private String buildFileName(String downPath, String fileName, int times) {
        String url = downPath.concat(File.separator).concat(fileName);
        if (times > 0) {
            url = url.concat("(%d)".formatted(times));
        }
        if (FileUtil.exist(url)) {
            return buildFileName(downPath, fileName, times + 1);
        } else {
            return url;
        }
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
            vo.setFileType(record.getFileType());
            vo.setFileName(record.getFileName());
            vo.setFileSize(record.getFileSize());
            vo.setDownloadUrl(record.getDownloadUrl());
            vo.setInstallStatus(InstallStatusEnum.getByStatus(record.getDownloadStatus()));
            vo.setDownloadProgress(record.getDownloadProgress());
            vo.setJdkPackageUrl(record.getJdkPackageUrl());
            vo.setCreatedAt(record.getCreatedAt());
            vo.setDownloadEndAt(record.getDownloadEndAt());
            vo.setDownloadFileFolder(record.getDownloadFileFolder());
            vo.setInstalledFolder(record.getInstalledFolder());
            vo.setInstalledJavaHome(record.getInstalledJavaHome());
            return vo;
        }).sorted(Comparator.comparing(InstallRecordVO::getCreatedAt).thenComparing((p1) ->
                p1.getInstallStatus().equals(InstallStatusEnum.DOWNLOADING) ? -1 : 0
        )).toList();
    }


    /**
     * 更新下载状态
     *
     * @param installRecord 版本信息
     */
    public void updateDownloadStatus(InstallRecordVO installRecord) {
        InstallRecordDO record = installRecordManager.getById(installRecord.getId());
        record.setDownloadStatus(installRecord.getInstallStatus().getStatus());
        if (installRecord.getInstallStatus().equals(InstallStatusEnum.DOWNLOADED)) {
            record.setDownloadEndAt(DateUtil.now());
        }
        if (installRecord.getInstallStatus().equals(InstallStatusEnum.INSTALLED)) {
            record.setInstalledJavaHome(installRecord.getInstalledJavaHome());
        }
        installRecordManager.updateById(record);
    }

    /**
     * 更新下载状态
     *
     * @param id id
     */
    public void deleteRecord(Integer id) {
        installRecordManager.removeById(id);
    }
}
