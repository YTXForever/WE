package recall;

/**
 * 正则表达式
 *
 * @author yuh
 * @date 2019-06-07 09:20
 **/
public class Pattern {

    private String pattern;

    public Pattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean match(String str) {
        return _match(str, 0, 0);
    }

    private boolean _match(String str, int pIndex, int sIndex) {
        if (pIndex == pattern.length()) {
            return true;
        }
//        if(sIndex == str.length()){
//            return false;
//        }
        char c = pattern.charAt(pIndex);
        if (c == '*') {
            for (int i = 0; i < str.length() - sIndex; i++) {
                boolean b = _match(str, pIndex + 1, sIndex + i);
                if(b){
                    return true;
                }
            }
        }else if(c == '?'){
            if(_match(str, pIndex + 1, sIndex)){
                return true;
            }
            if(_match(str, pIndex + 1, sIndex+1)){
                return true;
            }
        }else{
            if(str.charAt(sIndex) == pattern.charAt(pIndex)){
                return _match(str,pIndex+1,sIndex+1);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Pattern pattern = new Pattern("*abc");
        boolean res = pattern.match("abc");
        System.out.println(res);
    }
}
