package io.github.lxxbai.javaversionselector.state;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;

import java.util.Objects;

/**
 * 统一执行器
 *
 * @author lxxbai
 */
public interface StateMachineHandler<S, E, C> extends Action<S, E, C>, Condition<C> {

    /**
     * from 状态集合
     *
     * @return from 状态集合
     */
    S[] froms();

    /**
     * to 状态
     *
     * @return to 状态
     */
    S to();

    /**
     * 事件
     *
     * @return 事件
     */
    E event();


    default boolean moreFrom() {
        S[] froms = froms();
        return ArrayUtil.isNotEmpty(froms) && froms.length > 1;
    }

    default boolean nullTo() {
        return Objects.isNull(to());
    }
}
