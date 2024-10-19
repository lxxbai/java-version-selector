package io.github.lxxbai.javaversionselector.common.exception;


import lombok.Data;

@Data
public class ClientException extends RuntimeException {

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误消息
     */
    private String errorMsg;

    public ClientException(String errorCode, String errorMsg) {
        super(errorMsg);
    }

    public ClientException(String errorMsg) {
        super(errorMsg);
    }
}
