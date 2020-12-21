package webserver;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import webcontainer.Servlet;

public class WebServer {

  private static final int PORT = 5027;

  public static ExecutorService executorService; // 스레드풀인 ExecutorService 선언

  private ServerSocket serverSocket;

  public void start(Map<String, Servlet> executableServlets) {

    ExecutorService threadPool = new ThreadPoolExecutor(10, // 코어 스레드 개수
        100, // 최대 스레드 개수
        120L, // 놀고 있는 시간
        TimeUnit.SECONDS, // 놀고 있는 시간 단위
        new SynchronousQueue<Runnable>() // 작업 큐
    ); // 초기 스레드 개수 0개,
    executorService = threadPool;

    try {
      serverSocket = new ServerSocket();
      serverSocket.bind(new InetSocketAddress(PORT));

      // 연결을 수락하는 코드
      while (true) {
        System.out.println("서버가 연결을 기다림");
        Socket socket = serverSocket.accept();

        // 스레드 풀의 작업 생성
        HttpReqRes my = new HttpReqRes(socket,executableServlets);
        executorService.submit(my);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void stopServer() {

    try {
      if (serverSocket != null && !serverSocket.isClosed()) { // ServerSocket 닫기.
        serverSocket.close();
      }

      if (executorService != null && !executorService.isShutdown()) {
        executorService.shutdown();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
