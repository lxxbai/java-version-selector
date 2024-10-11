package io.github.lxxbai.javaversionselector.state.action;

import cn.hutool.core.thread.ThreadUtil;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.UserEnvUtil;
import io.github.lxxbai.javaversionselector.event.StatusChangeEvent;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 应用事件处理器
 *
 * @author lxxbai
 */
@Component
public class ApplyHandler extends AbstractVersionHandler {

    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.APPLY;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.INSTALLED};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.APPLYING;
    }


    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        //异步修改环境配置
        ThreadUtil.execute(() -> {
            try {
                //更新地址
                UserEnvUtil.updateUserEnv(Constants.STR_JAVA_HOME, context.getUserJavaVersion().getLocalPath());
                context.getUserJavaVersion().setCurrent(true);
            } catch (Exception e) {
                applicationContext.publishEvent(new StatusChangeEvent(context.getVersion(), VersionActionEnum.FAILURE));
                return;
            }
            applicationContext.publishEvent(new StatusChangeEvent(context.getVersion(), VersionActionEnum.SUCCESS));
        });
    }
}
