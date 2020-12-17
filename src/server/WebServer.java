package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import communication.Request;
import communication.Response;
import container.Servlet;
import container.WebContainer;
import util.Paths;

public class WebServer {

  private static final int PORT = 5027;

  public static ExecutorService executorService; // 스레드풀인 ExecutorService 선언

  private ServerSocket serverSocket;
  
  private static final String ERROR_PAGE = "errorPage.html";

  private static final String PAGE_FOLDER = "/basic";
  
  private static final String HTML_EXTENSION = ".html";
  
  public void start(Map<String, Servlet> executeServlet) {

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
        Runnable runnable = new Runnable() {
          
          @Override
          public void run() {

            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            OutputStream outputStream = null;

            try {
              // 요청을 받는 역할 - 요청을 읽어서 request 객체에 정보를 넣음
              Request request = new Request();
              inputStream = socket.getInputStream();
              bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
              request = receiveRequest(request, bufferedReader);
              System.out.println("오청받기 성공");

              outputStream = socket.getOutputStream();
              Response response = new Response(outputStream);

              // xml에 매핑되어있는 것이 있는지 확인하기 
              String newRequestUrl = "";
              if (request.getRequestUrl().contains("?")) {
                String[] newRequestURL = request.getRequestUrl().split("\\?");
                newRequestUrl = newRequestURL[0];
              }
              
              for (String key : executeServlet.keySet()) {
                if ((PAGE_FOLDER + newRequestUrl).equals(key)) {

                  new WebContainer(request, response).start(executeServlet);

                } else { // 매핑 없으면 정적폴더로 가서 알맞게 띄우기
                  
                  String changeRequestUrl = request.getRequestUrl().replace("/", "\\");

                  File file = new File(Paths.getStaticFilePath() + changeRequestUrl + HTML_EXTENSION);

                  if (file.isFile()) {
                    sendResponse(file, response);
                  } else {
                    System.out.println("파일을 찾을 수 없습니다.");
                    file = new File(
                        Paths.getStaticFilePath() + Paths.getPathSeparate() + ERROR_PAGE);
                    sendResponse(file, response);
                  }
                }
              }
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
              try {
                inputStream.close();
                bufferedReader.close();
                outputStream.close();
                socket.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        };
        executorService.submit(runnable);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void sendRedirect() {
    
  }
  
  
  private Request receiveRequest(Request request, BufferedReader bufferedReader) {

    try {

      String line = "tmp";
      Map<String, String> requestHeaderMap = new HashMap<String, String>();

      for (int i = 1; (line = bufferedReader.readLine()) != null && !line.equals(""); i++) {
        if (i == 1) {
          int firstBlank = line.indexOf(" ");
          int lastBlank = line.lastIndexOf(" ");

          request.setMethod(line.substring(0, firstBlank));
          request.setRequestUrl(line.substring(firstBlank + 1, lastBlank).trim());
          System.out.println("?????????: " +request.getRequestUrl());
          request.setHttpVersion(line.substring(lastBlank + 1).trim());

        } else {
          int indexOfColon = line.indexOf(": ");
          if (indexOfColon == -1) {
            continue;
          } else {
            String headerName = line.substring(0, indexOfColon).trim();
            String headerValue = line.substring(indexOfColon + 1).trim();
            requestHeaderMap.put(headerName, headerValue);
          }

        }
      }
      request.setHeaderMap(requestHeaderMap);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return request;
  }

  public void sendResponse(File file, Response response) throws IOException {

    response.setFirstLine("HTTP/1.1 200 OK");
    response.setContentType("Content-Type: text/html; charset=UTF-8");
    response.setContentLength("Content-Length: " + file.length());
    response.showScreen(file);


    System.out.println("정적 파일 응답 보내줌");
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
