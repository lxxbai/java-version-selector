package io.github.lxxbai.jvs.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充
 *
 * @author lxxbai
 */
@Component
@Slf4j
public class CommonFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.strictInsertFill(metaObject, "createdAt", () -> date, Date.class);
        this.strictInsertFill(metaObject, "updatedAt", () -> date, Date.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatedAt", Date::new, Date.class);
    }
}
