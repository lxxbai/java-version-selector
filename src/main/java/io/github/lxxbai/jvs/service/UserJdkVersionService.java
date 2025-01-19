package io.github.lxxbai.jvs.service;

import cn.hutool.core.date.DateUtil;
import io.github.lxxbai.jvs.common.enums.ApplyStatusEnum;
import io.github.lxxbai.jvs.common.enums.SourceEnum;
import io.github.lxxbai.jvs.datasource.entity.UserJdkVersionDO;
import io.github.lxxbai.jvs.manager.UserJdkVersionManager;
import io.github.lxxbai.jvs.model.InstallRecordVO;
import io.github.lxxbai.jvs.model.JdkInfoVO;
import io.github.lxxbai.jvs.model.UserJdkVersionVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
     */
    public void saveUserJdk(List<JdkInfoVO> jdkInfoList) {
        List<UserJdkVersionDO> jdkList = new ArrayList<>();
        for (JdkInfoVO jdkInfoVO : jdkInfoList) {
            boolean exists = userJdkVersionManager.exists(jdkInfoVO.getLocalHomePath());
            //校验数据是否存在
            if (exists) {
                continue;
            }
            jdkList.add(getJdkVersionDO(jdkInfoVO));
        }
        userJdkVersionManager.saveBatch(jdkList);
    }

    /**
     * 获取用户版本信息
     *
     * @param jdkInfoVO jdkInfoVO
     * @return 入库对象
     */
    private UserJdkVersionDO getJdkVersionDO(JdkInfoVO jdkInfoVO) {
        UserJdkVersionDO userJdkVersionDO = new UserJdkVersionDO();
        userJdkVersionDO.setLocalHomePath(jdkInfoVO.getLocalHomePath());
        userJdkVersionDO.setStatus(ApplyStatusEnum.NOT_APPLY.getStatus());
        userJdkVersionDO.setCurrent(false);
        userJdkVersionDO.setSource(SourceEnum.LOCAL.getCode());
        userJdkVersionDO.setVmVendor(jdkInfoVO.getVmVendor());
        userJdkVersionDO.setMainVersion(jdkInfoVO.getMainVersion());
        userJdkVersionDO.setJavaVersion(jdkInfoVO.getJavaVersion());
        return userJdkVersionDO;
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
        List<UserJdkVersionDO> jdkList = userJdkVersionManager.lambdaQuery()
                .orderByDesc(UserJdkVersionDO::getId).list();
        return jdkList.stream().map(v -> {
            UserJdkVersionVO vo = new UserJdkVersionVO();
            vo.setId(v.getId());
            vo.setVmVendor(v.getVmVendor());
            vo.setMainVersion(v.getMainVersion());
            vo.setJavaVersion(v.getJavaVersion());
            vo.setUkVersion(v.getUkVersion());
            vo.setLocalHomePath(v.getLocalHomePath());
            vo.setStatus(ApplyStatusEnum.valueOf(v.getStatus()));
            //创建时间是2日内的
            vo.setNewJdk(DateUtil.betweenDay(v.getCreatedAt(), DateUtil.date(), true) <= 2);
            return vo;
        }).sorted(Comparator.comparing((x) -> x.getStatus().equals(ApplyStatusEnum.CURRENT) ? -1 : 0)).toList();
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

    /**
     * 删除记录
     *
     * @param userJdkVersion 版本信息
     */
    public void deleteRecord(UserJdkVersionVO userJdkVersion) {
        userJdkVersionManager.lambdaUpdate()
                .eq(UserJdkVersionDO::getId, userJdkVersion.getId())
                .remove();
    }
}
