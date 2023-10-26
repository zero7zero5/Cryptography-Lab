package Sample;
import java.util.*;

public class Sample {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Decrypted String :");
        String s = scan.nextLine();
        for(int i=1;i<27;i++){
            for(int j=0;j<27;j++){
                System.out.println("KEY 1:"+i+" KEY :"+j+" STRING : "+decrypt(s, i, j));
            }
        }
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
