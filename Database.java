import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Database {

  private static List<Database> list = new ArrayList<>(); // データベースを保持するリスト

  private String filename;
  private BloomFilter bFilter;

  /**
   * staticメソッド。DBにファイルを登録
   * 
   * @param filepath
   */
  public static void register(String filepath) {
    Database db = new Database(filepath);
    list.add(db);
  }

  /**
   * staticメソッド。Positiveなファイルのファイル名を返す
   * 
   * @param keyword
   * @return
   */
  public static List<String> getPositiveName(String keyword) {
    List<String> result = new ArrayList<>();
    for (Database db : list) {
      if (db.contain(keyword))
        result.add(db.getFilename());
    }
    return result;
  }

  /**
   * staticメソッド。データベースのリストを返す
   * 
   * @return
   */
  public static List<Database> getList() {
    return list;
  }

  /**
   * ファイル名の登録と、bfindexの登録
   * 
   * @param filepath
   */
  private Database(String filepath) {
    try {
      File file = new File(filepath);

      this.filename = file.getName(); // ファイル名を登録
      byte[] filebyte = Files.readAllBytes(file.toPath()); // ファイルのByte配列を取得

      // bfindexの登録
      this.bFilter = new BloomFilter(64, 3);
      for (String keyword : Keywords.containKeywords(filebyte)) {
        this.bFilter.add(keyword);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * ファイル名の取得
   * 
   * @return
   */
  public String getFilename() {
    return this.filename;
  }

  /**
   * bfindexのなかにキーワードが含まれるかどうか
   * 
   * @param keyword
   * @return
   */
  public boolean contain(String keyword) {
    return this.bFilter.contain(keyword);
  }

}