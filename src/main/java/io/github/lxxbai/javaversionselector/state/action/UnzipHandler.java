package io.github.lxxbai.javaversionselector.state.action;

import cn.hutool.core.util.ZipUtil;
import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.ThreadPoolUtil;
import io.github.lxxbai.javaversionselector.event.StatusChangeEvent;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 安装事件处理器
 *
 * @author lxxbai
 */
@Component
public class UnzipHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.UNZIP;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.DOWNLOADED};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.UNZIPPING;
    }

    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        //执行解压等操作,需要异步
        ThreadPoolUtil.execute(() -> {
            try {
                File zipFile = ZipUtil.unzip(context.getUserJavaVersion().getLocalPath(), "D://Java//jdk"
                        + context.getUserJavaVersion().getVersion());
                //设置解压路径
                String javaHomePath = getJavaHomePath(zipFile);
                //获取解压后的名称
                context.getUserJavaVersion().setUnzipLocalPath(javaHomePath);
            } catch (Exception e) {
                applicationContext.publishEvent(new StatusChangeEvent(context.getVersion(), VersionActionEnum.FAILURE));
                return;
            }
            applicationContext.publishEvent(new StatusChangeEvent(context.getVersion(), VersionActionEnum.SUCCESS));
        });
    }


    /**
     * 获取解压后的路径
     *
     * @param zipFile zip文件
     * @return jdk Home地址
     */
    private String getJavaHomePath(File zipFile) {
        if (zipFile.isDirectory()) {
            //判断是不是JDK目录
            Boolean isHome = isJdkHome(zipFile);
            if (isHome) {
                return zipFile.getPath();
            }
            File[] files = zipFile.listFiles();
            if (files != null && files.length == 1) {
                return getJavaHomePath(files[0]);
            }
            return null;
        }
        return null;
    }


    /**
     * 判断是否是JDK目录
     *
     * @param directory 目录
     * @return 是否是JDK目录
     */
    private Boolean isJdkHome(File directory) {
        if (!directory.isDirectory()) {
            return false;
        }
        // 检查关键子目录
        File binDir = new File(directory, "bin");
        File libDir = new File(directory, "lib");
        File includeDir = new File(directory, "include");
        return binDir.exists() && libDir.exists() && includeDir.exists();
    }
}
