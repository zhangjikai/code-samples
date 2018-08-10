package com.zhangjikai.unsafe;

import org.junit.Test;
import static org.junit.Assert.*;
import sun.misc.Unsafe;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


/**
 * 原文地址： http://mydailyjava.blogspot.com/2013/12/sunmiscunsafe.html
 *
 * Created by Jikai Zhang on 2017/4/8.
 */
public class UnsafeTest {

    /**
     * 直接使用 Unsafe.getUnsafe() 会抛出 Exception in thread "main" java.lang.SecurityException: Unsafe
     * 因为 getUnsafe 方法会检查调用对象的 ClassLoader，只有类加载器为 null，也就是当前类使用 bootstrap class loader 加载时，
     * 才可以获得 Unsafe 对象。一般 non-core Java 类是不会使用 bootstrap class loader 加载的。
     */
    public static void testGetUnSafe() {
        Unsafe unsafe = Unsafe.getUnsafe();
    }


    /**
     * 获得 Unsafe 对象。Unsafe 类使用单例模式，将 Unsafe 对象的实例保存在私有 final 变量 theUnsafe 中，并且使用 static 代码块进行了初始化，
     * 因此我们可以通过反射的方式获得 Unsafe 类的 theUnsafe 变量，通过该变量获得 Unsafe 实例。需要注意的是在不同的 JVM 实现中，Unsafe 类的实现
     * 方式会有所不同，也就是说其他的 JVM 中，Unsafe 中私有变量的名称不一定叫 theUnsafe。
     * <p>
     * 另外也可以通过反射调用 Unsafe 私有构造方法的方式来创建新的实例。和上面的方式相比，这种方法更通用一些，但是缺点是： 1. 创建了新的对象 2. 如果 Unsafe 类在静态代码块中 初始化 Unsafe 实例时进行
     * 了额外的操作，使用该方法还要把额外的操作再执行一遍，相对会繁琐一些（当然现在的实现中没有做额外的操作，所以不用担心这种情况，但是以后的版本就不能确定了）。
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Unsafe getUnsafe() {

        Unsafe unsafe = null;
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);

            Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
            unsafeConstructor.setAccessible(true);
            Unsafe unsafe2 = unsafeConstructor.newInstance();
        } catch (NoSuchFieldException | InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return unsafe;
    }


    static class DemoClass {
        private final int value;

        private DemoClass() {
            value = doExpensiveLookup();
        }

        private int doExpensiveLookup() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 不调用构造方法创建实例。对于 DemoClass 类，其构造方法会休眠两秒，我们执行下面代码会发现程序并没有停顿，
     * 并且 value 的值被赋成初始值，所以我们没有调用构造方法从而创建了实例。
     *
     * @throws InstantiationException
     */
    @Test
    public void createInstance() throws InstantiationException {

        DemoClass instance = (DemoClass) getUnsafe().allocateInstance(DemoClass.class);
        System.out.println(instance.getValue());
    }

    /**
     * 通过 ReflectionFactory，我们将其他类的构造方法指定为当前类的构造方法，在下面的例子中，我们将 Object 类的构造方法指定给了 DemoClass，也就是说创建的实例类型是 DemoClass，
     * 当时调用的构造方法是 Object 的。
     *
     * @throws Exception
     */
    @Test
    public void createInstanceWithReflectionFactory() throws Exception {
        Constructor<DemoClass> silentConstructor = (Constructor<DemoClass>) ReflectionFactory.getReflectionFactory().newConstructorForSerialization(DemoClass.class, Object.class.getConstructor());

        silentConstructor.setAccessible(true);
        DemoClass demoClass = silentConstructor.newInstance();
        System.out.println(demoClass.getValue());
    }

    static class OtherClass{
        private final int value;
        private final int unknownValue;
        private OtherClass() {
            System.out.println("in OtherClass Constructor");
            value = 10;
            unknownValue = 20;
        }
    }

    /**
     * 在下面的示例中，DemoClass 将调用 OtherClass 的无参构造方法进行初始化。在调用的时候，对于 DemoClass 中有的属性（value）会赋予对应的值（10），对于 DemoClass 中没有的属性（unknownValue）,
     * 会忽略这个属性。
     *
     * @throws Exception
     */
    @Test
    public void createInstanceWithReflectionFactory2() throws Exception{
        Constructor<DemoClass> silentConstructor = (Constructor<DemoClass>) ReflectionFactory.getReflectionFactory().newConstructorForSerialization(DemoClass.class, OtherClass.class.getDeclaredConstructor());

        silentConstructor.setAccessible(true);
        DemoClass demoClass = silentConstructor.newInstance();
        assertEquals(10, demoClass.getValue());
        assertEquals(DemoClass.class, demoClass.getClass());
        assertEquals(Object.class, demoClass.getClass().getSuperclass());
    }

    @Test
    public void testArray() {
        int array[] = new int[(int) (Integer.MAX_VALUE+1)];
        System.out.println(array.length);
    }



    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        //testGetUnSafe();
        getUnsafe();
    }
}
