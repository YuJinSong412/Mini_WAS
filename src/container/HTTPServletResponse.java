package container;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import communication.Response;

public class HTTPServletResponse {

  Response response;

  private String contentType;

  OutputStream outputStream;

  private int contentLength;
  
  
  public HTTPServletResponse(Response response) {

    this.response = response;

    reApply();
  }

  private void reApply() {

    this.outputStream = response.getOutputStream();

  }
  
  public PrintWriter headerInfo() {
    PrintWriter out = new PrintWriter(outputStream);
    out.println("HTTP/1.1 200 OK");
    out.println("Content-Type: text/html; charset=UTF-8");
    out.println();
    out.flush();
    
    return out;
  }

  public PrintWriter getWriter() throws IOException {

    PrintWriter out = headerInfo(); 
    
    return out;
   
  }
  
  public void showScreen(ByteArrayOutputStream bout) throws IOException {
    headerInfo(); 
    
    bout.writeTo(outputStream);
  }   

  public int getContentLength() {
  
    return contentLength;
  }

  
  public void setContentLength(int contentLength) {
  
    this.contentLength = contentLength;
  }

  
  public String getContentType() {

    return contentType;
  }


  public void setContentType(String contentType) {

    this.contentType = contentType;
  }

  
  public OutputStream getOutputStream() {
  
    return outputStream;
  }

  
  public void setOutputStream(OutputStream outputStream) {
  
    this.outputStream = outputStream;
  }

}
