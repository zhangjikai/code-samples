import org.junit.Test;

/**
 * Created by Jikai Zhang on 2017/4/19.
 */
public class TestInt {

    @Test
    public void testNegative() {
        int countBits = Integer.SIZE - 3;
        System.out.println(Integer.toBinaryString(-1 << countBits));
        System.out.println(Integer.toBinaryString(0 << countBits));
        System.out.println(Integer.toBinaryString(1 << countBits));
        System.out.println(Integer.toBinaryString(2 << countBits));
        System.out.println(Integer.toBinaryString(3 << countBits));

        System.out.println(-1 << countBits);
        System.out.println(0 << countBits);
        System.out.println((1 << countBits) - 1);
        System.out.println(2 << countBits);
        System.out.println(3 << countBits);


    }
}
