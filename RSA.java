package Sample;
import java.util.*;

public class Main {
    public static void main(String[] args){
        long p = 7919;
        long q = 7907;
        long n = p * q;
        long phi = (p - 1) * (q - 1);
        long e=0;
        for(long i=2;i<phi;i++){
            if(gcd(phi,i)==1) {
                e=i;
                break;
            }
        }
        long d = modInverse(e, phi);
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the Message : ");
        String message = scan.nextLine();
        long[] encrypted = encrypt(message, e, n);
        String decrypted = decrypt(encrypted, d, n);

        System.out.println("Original message: " + message);
        System.out.print("Encrypted message: ");
        for (long num : encrypted) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println("Decrypted message: " + decrypted);
        }
        
    public static long modPow(long base, long exponent, long modulus) {
        long result = 1L;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent /= 2;
        }
        return result;
    }
    public static long modInverse(long a, long m) {
        long m0 = m;
        long x0 = 0L;
        long x1 = 1L;

        while (a > 1) {
            long q = a / m;
            long t = m;
            m = a % m;
            a = t;

            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0) {
            x1 += m0;
        }

        return x1;
    }
    public static long[] encrypt(String message, long e, long n) {
        long[] encrypted = new long[message.length()];
        for (int i = 0; i < message.length(); i++) {
            long m = (long) message.charAt(i);
            encrypted[i] = modPow(m, e, n);
        }
        return encrypted;
    }
    public static String decrypt(long[] encrypted, long d, long n) {
        StringBuilder decrypted = new StringBuilder();
        for (long num : encrypted) {
            long m = modPow(num, d, n);
            decrypted.append((char) m);
        }
        return decrypted.toString();
    }
    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
