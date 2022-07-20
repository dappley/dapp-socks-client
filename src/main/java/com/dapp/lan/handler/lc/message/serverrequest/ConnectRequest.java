package com.dapp.lan.handler.lc.message.serverrequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectRequest {
    private String id;
    private String dst;
    private int    port;
}
