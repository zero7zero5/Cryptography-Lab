package Sample;
import java.util.Scanner;
public class HillCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a 2x2 key matrix (space-separated values, row by row): ");
        int[][] key = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                key[i][j] = scanner.nextInt();
            }
        }
        System.out.print("Enter the plaintext (uppercase letters, no spaces): ");
        String plaintext = scanner.next();
        if(plaintext.length()%2!=0) plaintext=plaintext+'a';
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Encrypted Text: " + ciphertext);
        String decryptedText = decrypt(ciphertext, key);
        System.out.println("Decrypted Text: " + decryptedText);
        scanner.close();
    }

    public static String encrypt(String plaintext, int[][] key) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            int[] block = new int[2];
            for (int j = 0; j < 2; j++) {
                block[j] = plaintext.charAt(i + j) - 'A';
            }
            int encryptedChar1 = (key[0][0] * block[0] + key[0][1] * block[1]) % 26;
            int encryptedChar2 = (key[1][0] * block[0] + key[1][1] * block[1]) % 26;
            ciphertext.append((char) (encryptedChar1 + 'A'));
            ciphertext.append((char) (encryptedChar2 + 'A'));
        }
        return ciphertext.toString();
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
