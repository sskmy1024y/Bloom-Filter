import java.util.*;

public class Main {

  public static void main(String[] args) {
    String[] filelist = { "./file1.txt", "./file2.txt", "./file3.txt", "./file4.txt" }; // 登録するファイルリスト
    Keywords.input("./keyword.txt"); // キーワードを取得

    for (String filepath : filelist) {
      Database.register(filepath); // ファイルを読み込んで登録
    }

    // キーワードを入力する
    Scanner scanner = new Scanner(System.in);
    System.out.print("Keyword Input > ");

    // 検索を実行
    String input = scanner.nextLine();
    scanner.close();

    for (FileSet fileSet : Database.getList()) {
      if (fileSet.contain(input)) { // FileSetのbfindexの中に、キーワードが含まれているか
        System.out.println(fileSet.getFilename() + " is negative");
      } else {
        System.out.println(fileSet.getFilename() + " is false positive");
      }
    }
  }

}