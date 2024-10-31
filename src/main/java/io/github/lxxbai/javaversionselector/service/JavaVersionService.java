package io.github.lxxbai.javaversionselector.service;

import cn.hutool.core.collection.CollUtil;
import io.github.lxxbai.javaversionselector.common.enums.DownloadStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.ObjectMapperUtil;
import io.github.lxxbai.javaversionselector.common.util.StringUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.DownloadRecordDO;
import io.github.lxxbai.javaversionselector.datasource.entity.JdkVersionDO;
import io.github.lxxbai.javaversionselector.datasource.entity.UserJdkVersionDO;
import io.github.lxxbai.javaversionselector.manager.DownloadRecordManager;
import io.github.lxxbai.javaversionselector.manager.JdkVersionManager;
import io.github.lxxbai.javaversionselector.manager.UserJdkVersionManager;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
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
    private DownloadRecordManager downloadRecordManager;

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
            jdkVersionManager.refresh();
        }
        //获取所有版本信息
        List<JdkVersionDO> javaVersionList = jdkVersionManager.list();
        System.out.println(ObjectMapperUtil.toJsonString(javaVersionList));
        //获取用户下载的信息
        List<DownloadRecordDO> downloadRecordList = downloadRecordManager.list();
        //转map
        Map<String, DownloadRecordDO> recordMap = CollUtil.toMap(downloadRecordList, new HashMap<>(), DownloadRecordDO::getUkVersion);
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
                vo.setDownloadStatus(DownloadStatusEnum.DOWNLOADED);
                return vo;
            }
            if (recordMap.containsKey(v.getUkVersion())) {
                vo.setDownloadStatus(DownloadStatusEnum.getByStatus(recordMap.get(v.getUkVersion()).getDownloadStatus()));
                return vo;
            }
            vo.setDownloadStatus(DownloadStatusEnum.NO_DOWNLOADING);
            return vo;
        }).toList();
    }


    /**
     * 根据虚拟机供应商、主要版本和Java版本过滤Java版本信息
     *
     * @param vmVendor    虚拟机供应商，如'Oracle Corporation'
     * @param mainVersion Java主要版本，如'1.8'
     * @param javaVersion Java版本，如'1.8.0_231'
     * @return 返回过滤后的Java版本信息列表
     */
    public List<JdkVersionVO> filter(String vmVendor, String mainVersion, String javaVersion) {
        // 查询所有Java版本信息，不进行缓存
        List<JdkVersionVO> jdkVersionVOList = queryAll(false);
        // 使用流式操作过滤Java版本信息
        // 过滤条件：虚拟机供应商、主要版本和Java版本任意一个为空或相等
        return jdkVersionVOList.stream().filter(v -> StringUtil.isBlankOrEqual(vmVendor, v.getVmVendor())
                && StringUtil.isBlankOrEqual(mainVersion, v.getMainVersion())
                && StringUtil.isBlankOrEqual(javaVersion, v.getJavaVersion())).toList();
    }
}
