import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Test {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public static void main(String[] args) {
    boolean isValid = false;

    try {

      // 引数の数を確認
      if (args.length != 2) {
        System.out.printf("%s%s%s\n", ANSI_RED, "[Error] Insufficient parameters", ANSI_RESET);
        System.exit(1);
      }

      // Step1. キーワードの登録
      System.out.printf("%s%s%s\n", ANSI_YELLOW, "Step1. Keywords Input", ANSI_RESET);
      try {
        Keywords.input(args[0]); // キーワードを登録

        System.out.println("Register keywords:");
        for (String keyword : Keywords.getKeywords()) {
          System.out.println("\t" + ANSI_CYAN + keyword + ANSI_RESET);
        }
      } catch (IOException e) {
        System.out.printf("%s%s%s\n", ANSI_RED, "[Error] Keyword file is not found", ANSI_RESET);
        System.exit(1);
      }

      // Step2. ディレクトリ内のファイルを登録
      System.out.printf("\n%s%s%s\n", ANSI_YELLOW, "Step2. Register Files Input", ANSI_RESET);
      try {
        Database.register(new File(args[1]));

        System.out.println("Register files:");
        for (String filename : Database.getFilesName()) {
          System.out.println("\t" + ANSI_BLUE + filename + ANSI_RESET);
        }

      } catch (IOException e) {
        System.err.printf("%s%s%s\n", ANSI_RED, "[Error] Register files are not found", ANSI_RESET);
        System.exit(1);
      }
      try (Scanner scanner = new Scanner(System.in)) {
        while (true) {

          // Step3. キーワードを入力する
          System.out.printf("\n%s%s%s\n", ANSI_YELLOW, "Step3. Please input Search keyword", ANSI_RESET);
          System.out.print("Search Keyword Input > ");
          String input = scanner.nextLine();

          long inputIndex = BloomFilter.getBfIndex(input);

          System.out.println("\tInput keyword is : \t\t" + ANSI_CYAN + input + ANSI_RESET);
          System.out
              .println("\tInput keyword index :\t\t" + ANSI_PURPLE + ParseByte.toHexString(inputIndex) + ANSI_RESET);

          System.out.print("\tVerified in keyword list :\t");
          if (isValid = Keywords.contains(input)) {
            System.out.println(ANSI_GREEN + "Valid" + ANSI_RESET);
          } else {
            System.out.println(ANSI_RED + "Invalid" + ANSI_RESET);
          }

          // Step4. 検索を実行
          System.out.printf("\n%s%s%s\n", ANSI_YELLOW, "Step4. Searching with bfindex ", ANSI_RESET);
          for (FileSet fileSet : Database.getList()) {

            // FileSetのbfindexの中に、キーワードが含まれているか
            if ((fileSet.getBfIndex() & inputIndex) == inputIndex) {
              System.out.println("\t" + ANSI_BLUE + fileSet.getFilename() + ANSI_RESET + " is " + ANSI_GREEN
                  + "positive" + ANSI_PURPLE + " (file index : " + ParseByte.toHexString(fileSet.getBfIndex())
                  + " & word : " + ParseByte.toHexString(fileSet.getBfIndex() & inputIndex) + ")" + ANSI_RESET);
            } else {
              System.out.println("\t" + ANSI_BLUE + fileSet.getFilename() + ANSI_RESET + " is " + ANSI_RED + "negative"
                  + ANSI_PURPLE + " (file index : " + ParseByte.toHexString(fileSet.getBfIndex()) + " & word : "
                  + ParseByte.toHexString(fileSet.getBfIndex() & inputIndex) + ")" + ANSI_RESET);
            }
          }

          System.out.printf("\n%s%s%s\n", ANSI_YELLOW, "Finish Searching. ", ANSI_RESET);
          if (isValid)
            System.out.println("\tHowever, there is a possibility of false positive.");
        }
      }
    } catch (NoSuchAlgorithmException e) {
      System.err.printf("%s%s%s\n", ANSI_RED, "[Error] Cannnot generate hash index", ANSI_RESET);
      System.exit(1);
    }
  }
}
