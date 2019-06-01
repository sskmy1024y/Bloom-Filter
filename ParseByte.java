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
}
