package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import util.Paths;

public class WarExtract {

  private static String warFileName = "Beer.war";  //그냥 일단 지정해줌

  public void start() {

    try {
      JarFile jarFile = new JarFile(Paths.getDefaultRunPath() + Paths.getPathSeparate() + warFileName);
      String contextName = getContextName(warFileName, "\\.");
      Paths.setContextName(contextName);
      
      // 일단 폴더 이름이 있는지 확인하고 없으면 폴더를 먼저 만들어준다.
      String contextPath = Paths.getDefaultRunPath() + Paths.getPathSeparate() + contextName;
      makeFolder(contextPath);

      // 그리고 그 다음에 폴더 밑으로 파일을 하나씩 풀어나간다.
      Enumeration<JarEntry> entries = jarFile.entries();

      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();

        if (entry.getName().endsWith("/")) {
          File file = new File(contextPath + Paths.getPathSeparate() + entry);
          file.mkdirs();
        }
      }

      entries = jarFile.entries();
      while (entries.hasMoreElements()) {
        JarEntry entry2 = entries.nextElement();
        if (!entry2.getName().endsWith("/")) {

          try {
            InputStream is = jarFile.getInputStream(entry2);
            FileOutputStream fos = new FileOutputStream(contextPath + Paths.getPathSeparate() + entry2);

            int readByteNo;
            byte[] readBytes = new byte[100];

            while ((readByteNo = is.read(readBytes)) != -1) {
              fos.write(readBytes, 0, readByteNo);
            }
            fos.flush();
            fos.close();
            is.close();

          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getContextName(String warFileName, String split) {

    String[] text = warFileName.split(split);

    String contextName = "";

    contextName = text[0];

    return contextName;

  }

  private void makeFolder(String path) {

    File folder = new File(path);

    if (!folder.exists()) {
      try {
        folder.mkdir();
        System.out.println("컨텍스트가 생성되었습니다.");
      } catch (Exception e) {
        e.getStackTrace();
      }
    } else {
      System.out.println("이미 컨텍스트가 생성되었습니다.");
    }
  }


}
