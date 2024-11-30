package io.github.lxxbai.javaversionselector.test;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;


public class JDKSwitcher {


    // Cross-platform method to set JAVA_HOME and update PATH
    public static void switchJDK(String newJDKPath) {
        if (newJDKPath == null || newJDKPath.isEmpty()) {
            throw new IllegalArgumentException("Invalid JDK path provided.");
        }


        // Set JAVA_HOME
        boolean success = Kernel32.INSTANCE.SetEnvironmentVariable("JAVA_HOME", newJDKPath);
        if (!success) {
            throw new RuntimeException("Failed to set JAVA_HOME environment variable.");
        }
        String environmentVariable = Kernel32Util.getEnvironmentVariable("Path");


        System.out.println("Switched to JDK: " + newJDKPath);
        System.out.println("Please restart your terminal or IDE to apply changes.");
    }

    public static void main(String[] args) {
        // Replace with the JDK path you want to switch to
        String newJDKPath = "E:\\jdk\\jdk-17.0.12";

        try {
            switchJDK(newJDKPath);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
