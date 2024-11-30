package io.github.lxxbai.javaversionselector.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SetEnvironmentVariable {
    public static void main(String[] args) {
        // 定义批处理文件的路径
        String batchFilePath = "set_path.bat";

        // 定义要设置的环境变量及其值
        String javaHome = "C:\\Program Files\\Java\\jdk-11.0.1";
        String pathValue = "%JAVA_HOME%\\bin";

        try (FileWriter writer = new FileWriter(new File(batchFilePath))) {
            // 写入批处理文件内容
            writer.write("@echo off\n");
            writer.write("setlocal enabledelayedexpansion\n");
            writer.write("set \"JAVA_HOME=" + javaHome + "\"\n");
            writer.write("set \"PATH=!JAVA_HOME!\\bin;%PATH%\"\n");
            writer.write("setx JAVA_HOME \"!JAVA_HOME!\"\n");
            writer.write("setx PATH \"!PATH!\"\n");
            writer.write("endlocal\n");

            System.out.println("Batch file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 执行批处理文件
        try {
            Process process = Runtime.getRuntime().exec("cmd /c " + batchFilePath);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Environment variables set successfully.");
            } else {
                System.out.println("Failed to set environment variables.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}