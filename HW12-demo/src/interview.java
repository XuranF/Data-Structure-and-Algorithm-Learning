//"012" -> 12
//edge cases
//character->exception

public class interview {

    public boolean isChar(char c){
        return (c-'a'>=0) && ('z'-c)>= 0;
    }

    public void transform(String ss) throws Exception {
        int length = ss.length();
        int ans = 0;
        int accu = 1;
        for(int i=length-1;i>=0;i--){
            char tmpC = ss.charAt(i);

            // deal with character exception
            if(isChar(tmpC)) throw new Exception("The input contains character!");

            //deal with leading zero cases
            if(tmpC=='0' && ss.charAt(0)=='0')
                break;
            else if(tmpC=='0'){
                accu *= 10;
                continue;
            }

            //main logic
            ans += (tmpC-'0')*accu;
            accu *= 10;
        }
        System.out.println("The extracted integer is " + ans);
    }

    public static void main(){

    }

}
