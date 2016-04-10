package terminal.run;

/**
 * Created by DotinSchool2 on 4/9/2016.
 */
public class Server {

    String ip;
    String port;

    public Server(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


}
