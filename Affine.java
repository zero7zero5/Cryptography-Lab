
package cryptography;
import java.util.*;

public class Affine {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the String :");
        String data = scan.nextLine();
        System.out.println("Enter the Key 1:");
        int key1 = scan.nextInt();
         System.out.println("Enter the Key 2:");
        int key2 = scan.nextInt();
        
        System.out.println("Enter 1 to Encrypt or Enter 2 to Decrypt");
        int inp = scan.nextInt();
        if(inp==1) System.out.println("Encrypted :"+encrypt(data,key1,key2));
        else System.out.println("Decrypted :"+decrypt(data,key1,key2));
 
    }
    public static String encrypt(String str, int a, int b){
        String res = "";
        for(char ch:str.toCharArray()){
            if(ch!=' '){
                if(Character.isLowerCase(ch)){
                    char cipher=(char)(((a*(ch-'a')+b)%26)+'a');
                    res+=cipher;
                }
                else {
                    char cipher=(char)(((a*(ch-'A')+b)%26)+'A');
                    res+=cipher;
                }
            }
            else ch+=' ';
        }
        return res;
    }
    public static String decrypt(String str, int a, int b){
        String res = "";
        a=multiplicativeInverse(26,a);
        for(char ch:str.toCharArray()){
            if(ch!=' '){
                if(Character.isLowerCase(ch)){
                    char cipher=(char) (((a *((ch + 'a' - b)) % 26)) + 'a');
                    res+=cipher;
                }
                else {
                    char cipher=(char) (((a *((ch + 'A' - b)) % 26)) + 'A');
                    res+=cipher;
                }
            }
            else ch+=' ';
        }
        return res;
    }
    
    public static int multiplicativeInverse(int a, int b){
        int r1 = a;
        int r2 = b;
        int t1=0;
        int t2=1;
        int t=0;
        int r=0;
        while(r2!=0){
            int q=r1/r2;
            r=r1%r2;
            t=t1-(q*t2);
            r1=r2;
            r2=r;
            t1=t2;
            t2=t;
        }
        if(t1<0) return t1+a;
        return t1;
                
    }
    
    
   
    
    
    
}
