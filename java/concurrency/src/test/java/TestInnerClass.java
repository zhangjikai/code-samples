import org.junit.Test;

/**
 * Created by Jikai Zhang on 2017/4/16.
 */
public class TestInnerClass {
    int value = 10;




    class InnerClass {
        public void print() {
            System.out.println(value);
        }
    }

    @Test
    public void testPrint() {
        InnerClass innerClass = new InnerClass();
        InnerClass innerClass2 = new InnerClass();
        innerClass.print();
        innerClass2.print();
        value = 20;
        innerClass.print();
        innerClass2.print();
    }

}
