package com.dapp.lan.handler.lc;

/**
 * @author Admin
 */

public enum MessageCode {
    /**
     * 响应编码
     */
    LOGIN(0),
    HEARTBEAT(1),
    CONNECT(2),
    PROXY_DATA(3),
    DISCONNECT(4);

    private int value;

    MessageCode(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static MessageCode value2MessageCode(int value) {
        if (value >= MessageCode.values().length) {
            //TODO Define Exception
            throw new RuntimeException("Invalid message code");
        }

        return MessageCode.values()[value];
    }

}
