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
   * ブルームフィルタにキーワードを追加
   * 
   * @param data
   * @throws NoSuchAlgorithmException
   */
  public void add(byte[] datas) throws NoSuchAlgorithmException {
    for (int i = 0; i < k; i++) {
      byte hash = (byte) i;
      int hash_value = HashGenerator.generate(datas, hash); // データにソルトを混ぜてハッシュ化
      int adjust_hash_value = Math.abs(hash_value) % m; // ビット数の間のindex（0 - index - m）に修正
      bfindex = bfindex | pow(2, adjust_hash_value);
    }
  }

  public long getBfIndex() {
    return this.bfindex;
  }

  /**
   * bfindexを作成してindexHexを取得する
   * 
   * @param word
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static long getBfIndex(String keyword) throws NoSuchAlgorithmException {
    BloomFilter bf = new BloomFilter();
    bf.add(keyword.getBytes());
    return bf.getBfIndex();
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
