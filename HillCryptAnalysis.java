package Sample;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class HillCryptAnalysis {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the decrypted text : ");
        String decryptedText = scanner.next();
        if(decryptedText.length()%2!=0) decryptedText+='a';
        List<String> possiblePlaintexts = findPossiblePlaintexts(decryptedText);
        if (possiblePlaintexts.isEmpty()) {
            System.out.println("No valid plaintexts found.");
        } else {
            System.out.println("Possible plaintexts:");
            for (String plaintext : possiblePlaintexts) {
                System.out.println(plaintext);
            }
        }
    }
    public static List<String> findPossiblePlaintexts(String decryptedText) {
        List<String> possiblePlaintexts = new ArrayList<>();
        for (int a = 0; a < 26; a++) {
            for (int b = 0; b < 26; b++) {
                for (int c = 0; c < 26; c++) {
                    for (int d = 0; d < 26; d++) {
                        int[][] key = {{a, b}, {c, d}};
                        String plaintext = decrypt(decryptedText, key);
                        if (!plaintext.isEmpty()) {
                            char a1 = (char)(a+'A');
                            char b1 = (char)(b+'A');
                            char c1 = (char)(c+'A');
                            char d1 = (char)(d+'A');
                            possiblePlaintexts.add("Key: "+a1+""+b1+""+c1+""+d1+" "+"PlainText: "+plaintext);
                        }
                    }
                }
            }
        }
        return possiblePlaintexts;
    }
    public static String decrypt(String ciphertext, int[][] key) {
        StringBuilder plaintext = new StringBuilder();
        int det = (key[0][0] * key[1][1] - key[0][1] * key[1][0] + 26) % 26;
        int detInverse = -1;
        for (int i = 1; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInverse = i;
                break;
            }
        }
        if (detInverse == -1) {
            System.out.println("Key is not invertible.");
            return "";
        }
        int keyInverse[][] = new int[2][2];
        keyInverse[0][0] = (key[1][1] * detInverse) % 26;
        keyInverse[0][1] = (26 - key[0][1]) * detInverse % 26;
        keyInverse[1][0] = (26 - key[1][0]) * detInverse % 26;
        keyInverse[1][1] = (key[0][0] * detInverse) % 26;
        for (int i = 0; i < ciphertext.length(); i += 2) {
            int[] block = new int[2];
            for (int j = 0; j < 2; j++) {
                block[j] = ciphertext.charAt(i + j) - 'A';
            }
            int decryptedChar1 = (keyInverse[0][0] * block[0] + keyInverse[0][1] * block[1] + 26) % 26;
            int decryptedChar2 = (keyInverse[1][0] * block[0] + keyInverse[1][1] * block[1] + 26) % 26;
            plaintext.append((char) (decryptedChar1 + 'A'));
            plaintext.append((char) (decryptedChar2 + 'A'));
        }
        return plaintext.toString();
    }
}
