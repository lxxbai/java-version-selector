package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.system.SystemUtil;
import io.github.lxxbai.javaversionselector.common.Constants;
import io.github.lxxbai.javaversionselector.manager.JdkVersionManager;
import io.github.lxxbai.javaversionselector.manager.UserJdkVersionManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * app 初始化工具类
 *
 * @author lxxbai
 */
public class AppInitUtil {


    /**
     * 初始化数据库
     */
    public static void initUserData() {
        //初始化全部Java版本
        SpringUtil.getBean(JdkVersionManager.class).refresh();
        // 初始化用户已安装的Java版本
        SpringUtil.getBean(UserJdkVersionManager.class).initInstalledUserJdk();
    }


    /**
     * 初始化方法，用于准备数据库环境
     * 该方法首先尝试创建数据库文件夹，如果文件夹不存在，则创建它
     * 如果创建过程中遇到任何异常，将显示错误对话框通知用户
     */
    public static void initDb() {
        //数据库文件夹
        Path dbFolder = Paths.get(System.getenv(Constants.LOCAL_APP_DATA)).resolve(Constants.APP_NAME);
        try {
            if (!Files.exists(dbFolder)) {
                //创建文件夹
                Files.createDirectory(dbFolder);
            }
        } catch (Exception e) {
            DialogUtils.showErrorDialog("初始化数据库失败", "请检查权限", e.getMessage());
        }
        SystemUtil.set("db.home", dbFolder.toString());
    }
}
