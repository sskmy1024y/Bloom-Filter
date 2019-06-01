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
    return MessageDigest.getInstance("SHA-256").digest(bytes)[0];
  }

  /**
   * soltを使ってハッシュを生成し、正規化も行う関数
   * 
   * @param bytes
   * @param solt
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static int generate(byte[] bytes, byte solt) throws NoSuchAlgorithmException {
    byte[] temp = new byte[bytes.length + 1];
    System.arraycopy(bytes, 0, temp, 0, bytes.length);
    temp[bytes.length - 1] = solt;
    return MessageDigest.getInstance("SHA1").digest(temp)[0];
  }
}
