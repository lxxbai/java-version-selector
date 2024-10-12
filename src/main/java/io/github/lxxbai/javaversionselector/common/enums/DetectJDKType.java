package io.github.lxxbai.javaversionselector.common.enums;

public class DetectJDKType {
    public static void main(String[] args) {
        // 获取系统属性
        String vmName = System.getProperty("java.vm.name");
        String vmVendor = System.getProperty("java.vm.vendor");
        String runtimeName = System.getProperty("java.runtime.name");
        String runtimeVersion = System.getProperty("java.runtime.version");

        // 输出系统属性
        System.out.println("Java VM Name: " + vmName);
        System.out.println("Java VM Vendor: " + vmVendor);
        System.out.println("Java Runtime Name: " + runtimeName);
        System.out.println("Java Runtime Version: " + runtimeVersion);

        // 判断 JDK 类型
        if (vmVendor != null && vmVendor.contains("Oracle")) {
            System.out.println("This is Oracle JDK.");
        } else if (vmVendor != null && vmVendor.contains("OpenJDK")) {
            System.out.println("This is OpenJDK.");
        } else {
            System.out.println("This is another type of JDK.");
        }
    }
}