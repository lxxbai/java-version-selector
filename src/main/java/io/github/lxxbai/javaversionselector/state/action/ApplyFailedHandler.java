package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.DialogUtils;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 应用事件处理器
 *
 * @author lxxbai
 */
@Component
public class ApplyFailedHandler extends AbstractVersionHandler {


    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.FAILURE;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.APPLYING};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.INSTALLED;
    }

    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        System.out.println("应用失败");

        //弹框应用失败
        DialogUtils.showErrorDialog("应用失败", "应用失败", "应用失败");
    }
}
