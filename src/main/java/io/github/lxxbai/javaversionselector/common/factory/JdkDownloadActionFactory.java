
package io.github.lxxbai.javaversionselector.common.factory;

import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.javaversionselector.common.enums.DownloadStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.DialogUtils;
import io.github.lxxbai.javaversionselector.component.fx.DownloadProgress;
import io.github.lxxbai.javaversionselector.model.DownloadVO;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * 操作
 *
 * @author lxxbai
 */
public class JdkDownloadActionFactory implements Callback<TableColumn<DownloadVO, String>, TableCell<DownloadVO, String>> {

    @Override
    public TableCell<DownloadVO, String> call(TableColumn<DownloadVO, String> tableColumn) {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                // 获取当前行数据
                DownloadVO downloadVO = getTableView().getItems().get(getIndex());
                DownloadStatusEnum downloadStatus = downloadVO.getDownloadStatus();
                //开始下载按钮，暂停按钮，文件位置按钮，删除按钮

                JFXButton filePathButton = new JFXButton();
//                filePathButton.setGraphic();

                switch (downloadStatus) {
                    case DOWNLOADING, DOWNLOAD_PAUSE -> {
                    }
                    case DOWNLOAD_FAILURE -> setText("下载失败");
                    case DOWNLOADED -> setText("已下载");
                }
            }
        };
    }


    /**
     * 构建下载进度条
     *
     * @param downloadVO 版本信息
     * @return 下载进度条
     */
    private DownloadProgress buildDownloadProgressBar(DownloadVO downloadVO) {
        //获取下载地址
        DownloadProgress downloadProgress = new DownloadProgress(100, 30,
                downloadVO.getDownloadUrl(), downloadVO.getDownloadFileUrl(), "下载");
        //失败
        downloadProgress.getTask().setOnFailed(event -> {
            DialogUtils.showErrorDialog("下载失败", "下载失败", "下载失败");
            //applicationContext.publishEvent(new StatusChangeEvent(version, VersionActionEnum.FAILURE));
        });
        //下载成功
        downloadProgress.getTask().setOnSucceeded(event -> {
            //applicationContext.publishEvent(new StatusChangeEvent(version, VersionActionEnum.SUCCESS))
        });
        //当做暂停
        downloadProgress.getTask().setOnCancelled(event -> {
            //applicationContext.publishEvent(new StatusChangeEvent(version, VersionActionEnum.CANCEL))
        });
        return downloadProgress;
    }

}
