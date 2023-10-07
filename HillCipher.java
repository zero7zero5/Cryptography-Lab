import java.util.Scanner;

public class HillCipher {

    public static void main(String[] args) {
        Scanner ds = new Scanner(System.in);
        String k, p;
        char l;
        System.out.println("Enter Key : ");
        k = ds.nextLine();
        k = k.toLowerCase();
        while (k.length() < 4) {
            k += 'a';
        }
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int[][] a = new int[2][2];
        int x = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                l = k.charAt(x);
                a[i][j] = alphabet.indexOf(l);
                x++;
            }
        }
        System.out.println("Enter Plaintext : ");
        p = ds.nextLine();

        // Encryption
        StringBuilder ciphertext = new StringBuilder();
        int padding = 0;
        if (p.length() % 2 != 0) {
            p += 'x'; 
            padding = 1;
        }

        for (int i = 0; i < p.length(); i += 2) {
            int[] block = new int[2];
            block[0] = alphabet.indexOf(p.charAt(i));
            block[1] = alphabet.indexOf(p.charAt(i + 1));

            int X = (a[0][0] * block[0] + a[0][1] * block[1]) % 26;
            int Y = (a[1][0] * block[0] + a[1][1] * block[1]) % 26;

            ciphertext.append(alphabet.charAt(X));
            ciphertext.append(alphabet.charAt(Y));
        }

        System.out.println("Ciphertext : " + ciphertext);

        // Decryption
        String c = ciphertext.toString();

        StringBuilder plaintext = new StringBuilder();
        int[][] aInverse = new int[2][2];

        // Calculate the modular inverse of matrix 'a'
        int det = (a[0][0] * a[1][1] - a[0][1] * a[1][0] + 26) % 26;
        int detInverse = 0;
        for (int i = 0; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInverse = i;
                break;
            }
        }

        // Calculate the inverse of matrix 'a'
        aInverse[0][0] = (a[1][1] * detInverse) % 26;
        aInverse[0][1] = ((-a[0][1]) * detInverse + 26) % 26;
        aInverse[1][0] = ((-a[1][0]) * detInverse + 26) % 26;
        aInverse[1][1] = (a[0][0] * detInverse) % 26;

        for (int i = 0; i < c.length(); i += 2) {
            int[] block = new int[2];
            block[0] = alphabet.indexOf(c.charAt(i));
            block[1] = alphabet.indexOf(c.charAt(i + 1));

            int decryptedX = (aInverse[0][0] * block[0] + aInverse[0][1] * block[1]) % 26;
            int decryptedY = (aInverse[1][0] * block[0] + aInverse[1][1] * block[1]) % 26;

            plaintext.append(alphabet.charAt((decryptedX + 26) % 26));
            plaintext.append(alphabet.charAt((decryptedY + 26) % 26));
        }

        // Remove padding characters if necessary
        if (padding == 1) {
            plaintext.setLength(plaintext.length() - 1);
        }

        System.out.println("Decrypted Plaintext : " + plaintext);
    }
}