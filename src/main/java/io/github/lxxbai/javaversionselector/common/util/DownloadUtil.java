package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.URLUtil;
import javafx.concurrent.Task;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class DownloadUtil {


    /**
     * @param fileURL         下载地址
     * @param destinationFile 目标地址
     * @return 任务
     */
    public static Task<Void> buildDownloadTask(String fileURL, String destinationFile) {
        return new Task<>() {
            protected Void call() throws Exception {
                File file = new File(destinationFile);
                URL url = new URL(fileURL);
                //总大小
                long contentLength = URLUtil.getContentLength(url);
                //下载完成的大小
                if (Objects.equals(contentLength, file.length())) {
                    updateProgress(1, 1);
                    return null;
                }
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if (file.exists()) {
                    // 设置请求头，告诉服务器从哪个位置开始发送数据
                    connection.setRequestProperty("Range", "bytes=" + file.length() + "-");
                }
                BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
                byte[] buffer = new byte[1024];
                int bytesRead;
                long totalBytesRead = 0;
                while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                    totalBytesRead += bytesRead;
                    fileOutputStream.write(buffer, 0, bytesRead);
                    updateProgress(totalBytesRead, contentLength);
                    updateMessage(NumberUtil.div(totalBytesRead, contentLength) + "%");
                }
                in.close();
                fileOutputStream.close();
                return null;
            }

            @Override
            protected void setException(Throwable t) {
                System.out.println(t);
            }
        };
    }
}
