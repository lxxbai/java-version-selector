package io.github.lxxbai.jvs.datasource.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author lxxbai
 */
public class StrDateTypeHandler extends BaseTypeHandler<Date> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, DateUtil.formatDateTime(parameter));
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String dateStr = rs.getString(columnName);
        try {
            return StrUtil.isNotBlank(dateStr) ? DateUtil.parseDateTime(dateStr) : null;
        } catch (Exception e) {
            throw new SQLException("解析日期字符串出错: " + dateStr, e);
        }
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String dateStr = rs.getString(columnIndex);
        try {
            return StrUtil.isNotBlank(dateStr) ? DateUtil.parseDateTime(dateStr) : null;
        } catch (Exception e) {
            throw new SQLException("解析日期字符串出错: " + dateStr, e);
        }
    }


    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String dateStr = cs.getString(columnIndex);
        try {
            return StrUtil.isNotBlank(dateStr) ? DateUtil.parseDateTime(dateStr) : null;
        } catch (Exception e) {
            throw new SQLException("解析日期字符串出错: " + dateStr, e);
        }
    }
}