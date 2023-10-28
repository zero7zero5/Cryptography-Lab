package Sample;
public class SHA512 {
    public static void main(String[] args) {
        String input = "Saber Basha";
        byte[] data = input.getBytes();
        byte[] hash = sha512(data);
        String hashedData = bytesToHex(hash);
        System.out.println("SHA-512 Hash: " + hashedData.length());
    }

    public static byte[] sha512(byte[] data) {
        int[] k = {
                0x428A2F98, 0xD728AE22,
                0x71374491, 0x23EF65CD,
                0xB5C0FBCF, 0xEC4D3B2F,
                0xE9B5DBA5, 0x8189DBBC,
                0x3956C25B, 0xF348B538,
                0x59F111F1, 0xB605D019,
                0x923F82A4, 0xAF194F9B,
                0xAB1C5ED5, 0xDA6D8118,
                0xD807AA98, 0xA3030242,
                0x12835B01, 0x45706FBE,
                0x243185BE, 0x4EE4B28C,
                0x550C7DC3, 0xD5FFB4E2,
                0x72BE5D74, 0xF27B896F,
                0x80DEB1FE, 0x3B1696B1,
                0x9BDC06A7, 0x25C71235,
                0xC19BF174, 0xCF692694,
                0xE49B69C1, 0x9EF14AD2,
                0xEFBE4786, 0x384F25E3,
                0x0FC19DC6, 0x8B8CD5B5,
                0x240CA1CC, 0x77AC9C65,
                0x2DE92C6F, 0x592B0275,
                0x4A7484AA, 0x6EA6E483,
                0x5CB0A9DC, 0xBD41FBD4,
                0x76F988DA, 0x831153B5,
                0x983E5152, 0xEE66DFAB,
                0xA831C66D, 0x983E5152,
                0xB00327C8, 0x98FB213F,
                0xBF597FC7, 0xBEEF0EE4,
                0xC6E00BF3, 0x3DA88FC2,
                0xD5A79147, 0x930AA725,
                0x06CA6351, 0xE003826F,
                0x14292967, 0x0A0E6E70,
                0x27B70A85, 0x46D22FFC,
                0x2E1B2138, 0x5C26C926,
                0x4D2C6DFC, 0x5AC42AED,
                0x53380D13, 0x9D95B3DF,
                0x650A7354, 0x8BAF63DE,
                0x766A0ABB, 0x3C77B2A8,
                0x81C2C92E, 0x47EDAEE6,
                0x92722C85, 0x1482353B,
                0xA2BFE8A1, 0x4CF10364,
                0xA81A664B, 0xBC423001,
                0xC24B8B70, 0xD0F89791,
                0xC76C51A3, 0x0654BE30,
                0xD192E819, 0xD6EF5218,
                0xD6990624, 0x5565A910,
                0xF40E3585, 0x5771202A,
                0x106AA070, 0x32BBD1B8,
                0x19A4C116, 0xB8D2D0C8,
                0x1E376C08, 0x5141AB53,
                0x2748774C, 0xDF8EEB99,
                0x34B0BCB5, 0xE19B48A8,
                0x391C0CB3, 0xC5C95A63,
                0x4ED8AA4A, 0xE3418ACB,
                0x5B9CCA4F, 0x7763E373,
                0x682E6FF3, 0xD6B2B8A3,
                0x748F82EE, 0x5DEFB2FC,
                0x78A5636F, 0x43172F60,
                0x84C87814, 0xA1F0AB72,
                0x8CC70208, 0x1A6439EC,
                0x90BEFFFA, 0x23631E28,
                0xA4506CEB, 0xDE82BDE9,
                0xBEF9A3F7, 0xB2C67915,
                0xC67178F2, 0xE372532B,
                0xCA273ECE, 0xEA26619C,
                0xD186B8C7, 0x21C0C207,
                0xEADA7DD6, 0xCDE0EB1E,
                0xF57D4F7F, 0xEE6ED178
        };

        long[] h = {
                0x6a09e667f3bcc908L, 0xbb67ae8584caa73bL, 0x3c6ef372fe94f82bL, 0xa54ff53a5f1d36f1L,
                0x510e527fade682d1L, 0x9b05688c2b3e6c1fL, 0x1f83d9abfb41bd6bL, 0x5be0cd19137e2179L
        };

        int dataLength = data.length;
        long totalBits = dataLength * 8;

        int paddingBytes = (int) ((128 - (totalBits % 128)) / 8);
        if (paddingBytes == 0) {
            paddingBytes = 16;
        }
        byte[] paddedData = new byte[dataLength + paddingBytes];
        System.arraycopy(data, 0, paddedData, 0, dataLength);
        paddedData[dataLength] = (byte) 0x80;

        for (int i = 0; i < 8; i++) {
            paddedData[dataLength + paddingBytes - 1 - i] = (byte) (totalBits & 0xFF);
            totalBits >>= 8;
        }

        // Process the message in 1024-bit blocks
        for (int i = 0; i < paddedData.length; i += 128) {
            // Process a 1024-bit block of data
            long[] w = new long[80];
            for (int j = 0; j < 16; j++) {
                w[j] = bytesToLong(paddedData, i + j * 8);
            }

            for (int j = 16; j < 80; j++) {
                long s0 = rightRotate(w[j - 15], 1) ^ rightRotate(w[j - 15], 8) ^ (w[j - 15] >>> 7);
                long s1 = rightRotate(w[j - 2], 19) ^ rightRotate(w[j - 2], 61) ^ (w[j - 2] >>> 6);
                w[j] = w[j - 16] + s0 + w[j - 7] + s1;
            }

            long a = h[0];
            long b = h[1];
            long c = h[2];
            long d = h[3];
            long e = h[4];
            long f = h[5];
            long g = h[6];
            long z = h[7];

            for (int j = 0; j < 80; j++) {
                long s1 = rightRotate(e, 14) ^ rightRotate(e, 18) ^ rightRotate(e, 41);
                long ch = (e & f) ^ (~e & g);
                long temp1 = z + s1 + ch + k[j] + w[j];
                long s0 = rightRotate(a, 28) ^ rightRotate(a, 34) ^ rightRotate(a, 39);
                long maj = (a & b) ^ (a & c) ^ (b & c);
                long temp2 = s0 + maj;

                z = g;
                g = f;
                f = e;
                e = d + temp1;
                d = c;
                c = b;
                b = a;
                a = temp1 + temp2;
            }

            h[0] += a;
            h[1] += b;
            h[2] += c;
            h[3] += d;
            h[4] += e;
            h[5] += f;
            h[6] += g;
            h[7] += z;
        }

        // Convert the final hash to bytes
        byte[] hash = new byte[64];
        for (int i = 0; i < 8; i++) {
            hash[i * 8] = (byte) (h[i] >>> 56);
            hash[i * 8 + 1] = (byte) (h[i] >>> 48);
            hash[i * 8 + 2] = (byte) (h[i] >>> 40);
            hash[i * 8 + 3] = (byte) (h[i] >>> 32);
            hash[i * 8 + 4] = (byte) (h[i] >>> 24);
            hash[i * 8 + 5] = (byte) (h[i] >>> 16);
            hash[i * 8 + 6] = (byte) (h[i] >>> 8);
            hash[i * 8 + 7] = (byte) h[i];
        }

        return hash;
    }

    public static long bytesToLong(byte[] bytes, int offset) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            if (offset + i < bytes.length) {
                value = (value << 8) | (bytes[offset + i] & 0xFFL);
            }
        }
        return value;
    }

    public static long rightRotate(long value, int count) {
        return (value >>> count) | (value << (64 - count));
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
