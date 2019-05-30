import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

class Database {

  private static List<FileSet> list = new ArrayList<>(); // FileSetクラスを保持するリスト

  /**
   * staticメソッド。DBにファイルを登録
   * 
   * @param filepath
   * @throws NoSuchAlgorithmException
   */
  public static void register(String filepath) throws IOException, NoSuchAlgorithmException {
    FileSet fileSet = new FileSet(filepath);
    list.add(fileSet);
  }

  /**
   * staticメソッド。指定したディレクトリ内のファイルを登録
   * 
   * @param dirpath
   * @throws NoSuchAlgorithmException
   */
  public static void register(File dirpath) throws IOException, NoSuchAlgorithmException {
    File[] filelist = dirpath.listFiles();
    for (File file : filelist) {
      FileSet fileSet = new FileSet(file);
      list.add(fileSet);
    }
  }

  /**
   * staticメソッド。Positiveなファイルのファイル名を返す
   * 
   * @param keyword
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static List<String> getPositiveName(String keyword) throws NoSuchAlgorithmException {
    List<String> result = new ArrayList<>();
    for (FileSet fileSet : list) {
      if (fileSet.contain(keyword))
        result.add(fileSet.getFilename());
    }
    return result;
  }

  /**
   * データベースに登録されているファイル名を返す。
   * 
   * @return
   */
  public static List<String> getFilesName() {
    List<String> result = new ArrayList<>();
    for (FileSet fileSet : list) {
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
