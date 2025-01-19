package io.github.lxxbai.jvs.test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class FolderScannerWithProgress {

    public static void main(String[] args) {
        Path folderPath = Paths.get("D:\\");
        AtomicInteger totalFiles = new AtomicInteger(0);
        AtomicInteger processedFiles = new AtomicInteger(0);

        try {
            // 首先遍历一次以确定总文件数量
            Files.walkFileTree(folderPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    totalFiles.incrementAndGet();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.err.println("无法访问文件: " + file.toString());
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("共找到 " + totalFiles.get() + " 个文件.");

            // 再次遍历以实际处理每个文件，并显示进度
            Files.walkFileTree(folderPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    processFile(file);
                    int currentCount = processedFiles.incrementAndGet();
                    printProgress(currentCount, totalFiles.get());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.err.println("无法访问文件: " + file.toString());
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("\n扫描完成.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processFile(Path file) {
        // 在这里执行你对文件的操作
    }

    private static void printProgress(int current, int total) {
        double progress = (double) current / total * 100;
        System.out.printf("\r进度: %.2f%% (%d/%d)", progress, current, total);
    }
}