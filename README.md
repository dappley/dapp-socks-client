# proxy-client/config.properties
```
socks.username  #Your proxy client id, if it is empty, the server will automatically generate a return
socks.host      #Your socks server ip
socks.port      #Your socks server port
proxy.ip        #Http ip address being proxied
proxy.port      #Http port being proxied
```
# Start script
```java -jar -Dconfig=properties proxy-client.jar```
# output:
```
Login Success, proxy [ip:192.168.5.24, port:80] to url is : http://test.dappworks.net
Login Success, proxy [ip:192.168.5.24, port:80] to url is : https://test.dappworks.net
```
