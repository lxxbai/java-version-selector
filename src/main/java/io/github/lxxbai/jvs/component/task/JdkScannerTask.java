package io.github.lxxbai.jvs.component.task;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import io.github.lxxbai.jvs.common.exception.ClientException;
import io.github.lxxbai.jvs.common.model.JdkInfo;
import io.github.lxxbai.jvs.common.util.JdkUtil;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * jdk 扫描任务
 *
 * @author lxxbai
 */
@Slf4j
public class JdkScannerTask extends Task<Void> {

    /**
     * 根目录
     */
    private final File rootDir;

    @Getter
    private final List<JdkInfo> jdkInfoList;

    public JdkScannerTask(File rootDir) {
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new ClientException("文件夹不存在: " + rootDir);
        }
        this.rootDir = rootDir;
        this.jdkInfoList = new ArrayList<>();

    }

    public JdkScannerTask(String path) {
        this(new File(path));
    }

    @Override
    protected Void call() throws Exception {
        // 等待0.5秒，让进度条显示完成
        ThreadUtil.sleep(500);
        scan();
        return null;
    }

    /**
     * 递归扫描目录，查找JDK安装。
     */
    private void scan() {

        FileUtil.walkFiles(rootDir.toPath(), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                // 如果任务被取消，则终止递归
                if (isCancelled()) {
                    return FileVisitResult.TERMINATE;
                }
                if (JdkUtil.isJdk(dir)) {
                    //获取JDK信息
                    JdkInfo javaInfo = JdkUtil.toJavaInfo(dir.toFile().getAbsolutePath());
                    jdkInfoList.add(javaInfo);
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });
    }
}