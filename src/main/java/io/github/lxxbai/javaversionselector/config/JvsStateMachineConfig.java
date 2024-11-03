package io.github.lxxbai.javaversionselector.config;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import io.github.lxxbai.javaversionselector.common.enums.ActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.state.action.AbstractInstallHandler;
import io.github.lxxbai.javaversionselector.state.context.InstallContext;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 状态机配置
 *
 * @author lxxbai
 */
@Configuration
public class JvsStateMachineConfig {

    @Resource
    private List<AbstractInstallHandler> handlers;

    @Bean
    public StateMachine<InstallStatusEnum, ActionEnum, InstallContext> jvsStateMachine() {
        StateMachineBuilder<InstallStatusEnum, ActionEnum, InstallContext> builder = StateMachineBuilderFactory.create();
        handlers.forEach(handler -> {
            //是否多个状态
            boolean moreFrom = handler.moreFrom();
            if (moreFrom) {
                builder.externalTransitions()
                        .fromAmong(handler.froms())
                        .to(handler.to())
                        .on(handler.event())
                        .when(handler)
                        .perform(handler);
            } else {
                builder.externalTransition()
                        .from(handler.froms()[0])
                        .to(handler.to())
                        .on(handler.event())
                        .when(handler)
                        .perform(handler);
            }
        });
        builder.setFailCallback((sourceState, event, context) -> System.out.println(context));
        return builder.build("JVS");
    }
}
