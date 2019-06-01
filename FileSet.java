import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

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
   * @throws NoSuchAlgorithmException
   */
  public FileSet(String filepath) throws IOException, NoSuchAlgorithmException {
    File file = new File(filepath);
    registerBFilter(file);
  }

  /**
   * ファイル名の登録と、bfindexの登録
   * 
   * @param file
   * @throws NoSuchAlgorithmException
   */
  public FileSet(File file) throws IOException, NoSuchAlgorithmException {
    registerBFilter(file);
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
   * bfIndexを返す
   * 
   * @return
   */
  public long getBfIndex() {
    return this.bFilter.getBfIndex();
  }

  private void registerBFilter(File file) throws IOException, NoSuchAlgorithmException {
    this.filename = file.getName(); // ファイル名を登録
    byte[] filebyte = Files.readAllBytes(file.toPath()); // ファイルのByte配列を取得

    // bfindexの登録
    this.bFilter = new BloomFilter();
    for (String keyword : Keywords.containKeywords(filebyte)) {
      this.bFilter.add(keyword.getBytes());
    }
  }

}
