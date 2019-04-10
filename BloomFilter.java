public class BloomFilter {

  private long bfindex; // bfindex

  private int m;
  private int k; // ハッシュの個数

  public BloomFilter(int m, int k) {
    this.bfindex = Long.MIN_VALUE;
    this.m = m;
    this.k = k;
  }

  /**
   * ブルームフィルタにデータを追加
   * 
   * @param data
   */
  public void add(String data) {
    try {
      for (int i = 0; i < k; i++) {
        int hash_value = HashGenerator.generate((i + data).getBytes()); // データにソルトを混ぜてハッシュ化
        int adjust_hash_value = Math.abs(hash_value) % m; // ビット数の間のindex（0 - index - m）に修正
        bfindex = bfindex | pow(2, adjust_hash_value);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * ブルームフィルタにデータがあればtrue, なければfalseを返す。
   * 
   * @param keyword
   */
  public boolean contain(String keyword) {
    try {
      long search_index = Long.MIN_VALUE;
      for (int i = 0; i < k; i++) {
        int hash_value = HashGenerator.generate((i + keyword).getBytes());
        int adjust_hash_value = Math.abs(hash_value) % m;
        search_index = search_index | pow(2, adjust_hash_value);
      }
      return (bfindex & search_index) != Long.MIN_VALUE;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
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