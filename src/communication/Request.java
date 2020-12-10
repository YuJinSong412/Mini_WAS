package communication;

import java.util.HashMap;
import java.util.Map;

public class Request {

  private String method;

  private String requestUrl;

  private String httpVersion;

  private String connection;

  private String accept;

  private String acceptEncoding;

  private Map<String, String> headerMap;

  public Request() {

    headerMap = new HashMap<String, String>();
  }


  public String getMethod() {

    return method;
  }


  public void setMethod(String method) {

    this.method = method;
  }


  public String getRequestUrl() {

    return requestUrl;
  }


  public void setRequestUrl(String requestUrl) {

    this.requestUrl = requestUrl;
  }


  public String getHttpVersion() {

    return httpVersion;
  }


  public void setHttpVersion(String httpVersion) {

    this.httpVersion = httpVersion;
  }


  public String getConnection() {

    return connection;
  }


  public void setConnection(String connection) {

    this.connection = connection;
  }


  public String getAccept() {

    return accept;
  }


  public void setAccept(String accept) {

    this.accept = accept;
  }


  public String getAcceptEncoding() {

    return acceptEncoding;
  }


  public void setAcceptEncoding(String acceptEncoding) {

    this.acceptEncoding = acceptEncoding;
  }

  public Map<String, String> getHeaderMap() {

    return headerMap;
  }

  public void setHeaderMap(Map<String, String> headerMap) {

    this.headerMap = headerMap;
  }

}
