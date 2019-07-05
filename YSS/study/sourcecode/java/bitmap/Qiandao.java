package bitmap;

/**
 * @author yuh
 * @date 2019-07-05 08:14
 **/
public class Qiandao {

    private int qiandao;

    public void qiandao(int date) {
        qiandao |= (1 << date - 1);
    }

    public boolean qianle(int date) {
        return (qiandao >> (date - 1) & 1) == 1;
    }

    public int duoshao() {
        int t = 0;
        int temp = qiandao;
        while (temp > 0) {
            temp &= temp - 1;
            t++;
        }
        return t;
    }

    public int qianlefrom(int start, int end) {
        int temp = qiandao;
        temp >>= start - 1;
        temp <<= 32 - end;
        int t = 0;
        while (temp != 0) {
            temp &= temp - 1;
            t++;
        }
        return t;
    }

    public static void main(String[] args) {
        Qiandao qiandao = new Qiandao();
        for (int i = 1; i <= 5; i++) {
            qiandao.qiandao(i);
        }

//        for (int i = 0; i < 10; i++) {
//            System.out.println(qiandao.qianle(i));
//        }

        System.out.println(qiandao.duoshao());
        System.out.println(qiandao.qianlefrom(2,3));
    }
}
