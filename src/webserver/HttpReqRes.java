package webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import enums.StatusCode;
import env.Paths;
import webcontainer.Servlet;
import webcontainer.WebContainer;

public class HttpReqRes implements Runnable {

  private static final String ERROR_PAGE = "errorPage.html";

  private static final String PAGE_FOLDER = "/basic";

  private static final String HTML_EXTENSION = ".html";

  Socket socket;

  Map<String, Servlet> executableServlets;

  public HttpReqRes(Socket socket, Map<String, Servlet> executableServlets) {

    this.socket = socket;
    this.executableServlets = executableServlets;
  }

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

      if (executableServlets.keySet().contains(PAGE_FOLDER + newRequestUrl)) {

        new WebContainer(request, response).start(executableServlets);

      } else {

        File file = null;

        if (request.getRequestUrl().equals("/" + Paths.getContextName())) {

          file = new File(
              Paths.getStaticFilePath() + Paths.getPathSeparate() + Paths.getWELCOME_FILE());

        } else {
          String changeRequestUrl = request.getRequestUrl().replace("/", "\\");

          file = new File(Paths.getStaticFilePath() + changeRequestUrl + HTML_EXTENSION);

        }

        if (file.isFile()) {
          sendResponse(file, response, StatusCode.STATUS_200);
        } else {
          System.out.println("파일을 찾을 수 없습니다.");
          file = new File(Paths.getStaticFilePath() + Paths.getPathSeparate() + ERROR_PAGE);
          sendResponse(file, response, StatusCode.STATUS_404);
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

  public void sendResponse(File file, Response response, StatusCode status) throws IOException {

    response.setStatusCode("HTTP/1.1 " + status + "OK");
    response.setContentType("Content-Type: text/html; charset=UTF-8");
    response.setContentLength("Content-Length: " + file.length());
    response.showScreen(file);


    System.out.println("정적 파일 응답 보내줌");
  }

}
