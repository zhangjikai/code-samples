/**
 * 枚举使用示例
 * 使用枚举的值 EnumUse.POSITIVE.toString();
 */
public enum EnumUse {
    POSITIVE("pos"), NEGATIVE("neg"), ZERO("zero");

    private String value;

    private EnumUse(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

class Test{
    public static void main(String[] args) {
        System.out.println(EnumUse.POSITIVE);
    }
}
