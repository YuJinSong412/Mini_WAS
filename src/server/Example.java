package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Example {

  private static final String PATH = ".\\webapps";

  private static String WarFile = "\\Beer.war";

  private static final String PATH_SEPARATE = "\\";

  public static void main(String[] args) {

    try {

      JarFile jarFile = new JarFile(PATH + WarFile);

      String fileName = getSplit(WarFile, "\\.", false);
      System.out.println("fileName   " + fileName);

      // 일단 폴더 이름이 있는지 확인하고 없으면 파일을 만들어준다.
      String contextPath = PATH + PATH_SEPARATE + fileName; // .\webapps\\Beer
      File folder = new File(contextPath);

      if (!folder.exists()) {
        try {
          folder.mkdir(); // 폴더 생성합니다.
          System.out.println("폴더가 생성되었습니다.");
        } catch (Exception e) {
          e.getStackTrace();
        }
      } else {
        System.out.println("이미 폴더가 생성되어 있습니다.");
      }
      //makeFiles(contextPath);


      // 그리고 그 다음에 폴더 믿으로 파일을 하나씩 풀어나간다.
      Enumeration<JarEntry> entries = jarFile.entries();
      
      while (entries.hasMoreElements()) {
        JarEntry name = entries.nextElement();
        System.out.println("name: " + name);
        gogo(contextPath, name, jarFile);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  
  private static void gogo(String filePath, JarEntry name, JarFile jarFile) {
    String[] a = name.toString().split("/");
    
    String path  = filePath;
    
    for(String aa : a) {
     // System.out.println("path" + path);
      
      if(!aa.contains(".")) {
        File folder = new File(path + PATH_SEPARATE + aa);
        if (!folder.exists()) {
          try {
            folder.mkdir(); // 폴더 생성합니다.
            //System.out.println("폴더가 생성되었습니다.");
            //path = path + PATH_SEPARATE + folder.getName();
          } catch (Exception e) {
            e.getStackTrace();
          }
        } else {
          //System.out.println("이미 폴더가 생성되어 있습니다.");
         // path = path + PATH_SEPARATE + folder.getName();
        }
        path = path + PATH_SEPARATE + folder.getName();
      }else if(aa.contains(".")) {
        String path2 = filePath + PATH_SEPARATE + name;
        
        try {
          InputStream yes = jarFile.getInputStream(name);
         // FileInputStream fis = new FileInputStream(path2);
          FileOutputStream fos = new FileOutputStream(path + PATH_SEPARATE + aa);
          
          int readByteNo;
          byte[] readBytes = new byte[100];
          
          while((readByteNo = yes.read(readBytes)) != -1) {
            fos.write(readBytes, 0 , readByteNo);
          }
          fos.flush();
          fos.close();
          yes.close();
          
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
      }
     // System.out.println("## " + aa);
    }
  }
  
//  File file = new File(path + PATH_SEPARATE + aa);
//  boolean success;
//  try {
//    success = file.createNewFile();
//    if(!success) {
//      System.out.println("이미 존재");
//    }
//  } catch (IOException e) {
//    // TODO Auto-generated catch block
//    e.printStackTrace();
//  }

//  private static void makeFiles(String contextPath) {
//
//    File file = new File(contextPath);
//  }

//  private static String[] getFileName() throws IOException {
//
//    ArrayList<String> fileNames = new ArrayList<String>();
//
//    Files.walk(Paths.get(PATH)).forEach(filePath -> {
//      if (Files.isRegularFile(filePath)) {
//        System.out.println("ㅠㅠ: " + filePath.getFileName());
//        // fileNames.add(filePath.getFileName());
//
//      }
//    });
//
//
//    return null;
//  }

  private static String getSplit(String text, String split, boolean b) {

    String[] a = text.split(split);

    String result = "";

    if (b) {
      result = a[a.length - 1];
    } else {
      result = a[0];
    }

    return result;

  }

  public void start() {}
}
