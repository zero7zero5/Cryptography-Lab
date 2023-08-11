
package cryptography;
import java.util.*;

public class TranspositionCipher {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the String :");
        String data = scan.nextLine();
        System.out.println("Enter 1 to Encrypt or Enter 2 to Decrypt");
        int inp = scan.nextInt();
        if(inp==1) System.out.println("Encrypted :"+encrypt(data));
        else System.out.println("Decrypted :"+decrypt(data));
    }
    public static String encrypt(String s){
        StringBuilder str = new StringBuilder();
        int i=0;
        int j=s.length()-1;
        while(i<=j){
            if(i==j){
                str.append(s.charAt(j));    
            }
            else{
                str.append(s.charAt(i));
                str.append(s.charAt(j));
            }
            i++;
            j--;
            
        }
        return str.toString();
                
    }
    public static String decrypt(String s){
        char[] array = new char[s.length()];
        int i=0;
        int j=s.length()-1;
        for(int index=0;index<s.length();index++){
            if(index%2==0){
                array[i]=s.charAt(index);
                i++;
            }
            else{
                array[j]=s.charAt(index);
                j--;
            }
        }
        return new String(array);
    }
}
