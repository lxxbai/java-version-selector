package io.github.lxxbai.javaversionselector.service;

import cn.hutool.core.collection.CollUtil;
import io.github.lxxbai.javaversionselector.common.enums.DownloadStatusEnum;
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
}
