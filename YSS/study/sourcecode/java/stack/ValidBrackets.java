package stack;

/**
 * @author yuh
 * @date 2019-05-30 07:06
 **/
public class ValidBrackets {

    public static boolean isValidBrackets(String brackets){
        if(brackets == null || brackets.length()==0){
            return false;
        }

        ArrayStack<Character> characterArrayStack = new ArrayStack<>(brackets.length());
        char[] chars = brackets.toCharArray();
        for (char aChar : chars) {
            if(aChar == '{' || aChar == '[' || aChar == '('){
                characterArrayStack.push(aChar);
            }else{
                Character pop = characterArrayStack.pop();
                if(!((aChar == '}' && pop == '{')
                        ||((aChar == ']' && pop == '['))
                        || ((aChar == ')' && pop == '(')))){
                    return false;
                }
            }
        }
        return characterArrayStack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValidBrackets("[{]}]"));
    }
}
