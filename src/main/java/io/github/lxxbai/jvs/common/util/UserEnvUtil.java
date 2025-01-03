package io.github.lxxbai.jvs.common.util;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import io.github.lxxbai.jvs.common.Constants;


/**
 * 实时获用户环境变量
 *
 * @author lxx
 */
public class UserEnvUtil {

    /**
     * windows用户环境变量路径
     */
    private static final String USER_ENV_PATH = "HKEY_CURRENT_USER\\Environment";


    private static final String JAVA_PATH = "%JAVA_HOME%\\bin";


    private UserEnvUtil() {

    }


    /**
     * 删除用户环境变量
     * <p>
     * 本方法通过调用注册表编辑命令来删除指定的用户环境变量
     * 使用cmd命令行工具执行注册表删除操作，强制删除环境变量而不提示用户
     *
     * @param envName 要删除的环境变量名称
     */
    public static void deleteUserEnv(String envName) {
        // 使用RuntimeUtil封装的执行命令行字符串的方法，拼接删除命令
        RuntimeUtil.execForStr("cmd /c reg delete " + USER_ENV_PATH + " /v " + envName + " /f");
    }

    /**
     * 添加用户环境变量
     * 该方法使用系统命令setx来设置用户级别的环境变量，避免了重启系统的问题
     *
     * @param envName  环境变量名称 需要设置或修改的环境变量的名称
     * @param envValue 环境变量值 对应环境变量的值
     */
    public static void addUserEnv(String envName, String envValue) {
        RuntimeUtil.execForStr("cmd /c setx " + envName + " \"" + envValue + "\"");
    }

    /**
     * 获取用户注册表信息
     *
     * @return 用户注册表信息
     */
    public static String getUserEnv(String envName) {
        return Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, "Environment", envName);
    }

    /**
     * 异步更新注册表信息
     */
    public static void updateUserEnv(String envName, String newEnvValue) {
        // 使用RuntimeUtil封装的执行命令行字符串的方法，拼接删除命令
        RuntimeUtil.execForStr("cmd /c reg delete " + USER_ENV_PATH + " /v " + envName + " /f");
        RuntimeUtil.execForStr("cmd /c setx " + envName + " \"" + newEnvValue + "\"");
    }


    /**
     * 添加JDK环境变量
     *
     * @param javaHome javaHome
     */
    public static void addWindowsJdkHome(String javaHome) {
        //获取path，查询%JAVA_HOME%\bin是否是第一个，
        String path = getUserEnv("Path");
        if (!StrUtil.startWith(path, JAVA_PATH)) {
            addPathToFirst(JAVA_PATH);
        }
        //设置java_home
        addUserEnv(Constants.STR_JAVA_HOME, javaHome);
    }


    /**
     * 添加用户 PATH 到最前面
     */
    public static void addPathToFirst(String pathValue) {
        String oldPath = Advapi32Util.registryGetExpandableStringValue(WinReg.HKEY_CURRENT_USER, "Environment", "PATH");
        if (StrUtil.contains(oldPath, pathValue + ";")) {
            oldPath = oldPath.replace(pathValue + ";", "");
        }
        Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_USER, "Environment", "PATH", pathValue + ";" + oldPath);
    }
}
