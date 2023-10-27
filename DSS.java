package Sample;
import java.util.Scanner;
public class DSS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the values for p, q, h, k, x, and hf: ");
        int p = sc.nextInt();
        int q = sc.nextInt();
        int h = sc.nextInt();
        int k = sc.nextInt();
        int x = sc.nextInt();
        int hf = sc.nextInt();

        int pow = (p - 1) / q;
        int g = 1;
        for (int i = 0; i < pow; i++) {
            g = (g * h) % p;
        }
        System.out.println("g: " + g);
        System.out.println("Global public keys: p, q, g: " + p + ", " + q + ", " + g);

        int y = 1;
        for (int i = 0; i < x; i++)
            y = (y * g) % p;
        System.out.println("Public key y: " + y);

        int r1 = 1;
        int r = 0;
        for (int i = 0; i < k; i++) {
            r1 = (r1 * g) % p;
            r = r1 % q;
        }
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

        int v1 = 1;
        for (int i = 0; i < n1; i++) {
            v1 = v1 * g;
        }

        int v2 = 1;
        for (int i = 0; i < n21; i++) {
            v2 = v2 * y;
        }

        int v3 = (v1 * v2) % p;
        int v = v3 % q;

        System.out.println("Value of v: " + v);

        if (r == v)
            System.out.println("DSS verified");
        else
            System.out.println("DSS not verified");
    }

    public static int inverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1; // No modular multiplicative inverse exists
    }
}
