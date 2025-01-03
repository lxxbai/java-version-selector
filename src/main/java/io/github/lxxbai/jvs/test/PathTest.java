package io.github.lxxbai.jvs.test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {
    public static void main(String[] args) {
        Path appDataLocalPath = Paths.get(System.getenv("LOCALAPPDATA")).resolve("JavaVersionSelector");
        System.out.println(appDataLocalPath.toString());
    }
}
