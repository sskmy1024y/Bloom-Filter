import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.nio.file.Files;

public class Main {

  public static void main(String[] args) throws Exception {
    String[] filelist = { "./file1.txt", "./file2.txt", "./file3.txt", "./file4.txt" }; // 登録するファイルリスト
    Keywords.input("./keyword.txt"); // キーワードを取得

    for (String filepath : filelist) {
      Database.register(filepath); // ファイルを読み込んで登録
    }

    // キーザードを入力する
    Scanner scanner = new Scanner(System.in);
    System.out.print("Keyword Input > ");

    // 検索を実行
    String input = scanner.nextLine();
    scanner.close();

    for (Database db : Database.getList()) {
      if (db.contain(input)) {
        System.out.println(db.getFilename() + " is negative");
      } else {
        System.out.println(db.getFilename() + " is false positive");
      }
    }
  }

}