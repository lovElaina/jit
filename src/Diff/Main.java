package Diff;

import Diff.util.FileUtil;

import java.io.IOException;


public class Main {
  /**
   * 项目入口.
   * @param args Read args from shell. args[0] is oldString and args[1] is newString.
   */
  public static void main(String[] args) {


    String oldFilename = "C:\\Users\\ZYC\\Desktop\\111.txt";
    String newFilename = "C:\\Users\\ZYC\\Desktop\\222.txt";

    String oldString;
    try {
      oldString = FileUtil.readToString(oldFilename);
    } catch (IOException exception) {
      System.out.println("Can't find file: " + oldFilename);
      return;
    }

    String newString;
    try {
      newString = FileUtil.readToString(newFilename);
    } catch (IOException exception) {
      System.out.println("Can't find file: " + oldFilename);
      return;
    }

    Difference difference = new Difference(oldString, newString);
    System.out.println(difference.getDiffString());
  }
}
