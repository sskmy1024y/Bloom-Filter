import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Keywords {
  private static List<String> keywords;

  /**
   * キーワードをファイルから登録する
   * 
   * @param filepath
   */
  public static void input(String filepath) {

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8));) {
      String str;
      keywords = new ArrayList<>();
      while ((str = reader.readLine()) != null) {
        keywords.add(str);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * キーワード一覧を返す
   * 
   * @return
   */
  public static List<String> getKeywords() {
    return keywords;
  }

  public static void print() {
    System.out.println(keywords.toString());
  }

  /**
   * 引数に与えられたファイル内に存在するキーワードのリストを返す
   * 
   * @param dest
   * @return
   */
  public static List<String> containKeywords(byte[] dest) {
    List<String> result = new ArrayList<>();
    for (String keyword : keywords) {
      if (contain(dest, keyword.getBytes()))
        result.add(keyword);
    }
    return result;
  }

  /**
   * byte配列を比較
   * 
   * @param org
   * @param dest
   * @return
   */
  public static boolean contain(byte[] org, byte[] dest) {
    if ((org == null) || (dest == null)) {
      throw new IllegalArgumentException("`org` or `dest` is null.");
    }

    if ((org.length == 0) || (dest.length == 0) // 長さが無いので比較しようが無い
        || (org.length < dest.length)) // 探したい配列の方が長いので見つかるワケがない
    {
      return false;
    }

    // 評価する限界値
    int limitIndex = org.length - dest.length + 1;

    for (int i = 0; i < limitIndex; i++) {
      for (int j = 0; j < dest.length; j++) { // (A)
        if (org[i + j] == dest[j]) { // (B)
          // 最後まで一致したら戻り値に設定する
          if (j == dest.length - 1) {
            return true;
          }
        } else {
          break;
        }
      }
    }
    // 最後まで見つからなかった
    return false;
  }
}