package com.dapp.lan.handler.lc.common;
/**
 * @Author: suxinsen
 * @Date: 2019/12/17 14:10
 * @Description:
 */
public enum ErrorCode {
    /**
     * success: connection success
     * fail: connection success
     */
    SUCCESS(0, "Success"), FAIL(1, "Fail");

    public Integer code;
    public String value;

    ErrorCode(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValue(Integer code) {
        ErrorCode[] values = ErrorCode.values();
        for (ErrorCode errorCode:values
             ) {
            if (errorCode.code.equals(code)) {
                return errorCode.value;
            }
        }
        return null;
    }

}
