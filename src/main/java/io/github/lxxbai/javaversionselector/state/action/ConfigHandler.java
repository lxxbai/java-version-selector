package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.ThreadPoolUtil;
import io.github.lxxbai.javaversionselector.common.util.UserEnvUtil;
import io.github.lxxbai.javaversionselector.event.StatusChangeEvent;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 安装事件处理器
 *
 * @author lxxbai
 */
@Component
public class ConfigHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.CONFIG;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.UNZIPPED};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.CONFIGURING;
    }


    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        //执行配置等操作,需要异步
        ThreadPoolUtil.execute(() -> {
            try {
                UserEnvUtil.addUserEnv(Constants.JAVA_X_HOME.formatted(context.getUserJavaVersion().getVersion()),
                        context.getUserJavaVersion().getUnzipLocalPath());
            } catch (Exception e) {
                //失败
                applicationContext.publishEvent(new StatusChangeEvent(context.getVersion(), VersionActionEnum.FAILURE));
                return;
            }
            applicationContext.publishEvent(new StatusChangeEvent(context.getVersion(), VersionActionEnum.SUCCESS));
        });
    }
}
