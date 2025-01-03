package io.github.lxxbai.jvs.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.lxxbai.jvs.datasource.entity.InstallRecordDO;
import io.github.lxxbai.jvs.datasource.mapper.InstallRecordMapper;
import org.springframework.stereotype.Component;


/**
 * 下载记录
 *
 * @author lxxbai
 */
@Component
public class InstallRecordManager extends ServiceImpl<InstallRecordMapper, InstallRecordDO> {

}
