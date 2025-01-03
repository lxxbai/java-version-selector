package io.github.lxxbai.jvs.test;

import java.io.IOException;

public class UpdateJavaHome {
    public static void main(String[] args) {
        try {
            // 1. 设置新的 JAVA_HOME
            String javaHomePath = "E:\\jdk\\jdk-21.0.4"; // 修改为你的新 Java 路径
            ProcessBuilder setEnv = new ProcessBuilder(
                "cmd.exe", "/c", "setx JAVA_HOME \"" + javaHomePath + "\" /M"
            );
            Process process = setEnv.start();
            process.waitFor();
            System.out.println("JAVA_HOME 设置为: " + javaHomePath);

            // 2. 启动新 CMD 窗口并验证
            ProcessBuilder newCmd = new ProcessBuilder(
                "cmd.exe", "/c", "start cmd.exe /k java -version"
            );
            newCmd.start();
            System.out.println("新 CMD 窗口已打开，正在验证 java 版本...");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
