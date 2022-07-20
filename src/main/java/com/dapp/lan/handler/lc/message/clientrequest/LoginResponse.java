package com.dapp.lan.handler.lc.message.clientrequest;

import lombok.Data;

/**
 * @author Admin
 */
@Data
public class LoginResponse {
    private int error;
    private String username;
}
