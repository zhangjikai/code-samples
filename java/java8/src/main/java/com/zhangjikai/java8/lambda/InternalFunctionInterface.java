package com.zhangjikai.java8.lambda;

import org.junit.Test;

import java.util.function.*;


/**
 *
 * @author Jikai Zhang
 * @date 2017/12/5.
 */
public class InternalFunctionInterface {


    public <T> boolean judge(T t, Predicate<T> p) {
        return p.test(t);
    }

    @Test
    public void testPredicate() {
        String text = "111";
        Predicate<String> a = s -> s != null;
        Predicate<String> b = s -> s.length() > 3;
        System.out.println(judge(text, a.and(b)));
        System.out.println(judge(text, a.negate()));
        System.out.println(judge(text, a.or(b)));
    }

    @Test
    public void testBiPredicate() {
        BiPredicate<Integer, Integer> b = (x, y) -> x > 0 && y > 3;
        boolean r = b.test(1, 4);
        System.out.println(r);
    }

    @Test
    public void testIntPredicate() {
        IntPredicate ip = x -> x > 3;
        boolean r = ip.test(4);
        System.out.println(r);
    }

    public <T> void consume(T t, Consumer<T> c) {
        c.accept(t);
    }

    @Test
    public void testConsume() {
        StringBuilder builder = new StringBuilder();
        Consumer<StringBuilder> a = s -> s.append("abcd");
        Consumer<StringBuilder> b = s -> s.reverse();
        Consumer<StringBuilder> c = s -> s.append("1234");
        consume(builder, a.andThen(b).andThen(c));
        System.out.println(builder.toString());
    }

    @Test
    public void testBiConsumer() {
        BiConsumer<String, String> b = (x, y) -> System.out.println(x + y);
        b.accept("111", "222");
    }

    public <T> T supplier(Supplier<T> s) {
        return s.get();
    }

    @Test
    public void testSupplier() {
        String text = supplier(() -> "1111");
        System.out.println(text);
    }

    public <T, R> R func(T t, Function<T, R> f) {
        return f.apply(t);
    }

    @Test
    public void testFunction() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        int i = func(1, f.andThen(g));
        System.out.println(i);

        i = func(1, f.compose(g));
        System.out.println(i);
    }

    @Test
    public void testUnaryOperator() {
        UnaryOperator<Integer> u = x -> x + 1;
        System.out.println(u.apply(1));
    }

    @Test
    public void testBiFunction() {
        BiFunction<Integer, Double, String> b = (i, d) -> String.valueOf(i + d);
        String r = b.apply(1, 2.5);
        System.out.println(r);
    }

    @Test
    public void testBinaryOperator() {
        BinaryOperator<Integer> b = (x, y) -> x + y;
        int z = b.apply(1, 3);
        System.out.println(z);

        BinaryOperator<Integer> min = BinaryOperator.minBy((x, y) -> x - y);
        z = min.apply(1, 3);
        System.out.println(z);

        BinaryOperator<Integer> max = BinaryOperator.maxBy((x, y) -> x - y);
        z = max.apply(1, 3);
        System.out.println(z);
    }


}
