package communication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Response {

  private String firstLine;

  private String contentType;

  private String contentLength;

  OutputStream outputStream;

  public Response() {

  }

  public Response(OutputStream outputStream) {

    this.outputStream = outputStream;

  }

  public OutputStream getOutputStream() {

    return outputStream;
  }


  public void setOutputStream(OutputStream outputStream) {

    this.outputStream = outputStream;
  }



  public String getFirstLine() {

    return firstLine;
  }

  public void setFirstLine(String firstLine) {

    this.firstLine = firstLine;
  }

  public String getContentType() {

    return contentType;
  }

  public void setContentType(String contentType) {

    this.contentType = contentType;
  }

  public String getContentLength() {

    return contentLength;
  }


  public void setContentLength(String contentLength) {

    this.contentLength = contentLength;
  }

  public void showScreen(File file) throws IOException {

    PrintWriter out = new PrintWriter(outputStream);
    out.println(firstLine);
    out.println(contentType);
    out.println(contentLength);
    out.println();
    out.flush();


    ArrayList<Byte> fileBytes = getFileBytes(file);

    byte[] byteArr = new byte[fileBytes.size()];

    int writeCount = 0;

    for (byte fileByte : fileBytes) {
      byteArr[writeCount] = fileByte;
      writeCount++;
    }

    outputStream.write(byteArr);
    outputStream.flush();

  }

  private ArrayList<Byte> getFileBytes(File file) {

    ArrayList<Byte> fileBytes = new ArrayList<Byte>();
    try {
      FileInputStream fis = new FileInputStream(file);

      int data = 0;
      while ((data = fis.read()) != -1) {
        fileBytes.add((byte) data);
        // System.out.write((byte)data);
      }
      fis.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileBytes;
  }
}
