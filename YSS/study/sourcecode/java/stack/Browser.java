package stack;

/**
 * 模拟浏览器操作
 * 分析问题要从宏观出发
 * 不要陷入一个子问题不能自拔
 * @author yuh
 * @date 2019-05-30 07:22
 **/
public class Browser {

    private ArrayStack<String> forward;
    private ArrayStack<String> backward;
    private String curr = null;
    private String pre = null;


    public Browser() {
        forward = new ArrayStack<>(5);
        backward = new ArrayStack<>(5);
    }

    public void req(String html){
       curr = html;
       if(pre !=null){
           backward.push(pre);
       }
       pre = html;
    }

    public void back(){
        forward.push(curr);
        String pop = backward.pop();
        curr = pop;
        System.out.println(pop==null?"BLANK":pop);
    }

    public void forward(){
        backward.push(curr);
        String pop = forward.pop();
        curr = pop;
        System.out.println(pop==null?"BLANK":pop);
    }

    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.req("aaa");
        browser.req("bbb");
        browser.back();//aaa
        browser.forward();//bbb
        browser.back();//aaa
        browser.back();//BLANK
    }
}
