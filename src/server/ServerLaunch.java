package server;


public class ServerLaunch {

  public static void main(String[] args) {

    WarExtract warExtract = new WarExtract();
    warExtract.start();


    WebServer webServer = new WebServer();
    webServer.start();

  }

}
