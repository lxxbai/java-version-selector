package io.github.lxxbai.javaversionselector.state.action;

import cn.hutool.core.io.FileUtil;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.ThreadPoolUtil;
import io.github.lxxbai.javaversionselector.common.util.UserEnvUtil;
import io.github.lxxbai.javaversionselector.event.StatusChangeEvent;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 卸载事件处理器
 *
 * @author lxxbai
 */
@Component
public class UnInstallHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.UNINSTALL;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.INSTALLED};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.UNINSTALLING;
    }

    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        ThreadPoolUtil.execute(() -> {
            try {
                UserJavaVersion userJavaVersion = context.getUserJavaVersion();
                //环境变量删除
                UserEnvUtil.deleteUserEnv(Constants.STR_JAVA_HOME + userJavaVersion.getVersion());
                if (FileUtil.exist(userJavaVersion.getLocalPath())) {
                    //文件删除
                    FileUtil.del(userJavaVersion.getLocalPath());
                }
            } catch (Exception e) {
                //删除失败
                applicationContext.publishEvent(new StatusChangeEvent(context.getVersion(), VersionActionEnum.FAILURE));
                return;
            }
            //删除成功
            applicationContext.publishEvent(new StatusChangeEvent(context.getVersion(), VersionActionEnum.SUCCESS));
        });
    }
}
