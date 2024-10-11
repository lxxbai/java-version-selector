
package io.github.lxxbai.javaversionselector.state.action;

import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.state.context.VersionContext;
import org.springframework.stereotype.Component;

/**
 * 应用事件处理器
 *
 * @author lxxbai
 */
@Component
public class ApplySuccessHandler extends AbstractVersionHandler {


    @Override
    public VersionActionEnum event() {
        return VersionActionEnum.SUCCESS;
    }

    @Override
    public VersionStatusEnum[] froms() {
        return new VersionStatusEnum[]{VersionStatusEnum.APPLYING};
    }

    @Override
    public VersionStatusEnum to() {
        return VersionStatusEnum.CURRENT;
    }

    @Override
    public void execute(VersionStatusEnum from, VersionStatusEnum to, VersionActionEnum event, VersionContext context) {
        System.out.println("应用成功");
    }
}
