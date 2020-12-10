package server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebServer {

  private static final int PORT = 5027;

  public static ExecutorService executorService; // 스레드풀인 ExecutorService 선언

  private ServerSocket serverSocket;

  public void start() {

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
      while(true) {
        System.out.println("서버가 연결을 기다림");
        Socket socket = serverSocket.accept();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
