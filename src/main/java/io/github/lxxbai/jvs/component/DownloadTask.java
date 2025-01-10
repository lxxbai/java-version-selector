package io.github.lxxbai.jvs.component;

import cn.hutool.http.HttpUtil;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author lxxbai
 */
@Slf4j
@Getter
public class DownloadTask extends Task<Void> {

    private final String downloadUrl;

    private final String fileName;

    /**
     * 缓冲区大小,1kb
     */
    private static final int BUFFER_SIZE = 1024;

    protected volatile boolean cancelled = false;

    private final long ONE_SECOND = 1000L;

    private long downloadedBytes = 0L;

    private long totalBytes = 0L;

    public DownloadTask(String downloadUrl, String fileName) {
        this.downloadUrl = downloadUrl;
        this.fileName = fileName;
    }


    public String getKey() {
        return fileName;
    }


    @Override
    protected Void call() throws Exception {
        URL url = new URL(downloadUrl);
//        HttpUtil.downloadFile()
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setConnectTimeout(3000);
        // 校验文件是否存在并获取长度
        Path outputPath = Paths.get(fileName);
        downloadedBytes = Files.exists(outputPath) ? Files.size(outputPath) : 0;
        // 计算文件大小
        totalBytes = httpConn.getContentLength();
        if (downloadedBytes >= totalBytes) {
            log.warn("已经下载完成");
            return null;
        }
        // 重新连接
        httpConn = (HttpURLConnection) url.openConnection();
        //设置开始的下载位置
        if (downloadedBytes > 0) {
            httpConn.setRequestProperty("Range", "bytes=" + downloadedBytes + "-");
        }
        int responseCode = httpConn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_PARTIAL) {
            throw new IOException("Server returned HTTP response code: " + responseCode);
        }
        //更新已下载的大小
        updateProgress(downloadedBytes, totalBytes);
        long lastUpdateTime = System.currentTimeMillis();
        //开始下载
        try (InputStream inputStream = httpConn.getInputStream();
             ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
             FileChannel fileChannel = FileChannel.open(outputPath,
                     StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            // 读取数据并写入文件
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            long bytesRead;
            //前一秒下载的字节数
            long lastSecondBytesRead = downloadedBytes;
            double speed = 0;
            //运行中
            while (!cancelled && (bytesRead = readableByteChannel.read(buffer)) != -1) {
                //开始读取数据
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();
                downloadedBytes += bytesRead;
                // 计算下载速度
                long currentTime = System.currentTimeMillis();
                long timeElapsed = currentTime - lastUpdateTime;
                if (timeElapsed > ONE_SECOND) {
                    long bytesDownloadedInLastSecond = downloadedBytes - lastSecondBytesRead;
                    // KB/s
                    speed = bytesDownloadedInLastSecond / 1024.0;
                    lastSecondBytesRead = downloadedBytes;
                    lastUpdateTime = currentTime;
                }
                // 更新进度
                updateProgressInfo(downloadedBytes, totalBytes, speed);
            }
        }
        if (cancelled) {
            // 确保任务被取消
            cancel(true);
        }
        return null;
    }

    /**
     * 暂停任务
     * <p>
     * 本方法通过线程池调度给定的任务，实现异步执行
     */
    public void pauseOrCancel() {
        this.cancelled = true;
    }

    @Override
    protected void setException(Throwable t) {
        log.error("下载异常：", t);
    }

    /**
     * 更新进度
     */
    public void updateProgressInfo(double downloadedBytes, long totalBytes, double speed) {
        double progress = downloadedBytes / totalBytes;
        super.updateProgress(downloadedBytes, totalBytes);
        super.updateMessage(String.format("%.2f%% %.2f KB/s", progress * 100, speed));
    }

    /**
     * 更新进度
     */
    public void updateProgress(double downloadedBytes, long totalBytes) {
        double progress = downloadedBytes / totalBytes;
        super.updateProgress(downloadedBytes, totalBytes);
        super.updateMessage(String.format("%.2f%%", progress * 100));
    }

    /**
     * 更新消息
     */
    public void setMessage(String message) {
        super.updateMessage(message);
    }
}