package container;

import java.util.Map;
import communication.Request;
import communication.Response;

public class WebContainer {
  
  HTTPServletRequest httpServletRequest;

  HTTPServletResponse httpServletResponse;
  
  public WebContainer(Request request, Response response) {
    
    System.out.println("컨테이너 로직까지 왔음");

    httpServletRequest = new HTTPServletRequest(request);
    httpServletResponse = new HTTPServletResponse(response);
    
  }
  
  public void start(Map<String, Servlet> executeServlet ) {
    
    for(String key : executeServlet.keySet()) {
      if(key.equals("/basic" + httpServletRequest.getRequestUrl())) {
        System.out.println("여기까지 옴");
         executeServlet.get(key).service(httpServletRequest, httpServletResponse);
      }
    }
  }

}
