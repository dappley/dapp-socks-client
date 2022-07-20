package com.dapp.lan.config;

/**
 * @Author: suxinsen
 * @Date: 2019/12/18 9:54
 * @Description:
 */
public class PersistentConnectionConfiguration {
    private String host;
    private Integer port;
    private String username;
    private String proxyIp;
    private Integer proxyPort;

    public PersistentConnectionConfiguration() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }
}
