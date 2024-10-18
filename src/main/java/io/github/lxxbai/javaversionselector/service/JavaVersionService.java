package io.github.lxxbai.javaversionselector.service;

import cn.hutool.core.collection.CollUtil;
import io.github.lxxbai.javaversionselector.common.enums.DownloadStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.ObjectMapperUtil;
import io.github.lxxbai.javaversionselector.common.util.StringUtil;
import io.github.lxxbai.javaversionselector.datasource.entity.DownloadRecordDO;
import io.github.lxxbai.javaversionselector.datasource.entity.JavaVersionDO1;
import io.github.lxxbai.javaversionselector.datasource.entity.UserJavaVersionDO1;
import io.github.lxxbai.javaversionselector.manager.DownloadRecordManager;
import io.github.lxxbai.javaversionselector.manager.JavaVersionManager1;
import io.github.lxxbai.javaversionselector.manager.UserJavaVersionManager;
import io.github.lxxbai.javaversionselector.model.JavaVersionVO;
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
    private JavaVersionManager1 javaVersionManager1;

    @Resource
    private DownloadRecordManager downloadRecordManager;

    @Resource
    private UserJavaVersionManager userJavaVersionManager;


    /**
     * 查询所有版本信息
     *
     * @param refresh 是否刷新
     * @return 所有版本信息
     */
    public List<JavaVersionVO> queryAll(boolean refresh) {
        if (refresh) {
            javaVersionManager1.refresh();
        }
        //获取所有版本信息
        List<JavaVersionDO1> javaVersionList = javaVersionManager1.list();
        System.out.println(ObjectMapperUtil.toJsonString(javaVersionList));
        //获取用户下载的信息
        List<DownloadRecordDO> downloadRecordList = downloadRecordManager.list();
        //转map
        Map<String, DownloadRecordDO> recordMap = CollUtil.toMap(downloadRecordList, new HashMap<>(), DownloadRecordDO::getUkVersion);
        //获取用户版本信息
        List<UserJavaVersionDO1> userJavaVersionList = userJavaVersionManager.list();
        //转map
        Map<String, UserJavaVersionDO1> userJavaVersionMap = CollUtil.toMap(userJavaVersionList, new HashMap<>(), UserJavaVersionDO1::getUkVersion);
        //数据转换
        return javaVersionList.stream().map(v -> {
            JavaVersionVO vo = new JavaVersionVO();
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
    public List<JavaVersionVO> filter(String vmVendor, String mainVersion, String javaVersion) {
        // 查询所有Java版本信息，不进行缓存
        List<JavaVersionVO> javaVersionVOList = queryAll(false);
        // 使用流式操作过滤Java版本信息
        // 过滤条件：虚拟机供应商、主要版本和Java版本任意一个为空或相等
        return javaVersionVOList.stream().filter(v -> StringUtil.isBlankOrEqual(vmVendor, v.getVmVendor())
                && StringUtil.isBlankOrEqual(mainVersion, v.getMainVersion())
                && StringUtil.isBlankOrEqual(javaVersion, v.getJavaVersion())).toList();
    }
}
