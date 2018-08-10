/**
 * Created by Jikai Zhang on 2017/4/8.
 */
public class TestFinal {

    private final void print() {
        System.out.println(1111);
    }

    static class TestFinalChild extends TestFinal {
        private final void print() {
            System.out.println(2222);
        }
    }

    public static void main(String[] args) {
        TestFinal testFinal = new TestFinalChild();
        testFinal.print();
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
