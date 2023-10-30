package Sample;
import java.util.Scanner;
public class DSS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int p, q, h, k, x, hf;
        System.out.println("Digital Signature Algorithm (DSA)");
        System.out.print("Enter a prime number p: ");
        p = getValidIntegerInput(sc);
        System.out.print("Enter a prime number q (must be smaller than p): ");
        q = getValidIntegerInput(sc);
        while (q >= p || !isPrime(q)) {
            System.out.print("q must be smaller than p and a prime number. Enter q again: ");
            q = getValidIntegerInput(sc);
        }
        System.out.print("Enter the base parameter h (usually a primitive root of p): ");
        h = getValidIntegerInput(sc);
        System.out.print("Enter the secret value k: ");
        k = getValidIntegerInput(sc);
        System.out.print("Enter your private key x: ");
        x = getValidIntegerInput(sc);
        System.out.print("Enter the message digest hf: ");
        hf = getValidIntegerInput(sc);
        int g = calculateGenerator(h, p, q);
        System.out.println("g: " + g);
        System.out.println("Global public keys: p, q, g: " + p + ", " + q + ", " + g);
        int y = calculatePublicKey(g, p, x);
        System.out.println("Public key y: " + y);
        int r = calculateR(g, p, q, k);
        System.out.println("Value of r: " + r);
        int invert = inverse(k, q);
        int t = x * r;
        int s1 = invert * (hf + t);
        int st = -s1;
        while (st > q)
            st = st % q;
        int s = q - st;
        System.out.println("Value of s: " + s);
        int w = inverse(s, q);
        System.out.println("w: " + w);
        int n1 = (hf * w) % q;
        int n2 = (r * w);
        int st1 = -n2;
        if (st1 > q)
            st1 = st1 % q;
        int n21 = q - st1;
        int v1 = calculatePowMod(g, n1, p);
        int v2 = calculatePowMod(y, n21, p);
        int v3 = (v1 * v2) % p;
        int v = v3 % q;
        System.out.println("Value of v: " + v);
        if (r == v)
            System.out.println("DSS verified");
        else
            System.out.println("DSS not verified");
    }
    public static int getValidIntegerInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter an integer: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static int calculateGenerator(int base, int prime, int subgroupOrder) {
        int g = 1;
        for (int i = 0; i < (prime - 1) / subgroupOrder; i++) {
            g = (g * base) % prime;
        }
        return g;
    }
    public static int calculatePublicKey(int generator, int prime, int privateKey) {
        return calculatePowMod(generator, privateKey, prime);
    }
    public static int calculateR(int generator, int prime, int subgroupOrder, int k) {
        return calculatePowMod(generator, k, prime) % subgroupOrder;
    }
    public static int calculatePowMod(int base, int exponent, int modulo) {
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulo;
            }
            base = (base * base) % modulo;
            exponent /= 2;
        }
        return result;
    }
    public static int inverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }
}
