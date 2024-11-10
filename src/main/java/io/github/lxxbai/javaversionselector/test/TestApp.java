package io.github.lxxbai.javaversionselector.test;

import cn.hutool.core.util.RuntimeUtil;

public class TestApp {
    public static void main(String[] args) throws Exception {
//        ProgressBarExample.main(args);


        try {
            Process exec = RuntimeUtil.exec("E:\\FileZilla_Server_1.9.1_win64-setup.exe");
            int i = exec.waitFor();
            System.out.println(i);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
