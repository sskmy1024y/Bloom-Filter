/**
 * byteに変換する用のクラス
 */
class ParseByte {

  /**
   * バイト配列を固定16進数文字列に変換する
   */
  public static String toHexString(long hex) {
    String result = Long.toHexString(hex);
    while (result.length() < 16)
      result = "0" + result;
    return "0x" + result;
  }

  /**
   * byte配列を文字列に変換する
   * 
   * @param bytes
   * @return
   */
  public static String toBytesString(byte[] bytes) {
    String result = "0x";
    for (byte n : bytes)
      result += String.format("%02X", n);
    return result;
  }
}
