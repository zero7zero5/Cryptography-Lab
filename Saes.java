package Sample;
import java.util.Scanner;
public class Saes {
    private static final int[] sBox = {0x9, 0x4, 0xA, 0xB, 0xD, 0x1, 0x8, 0x5, 0x6, 0x2, 0x0, 0x3, 0xC, 0xE, 0xF, 0x7};
    private static final int[] sBoxI = {0xA, 0x5, 0x9, 0xB, 0x1, 0x7, 0x8, 0xF, 0x6, 0x0, 0x2, 0x3, 0xC, 0x4, 0xD, 0xE};
    private int[] preRoundKey;
    private int[] round1Key;
    private int[] round2Key;
    public Saes(int key) {
        int[][] roundKeys = keyExpansion(key);
        preRoundKey = roundKeys[0];
        round1Key = roundKeys[1];
        round2Key = roundKeys[2];
    }
    private int subWord(int word) {
        return (sBox[(word >> 4)] << 4) + sBox[word & 0x0F];
    }
    private int rotWord(int word) {
        return ((word & 0x0F) << 4) + ((word & 0xF0) >> 4);
    }
    private int[][] keyExpansion(int key) {
        int Rcon1 = 0x80;
        int Rcon2 = 0x30;
        int[] w = new int[6];
        w[0] = (key & 0xFF00) >> 8;
        w[1] = key & 0x00FF;
        w[2] = w[0] ^ (subWord(rotWord(w[1])) ^ Rcon1);
        w[3] = w[2] ^ w[1];
        w[4] = w[2] ^ (subWord(rotWord(w[3])) ^ Rcon2);
        w[5] = w[4] ^ w[3];
        return new int[][] {
                int_to_state((w[0] << 8) + w[1]),
                int_to_state((w[2] << 8) + w[3]),
                int_to_state((w[4] << 8) + w[5])
        };
    }
    private int gf_mult(int a, int b) {
        int product = 0;
        a = a & 0x0F;
        b = b & 0x0F;
        while (a != 0 && b != 0) {
            if ((b & 1) != 0) {
                product ^= a;
            }
            a <<= 1;
            if ((a & (1 << 4)) != 0) {
                a ^= 0b10011;
            }
            b >>= 1;
        }
        return product;
    }
    private int[] int_to_state(int n) {
        return new int[] {(n >> 12) & 0xF, (n >> 4) & 0xF, (n >> 8) & 0xF, n & 0xF};
    }
    private int state_to_int(int[] state) {
        return (state[0] << 12) + (state[2] << 8) + (state[1] << 4) + state[3];
    }
    private int[] add_round_key(int[] s1, int[] s2) {
        int[] result = new int[s1.length];
        for (int i = 0; i < s1.length; i++) {
            result[i] = s1[i] ^ s2[i];
        }
        return result;
    }
    private int[] sub_nibbles(int[] sbox, int[] state) {
        int[] result = new int[state.length];
        for (int i = 0; i < state.length; i++) {
            result[i] = sbox[state[i]];
        }
        return result;
    }
    private int[] shift_rows(int[] state) {
        return new int[]{state[0], state[1], state[3], state[2]};
    }
    private int[] mix_columns(int[] state) {
        return new int[]{
                state[0] ^ gf_mult(4, state[2]),
                state[1] ^ gf_mult(4, state[3]),
                state[2] ^ gf_mult(4, state[0]),
                state[3] ^ gf_mult(4, state[1])
        };
    }
    private int[] inverse_mix_columns(int[] state) {
        return new int[]{
                gf_mult(9, state[0]) ^ gf_mult(2, state[2]),
                gf_mult(9, state[1]) ^ gf_mult(2, state[3]),
                gf_mult(9, state[2]) ^ gf_mult(2, state[0]),
                gf_mult(9, state[3]) ^ gf_mult(2, state[1])
        };
    }
    public int encrypt(int plaintext) {
        int[] state = add_round_key(preRoundKey, int_to_state(plaintext));
        state = mix_columns(shift_rows(sub_nibbles(sBox, state)));
        state = add_round_key(round1Key, state);
        state = shift_rows(sub_nibbles(sBox, state));
        state = add_round_key(round2Key, state);
        return state_to_int(state);
    }
    public int decrypt(int ciphertext) {
        int[] state = add_round_key(round2Key, int_to_state(ciphertext));
        state = sub_nibbles(sBoxI, shift_rows(state));
        state = inverse_mix_columns(add_round_key(round1Key, state));
        state = sub_nibbles(sBoxI, shift_rows(state));
        state = add_round_key(preRoundKey, state);
        return state_to_int(state);
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter key as binary: ");
        String str = s.nextLine();
        int key = Integer.parseInt(str,2);
        Saes aes = new Saes(key);

        System.out.print("Enter plain text in binary: ");
        str = s.nextLine();
        int plaintext = Integer.parseInt(str,2);
        int ciphertext = aes.encrypt(plaintext);
        int decryptedPlaintext = aes.decrypt(ciphertext);
        System.out.println("\n\nOriginal Plaintext: " + Integer.toBinaryString(plaintext));
        System.out.println("Cipher text: " + Integer.toBinaryString(ciphertext));
        System.out.println("Plain text (decrypted): " + Integer.toBinaryString(decryptedPlaintext));
    }
}
