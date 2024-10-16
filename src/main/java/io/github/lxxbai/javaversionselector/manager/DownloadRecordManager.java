package io.github.lxxbai.javaversionselector.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.lxxbai.javaversionselector.datasource.entity.DownloadRecordDO;
import io.github.lxxbai.javaversionselector.datasource.mapper.DownloadRecordMapper;
import org.springframework.stereotype.Component;


/**
 * 下载记录
 *
 * @author lxxbai
 */
@Component
public class DownloadRecordManager extends ServiceImpl<DownloadRecordMapper, DownloadRecordDO> {

}
