package container;

import java.util.HashMap;
import java.util.Map;
import communication.Request;

public class HTTPServletRequest {

  Request request;

  private String method;

  private String queryString;

  private String requestUrl;

  // private Map<String, String> queryStringAnswer;

  public HTTPServletRequest(Request request) {

    this.request = request;

    reApply();
  }

  private void reApply() {

    method = request.getMethod();

    String[] separationUrl = request.getRequestUrl().split("\\?");

    if (separationUrl.length != 0) {

      requestUrl = separationUrl[0];
      queryString = separationUrl[1];
    }

  }

  public Request getRequest() {

    return request;
  }


  public void setRequest(Request request) {

    this.request = request;
  }


  public String getMethod() {

    return method;
  }


  public void setMethod(String method) {

    this.method = method;
  }


  public String getQueryString() {

    return queryString;
  }


  public void setQueryString(String queryString) {

    this.queryString = queryString;
  }


  public String getRequestUrl() {

    return requestUrl;
  }


  public void setRequestUrl(String requestUrl) {

    this.requestUrl = requestUrl;
  }

  public String getParameter(String name) {

    Map<String, String> queryStringValue = new HashMap<String, String>();
    queryStringValue = divideQueryString();

    String result = "";
    for (String key : queryStringValue.keySet()) {
      if (key.equals(name)) {
        result = queryStringValue.get(key);
      } else {
        result = null;
      }
    }
    return result;
  }

  private Map<String, String> divideQueryString() {

    Map<String, String> queryStringValue = new HashMap<String, String>();

    String[] separationQuery = queryString.split("=");

    queryStringValue.put(separationQuery[0], separationQuery[1]);

    return queryStringValue;

  }

}
