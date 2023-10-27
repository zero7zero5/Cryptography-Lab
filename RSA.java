package Sample;
import java.math.BigInteger;
import java.util.*;
public class RSA {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the Message : ");
        String message = scan.nextLine();
        System.out.println("Enter the value of p");
        String p1 = scan.next();
        System.out.println("Enter the value of q");
        String p2 = scan.next();
        BigInteger p = new BigInteger(p1);
        BigInteger q = new BigInteger(p2);
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.ZERO;
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(phi) < 0; i = i.add(BigInteger.ONE)) {
            if (gcd(phi, i).equals(BigInteger.ONE)) {
                e = i;
                break;
            }
        }
        BigInteger d = modInverse(e, phi);

        BigInteger[] encrypted = encrypt(message, e, n);
        String decrypted = decrypt(encrypted, d, n);

        System.out.println("Original message: " + message);
        System.out.print("Encrypted message: ");
        for (BigInteger num : encrypted) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println("Decrypted message: " + decrypted);
    }
    public static BigInteger modPow(BigInteger base, BigInteger exponent, BigInteger modulus) {
        BigInteger result = BigInteger.ONE;
        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE)) {
                result = result.multiply(base).mod(modulus);
            }
            base = base.multiply(base).mod(modulus);
            exponent = exponent.divide(BigInteger.valueOf(2));
        }
        return result;
    }
    public static BigInteger modInverse(BigInteger a, BigInteger m) {
        BigInteger m0 = m;
        BigInteger x0 = BigInteger.ZERO;
        BigInteger x1 = BigInteger.ONE;

        while (a.compareTo(BigInteger.ONE) > 0) {
            BigInteger q = a.divide(m);
            BigInteger t = m;
            m = a.mod(m);
            a = t;

            t = x0;
            x0 = x1.subtract(q.multiply(x0));
            x1 = t;
        }

        if (x1.compareTo(BigInteger.ZERO) < 0) {
            x1 = x1.add(m0);
        }

        return x1;
    }
    public static BigInteger[] encrypt(String message, BigInteger e, BigInteger n) {
        BigInteger[] encrypted = new BigInteger[message.length()];
        for (int i = 0; i < message.length(); i++) {
            BigInteger m = BigInteger.valueOf((long) message.charAt(i));
            encrypted[i] = modPow(m, e, n);
        }
        return encrypted;
    }
    public static String decrypt(BigInteger[] encrypted, BigInteger d, BigInteger n) {
        StringBuilder decrypted = new StringBuilder();
        for (BigInteger num : encrypted) {
            BigInteger m = modPow(num, d, n);
            decrypted.append((char) m.intValue());
        }
        return decrypted.toString();
    }
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        while (b.compareTo(BigInteger.ZERO) > 0) {
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }
}
