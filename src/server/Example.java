package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Example {

  private static final String PATH = ".\\webapps";

  private static String WarFile = "\\Beer.war";

  public static void main(String[] args) {

    try {

      JarFile jarFile = new JarFile(PATH + WarFile);
//      String name2 = jarFile.getName();
//      System.out.println("name2 : " + name2);

//      String fileName = getSplit(name2, "\\\\", true);
//      System.out.println("aa: " + fileName);

      String realFileName = getSplit(WarFile, "\\.", false);
      System.out.println("ssdF   " + realFileName);
//
//      System.out.println("파일 이름 : " + jarFile.getName());

      // 일단 폴더 이름이 있는지 확인하고 없으면 파일을 만들어준다.
      File folder = new File(PATH + "\\" + realFileName);
      
      if (!folder.exists()) {
        try{
            folder.mkdir(); //폴더 생성합니다.
            System.out.println("폴더가 생성되었습니다.");
              } 
              catch(Exception e){
            e.getStackTrace();
        }        
             }else {
        System.out.println("이미 폴더가 생성되어 있습니다.");
      }

      
      // 그리고 그 다음에 폴더 믿으로 파일을 하나씩 풀어나간다.
      Enumeration<JarEntry> entries = jarFile.entries();

      while (entries.hasMoreElements()) {
        JarEntry name = entries.nextElement();
        System.out.println("name: " + name);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static String[] getFileName() throws IOException {

    ArrayList<String> fileNames = new ArrayList<String>();

    Files.walk(Paths.get(PATH)).forEach(filePath -> {
      if (Files.isRegularFile(filePath)) {
        System.out.println("ㅠㅠ: " + filePath.getFileName());
        // fileNames.add(filePath.getFileName());

      }
    });


    return null;
  }

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
