package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.ActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.datasource.entity.JdkVersionDO;
import io.github.lxxbai.javaversionselector.manager.JdkVersionManager;
import io.github.lxxbai.javaversionselector.state.context.InstallContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 安装事件处理器
 *
 * @author lxxbai
 */
@Component
public class JdkInstallHandler extends AbstractInstallHandler {


    @Resource
    private JdkVersionManager jdkVersionManager;

    @Override
    public ActionEnum event() {
        return ActionEnum.DOWNLOAD;
    }

    @Override
    public InstallStatusEnum[] froms() {
        return new InstallStatusEnum[]{InstallStatusEnum.NO_DOWNLOADED,
                InstallStatusEnum.DOWNLOAD_FAILURE, InstallStatusEnum.DOWNLOADED,
                InstallStatusEnum.INSTALLED_FAILURE, InstallStatusEnum.INSTALLED};
    }

    @Override
    public InstallStatusEnum to() {
        return InstallStatusEnum.DOWNLOADING;
    }

    @Override
    public void execute(InstallStatusEnum from, InstallStatusEnum to, ActionEnum event, InstallContext context) {
        //获取数据
        JdkVersionDO jdkVersion = jdkVersionManager.getById(context.getId());
        //

    }
}
