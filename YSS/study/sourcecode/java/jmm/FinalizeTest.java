package jmm;

/**
 * @author yuh
 * @date 2019-05-29 18:21
 **/
public class FinalizeTest {

    private static FinalizeTest instance = null;

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalized executing");
        instance = this;
        System.out.println("finalized executed");
    }

    public static void main(String[] args) throws InterruptedException {
        FinalizeTest finalizeTest = new FinalizeTest();
        System.out.println(finalizeTest);
        finalizeTest = null;
        System.gc();
        Thread.sleep(1000);
        System.out.println(FinalizeTest.instance);
        FinalizeTest.instance = null;
        System.out.println("==============");

        System.gc();
        Thread.sleep(1000);
        System.out.println(FinalizeTest.instance);

    }

}
