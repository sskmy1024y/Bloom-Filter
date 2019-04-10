import java.util.ArrayList;
import java.util.List;

class Database {

  private static List<FileSet> list = new ArrayList<>(); // FileSetクラスを保持するリスト

  /**
   * staticメソッド。DBにファイルを登録
   * 
   * @param filepath
   */
  public static void register(String filepath) {
    FileSet fileSet = new FileSet(filepath);
    list.add(fileSet);
  }

  /**
   * staticメソッド。Positiveなファイルのファイル名を返す
   * 
   * @param keyword
   * @return
   */
  public static List<String> getPositiveName(String keyword) {
    List<String> result = new ArrayList<>();
    for (FileSet fileSet : list) {
      if (fileSet.contain(keyword))
        result.add(fileSet.getFilename());
    }
    return result;
  }

  /**
   * staticメソッド。FileSetのリストを返す
   * 
   * @return
   */
  public static List<FileSet> getList() {
    return list;
  }

}