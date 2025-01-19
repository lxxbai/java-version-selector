package io.github.lxxbai.jvs.service;

import cn.hutool.core.collection.CollUtil;
import io.github.lxxbai.jvs.common.enums.InstallStatusEnum;
import io.github.lxxbai.jvs.common.util.ObjectMapperUtil;
import io.github.lxxbai.jvs.datasource.entity.InstallRecordDO;
import io.github.lxxbai.jvs.datasource.entity.JdkVersionDO;
import io.github.lxxbai.jvs.datasource.entity.UserJdkVersionDO;
import io.github.lxxbai.jvs.manager.InstallRecordManager;
import io.github.lxxbai.jvs.manager.JdkVersionManager;
import io.github.lxxbai.jvs.manager.UserJdkVersionManager;
import io.github.lxxbai.jvs.model.JdkVersionVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lxxbai
 */
@Service
public class JavaVersionService {

    @Resource
    private JdkVersionManager jdkVersionManager;

    @Resource
    private InstallRecordManager installRecordManager;

    @Resource
    private UserJdkVersionManager userJdkVersionManager;


    /**
     * 查询所有版本信息
     *
     * @param refresh 是否刷新
     * @return 所有版本信息
     */
    public List<JdkVersionVO> queryAll(boolean refresh) {
        if (refresh) {
            jdkVersionManager.refresh(true);
        }
        //获取所有版本信息
        List<JdkVersionDO> javaVersionList = jdkVersionManager.list();
        System.out.println(ObjectMapperUtil.toJsonString(javaVersionList));
        //获取用户下载的信息
        List<InstallRecordDO> installRecordList = installRecordManager.list();
        //转map
        Map<String, InstallRecordDO> recordMap = CollUtil.toMap(installRecordList, new HashMap<>(), InstallRecordDO::getUkVersion);
        //获取用户版本信息
        List<UserJdkVersionDO> userJavaVersionList = userJdkVersionManager.list();
        //转map
        Map<String, UserJdkVersionDO> userJavaVersionMap = CollUtil.toMap(userJavaVersionList, new HashMap<>(), UserJdkVersionDO::getUkVersion);
        //数据转换
        return javaVersionList.stream().map(v -> {
            JdkVersionVO vo = new JdkVersionVO();
            vo.setVmVendor(v.getVmVendor());
            vo.setMainVersion(v.getMainVersion());
            vo.setJavaVersion(v.getJavaVersion());
            vo.setUkVersion(v.getUkVersion());
            vo.setReleaseDate(v.getReleaseDate());
            vo.setFileName(v.getFileName());
            vo.setFileSize(v.getFileSize());
            vo.setFileType(v.getFileType());
            vo.setCanDownload(v.isCanDownload());
            vo.setDownloadUrl(v.getDownloadUrl());
            //下载状态
            if (userJavaVersionMap.containsKey(v.getUkVersion())) {
                vo.setInstallStatus(InstallStatusEnum.DOWNLOADED);
                return vo;
            }
            if (recordMap.containsKey(v.getUkVersion())) {
                vo.setInstallStatus(InstallStatusEnum.getByStatus(recordMap.get(v.getUkVersion()).getDownloadStatus()));
                return vo;
            }
            vo.setInstallStatus(InstallStatusEnum.NO_DOWNLOADED);
            return vo;
        }).toList();
    }
}
