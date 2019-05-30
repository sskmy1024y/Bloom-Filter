import java.security.NoSuchAlgorithmException;

public class BloomFilter {

  private long bfindex; // bfindex

  private int m;
  private int k; // ハッシュの個数

  public BloomFilter() {
    this.bfindex = 0;
    this.m = 64; // 64bit
    this.k = 3; // ハッシュは３つ
  }

  public BloomFilter(int m, int k) {
    this.bfindex = 0;
    this.m = m;
    this.k = k;
  }

  /**
   * ブルームフィルタにデータを追加
   * 
   * @param data
   * @throws NoSuchAlgorithmException
   */
  public void add(String data) throws NoSuchAlgorithmException {
    for (int i = 0; i < k; i++) {
      int hash_value = HashGenerator.generate((i + data).getBytes()); // データにソルトを混ぜてハッシュ化
      int adjust_hash_value = Math.abs(hash_value) % m; // ビット数の間のindex（0 - index - m）に修正
      bfindex = bfindex | pow(2, adjust_hash_value);
    }
  }

  /**
   * ブルームフィルタにデータがあればtrue, なければfalseを返す。
   * 
   * @param keyword
   * @throws NoSuchAlgorithmException
   */
  public boolean contain(String keyword) throws NoSuchAlgorithmException {
    long search_index = 0;
    for (int i = 0; i < k; i++) {
      int hash_value = HashGenerator.generate((i + keyword).getBytes());
      int adjust_hash_value = Math.abs(hash_value) % m;
      search_index = search_index | pow(2, adjust_hash_value);
    }
    return (bfindex & search_index) == search_index;

  }

  /**
   * 16進数に直したbfindexを返す
   * 
   * @return
   */
  public String getBfIndexHex() {
    String hex = Long.toHexString(this.bfindex);
    while (hex.length() < 16)
      hex = "0" + hex;
    return "0x" + hex;
  }

  public static String getBfIndex(String word) throws NoSuchAlgorithmException {
    BloomFilter bf = new BloomFilter();
    bf.add(word);
    return bf.getBfIndexHex();
  }

  /**
   * 1番目の引数を、2番目の引数で累乗した値をint型で返す。
   * 
   * @param num
   * @param n
   * @return
   */
  private static int pow(int num, int n) {
    if (n == 1) {
      return num;
    }
    int ans = pow(num, n - 1) * num;
    return ans;
  }
}
