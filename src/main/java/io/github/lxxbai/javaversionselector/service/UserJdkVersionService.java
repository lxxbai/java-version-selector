package io.github.lxxbai.javaversionselector.service;

import io.github.lxxbai.javaversionselector.common.enums.ApplyStatusEnum;
import io.github.lxxbai.javaversionselector.common.enums.SourceEnum;
import io.github.lxxbai.javaversionselector.datasource.entity.UserJdkVersionDO;
import io.github.lxxbai.javaversionselector.manager.UserJdkVersionManager;
import io.github.lxxbai.javaversionselector.model.InstallRecordVO;
import io.github.lxxbai.javaversionselector.model.UserJdkVersionVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户版本管理服务
 *
 * @author lxxbai
 */
@Service
public class UserJdkVersionService {

    @Resource
    private UserJdkVersionManager userJdkVersionManager;


    /**
     * 判断版本是否存在
     *
     * @param ukVersion 版本号
     * @return 是否存在
     */
    public boolean versionExists(String ukVersion) {
        return userJdkVersionManager.lambdaQuery()
                .eq(UserJdkVersionDO::getUkVersion, ukVersion)
                .exists();
    }


    /**
     * 保存用户版本信息
     *
     * @param javaHome 路径
     */
    public void saveUserJdk(String javaHome) {
        userJdkVersionManager.buildUserJdk(javaHome);
    }

    /**
     * 保存用户版本信息
     *
     * @param installRecordVO 安装记录
     */
    public void addUserJdk(InstallRecordVO installRecordVO) {
        boolean exists = userJdkVersionManager.lambdaQuery()
                .eq(UserJdkVersionDO::getLocalHomePath, installRecordVO.getInstalledJavaHome())
                .exists();
        //校验数据是否存在
        if (exists) {
            return;
        }
        UserJdkVersionDO userJdkVersionDO = new UserJdkVersionDO();
        userJdkVersionDO.setLocalHomePath(installRecordVO.getInstalledJavaHome());
        userJdkVersionDO.setStatus(ApplyStatusEnum.NOT_APPLY.getStatus());
        userJdkVersionDO.setCurrent(false);
        userJdkVersionDO.setSource(SourceEnum.PLATFORM.getCode());
        userJdkVersionDO.setVmVendor(installRecordVO.getVmVendor());
        userJdkVersionDO.setMainVersion(installRecordVO.getMainVersion());
        userJdkVersionDO.setJavaVersion(installRecordVO.getJavaVersion());
        userJdkVersionManager.save(userJdkVersionDO);
    }


    /**
     * 查询所有
     *
     * @return List
     */
    public List<UserJdkVersionVO> queryAll() {
        List<UserJdkVersionDO> jdkList = userJdkVersionManager.list();
        return jdkList.stream().map(v -> {
            UserJdkVersionVO vo = new UserJdkVersionVO();
            vo.setId(v.getId());
            vo.setVmVendor(v.getVmVendor());
            vo.setMainVersion(v.getMainVersion());
            vo.setJavaVersion(v.getJavaVersion());
            vo.setUkVersion(v.getUkVersion());
            vo.setLocalHomePath(v.getLocalHomePath());
            vo.setStatus(ApplyStatusEnum.valueOf(v.getStatus()));
            return vo;
        }).toList();
    }


    /**
     * 更新状态
     *
     * @param jdkVersionVO 版本信息
     */
    public void updateStatus(UserJdkVersionVO jdkVersionVO) {
        userJdkVersionManager.lambdaUpdate()
                .eq(UserJdkVersionDO::getId, jdkVersionVO.getId())
                .set(UserJdkVersionDO::getStatus, jdkVersionVO.getStatus());
    }
}
