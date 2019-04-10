import java.util.Arrays;
import java.util.BitSet;

public class BloomFilter {

  private BitSet bfindex; // bfindex

  private int m;
  private int k; // ハッシュの個数

  public BloomFilter(int m, int k) {
    this.bfindex = new BitSet(m);
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
        int hash_value = HashGenerator.generate((i + data).getBytes());
        int adjust_hash_value = Math.abs(hash_value) % m;
        bfindex.set(adjust_hash_value, true);
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
      BitSet search_index = new BitSet(m);
      for (int i = 0; i < k; i++) {
        int hash_value = HashGenerator.generate((i + keyword).getBytes());
        int adjust_hash_value = Math.abs(hash_value) % m;
        search_index.set(adjust_hash_value, true);
      }
      return bfindex.intersects(search_index);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}