package com.dapp.lan;

import com.dapp.lan.config.PersistentConnectionConfiguration;
import com.dapp.lan.handler.lc.UDPClient;
import com.dapp.lan.server.PersistentConnectionClient;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @Author: suxinsen
 * @Date: 2019/12/18 9:48
 * @Description:
 */
@Slf4j
public class Application {

    public static void main(String[] args) {
        Properties configProperties = new Properties();
        String config = System.getProperty("config");
        try {
            if (config != null) {
                FileInputStream configIs = new FileInputStream(config);
                configProperties.load(configIs);
            } else {
                InputStream localConfig = Application.class.getClassLoader().getResourceAsStream("config.properties");
                configProperties.load(localConfig);
            }
        } catch (Exception e) {
            log.error("[Application-main] read config properties fail, exception message is {}", e.getMessage());
        }
        if ("true".equals(configProperties.getProperty("useProtocol.TCP").toString())) {
            String host = Objects.requireNonNull(configProperties.getProperty("socks.host"));
            System.out.println("host = " + host);
            String port = Objects.requireNonNull(configProperties.getProperty("socks.port"));
            System.out.println("port = " + port);
            String username = configProperties.getProperty("socks.username");
            System.out.println("username = " + username);
            String proxyIp = Objects.requireNonNull(configProperties.getProperty("proxy.ip"));
            System.out.println("proxyIp = " + proxyIp);
            String proxyPort = Objects.requireNonNull(configProperties.getProperty("proxy.port"));
            System.out.println("proxyPort = " + proxyPort);
            PersistentConnectionConfiguration connectionConfiguration = new PersistentConnectionConfiguration();
            connectionConfiguration.setHost(host);
            connectionConfiguration.setPort(Integer.parseInt(port));
            connectionConfiguration.setUsername(username);
            connectionConfiguration.setProxyIp(proxyIp);
            connectionConfiguration.setProxyPort(Integer.parseInt(proxyPort));
            //start ping server
            PersistentConnectionClient client = new PersistentConnectionClient();
            client.connectionConfiguration(connectionConfiguration).run();
        }
        if ("true".equals(configProperties.getProperty("useProtocol.UDP").toString())) {
            try {
                new UDPClient().run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
