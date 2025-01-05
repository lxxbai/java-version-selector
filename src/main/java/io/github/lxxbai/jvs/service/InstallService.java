package io.github.lxxbai.jvs.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.jvs.common.Constants;
import io.github.lxxbai.jvs.common.enums.InstallStatusEnum;
import io.github.lxxbai.jvs.common.exception.ClientException;
import io.github.lxxbai.jvs.datasource.entity.InstallRecordDO;
import io.github.lxxbai.jvs.manager.InstallRecordManager;
import io.github.lxxbai.jvs.manager.UserConfigInfoManager;
import io.github.lxxbai.jvs.model.DownloadConfig;
import io.github.lxxbai.jvs.model.InstallRecordVO;
import io.github.lxxbai.jvs.model.JdkVersionVO;
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
    public InstallRecordVO addDownloadRecord(JdkVersionVO jdkVersionVO) {
        //先查询是否已经存在，存在则更新，不存在则插入
        boolean exists = installRecordManager.lambdaQuery()
                .eq(InstallRecordDO::getUkVersion, jdkVersionVO.getUkVersion())
                .eq(InstallRecordDO::getDownloadStatus, InstallStatusEnum.DOWNLOADING.getStatus())
                .exists();
        if (exists) {
            throw new ClientException("当前版本下载中，请勿重新下载！");
        }
        DownloadConfig downloadConfig = userConfigInfoManager.queryOneConfig(Constants.DOWNLOAD_CONFIG_KEY, DownloadConfig.class);
        if (Objects.isNull(downloadConfig)) {
            throw new ClientException("下载配置不存在");
        }
        return addDownloadRecord(jdkVersionVO, downloadConfig.getJdkSavePath(), downloadConfig.getJdkInstallPath());
    }

    /**
     * 添加下载记录
     *
     * @param jdkVersionVO 版本信息
     */
    public InstallRecordVO addDownloadRecord(JdkVersionVO jdkVersionVO, String jdkSavePath, String jdkInstallPath) {
        //创建下载路径
        FileUtil.mkdir(jdkSavePath);
        FileUtil.mkdir(jdkInstallPath);
        //构建名称
        String fileName = buildFileName(jdkSavePath, jdkVersionVO.getFileName(), 0);
        InstallRecordDO record = new InstallRecordDO();
        record.setFileName(fileName);
        record.setFileSize(jdkVersionVO.getFileSize());
        record.setFileType(jdkVersionVO.getFileType());
        record.setDownloadStatus(InstallStatusEnum.DOWNLOADING.getStatus());
        record.setDownloadUrl(jdkVersionVO.getDownloadUrl());
        record.setDownloadFileFolder(jdkSavePath);
        record.setInstalledFolder(jdkInstallPath);
        //判断文件是否已经存在
        record.setJdkPackageUrl(jdkSavePath.concat(File.separator).concat(fileName));
        record.setDownloadProgress(0.0D);
        record.setCreatedAt(DateUtil.now());
        record.setVmVendor(jdkVersionVO.getVmVendor());
        record.setMainVersion(jdkVersionVO.getMainVersion());
        record.setJavaVersion(jdkVersionVO.getJavaVersion());
        record.setUkVersion(jdkVersionVO.getUkVersion());
        installRecordManager.save(record);
        return converter(record);
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
        String newFileName = fileName;
        if (times > 0) {
            newFileName = StrUtil.replaceLast(fileName, ".", "(" + times + ").");
        }
        String url = downPath.concat(File.separator).concat(newFileName);
        if (FileUtil.exist(url)) {
            return buildFileName(downPath, fileName, times + 1);
        } else {
            return newFileName;
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
        return list.stream().map(this::converter)
                .sorted(Comparator.comparing((x) ->
                        x.getInstallStatus().equals(InstallStatusEnum.DOWNLOADING) ? -1 : 0)).toList();
    }

    /**
     * 数据转换
     *
     * @param record 记录
     * @return 转换后的数据
     */
    private InstallRecordVO converter(InstallRecordDO record) {
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
