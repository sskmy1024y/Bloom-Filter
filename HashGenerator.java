import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
  /**
   * ハッシュ関数を生成して正規化も行う関数
   * 
   * @param str
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static int generate(byte[] bytes) throws NoSuchAlgorithmException {
    return MessageDigest.getInstance("SHA1").digest(bytes)[0];
  }
}