import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * ファイルの名前とbfindexを格納するクラス
 */
class FileSet {

  private String filename; // ファイル名を格納
  private BloomFilter bFilter; // BloomFilterクラスを格納

  /**
   * ファイル名の登録と、bfindexの登録
   * 
   * @param filepath
   */
  public FileSet(String filepath) {
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