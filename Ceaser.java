
package cryptography;
import java.util.*;
public class Ceaser {

    final static String lowerCase ="abcdefghijklmnopqrstuvwxyz";
    final static String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the String :");
        String data = scan.next();
        System.out.println("Enter the Key :");
        int key = scan.nextInt();
        
        System.out.println("Enter 1 to Encrypt or Enter 2 to Decrypt");
        int inp = scan.nextInt();
        if(inp==1) System.out.println("Encrypted :"+encrypt(data,key));
        else System.out.println("Decrypted :"+decrypt(data,key));
    }
    public static String encrypt(String input,int key){
        StringBuilder str = new StringBuilder();
        for(char ch:input.toCharArray()){
            if(Character.isLowerCase(ch)){
                int index = (lowerCase.indexOf(ch)+key)%26;
                str.append(lowerCase.charAt(index));
            }
            else {
                int index = (upperCase.indexOf(ch)+key)%26;
                str.append(upperCase.charAt(index));
            }
        }
        return str.toString();
    }
    
     public static String decrypt(String input,int key){
        StringBuilder str = new StringBuilder();
        for(char ch:input.toCharArray()){
            if(Character.isLowerCase(ch)){
                int index = (lowerCase.indexOf(ch)-key)%26;
                if(index<0) index=26+index;
                str.append(lowerCase.charAt(index));
            }
            else {
                int index = (upperCase.indexOf(ch)-key)%26;
                if(index<0) index=26+index;
                str.append(upperCase.charAt(index));
            }
        }
        return str.toString();
    }
    
}
