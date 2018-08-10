# Effective Java 
## 创建和销毁对象
1. 考虑使用静态工厂方法代替构造函数
    + 常用命名规则:
        - from - 类型转换方法,接受一个参数返回相关的实例
        - of - 聚合方法, 接收多个参数, 返回相应的实例
        - valueOf - A more verbose alternative to from and of
        - instance / getInstance - 根据方法参数返回对应的实例, 但是不能够说与参数具有同样的值.
        - create / newInstance - 每次都返回一个新的对象
        - get<Type> - 和 getInstance 类似, 在工厂方法位于其他的类中时使用. Type 表示工厂方法返回的数据类型.
            ```
            FileStore fs = Files.getFileStore(path);
            ```
        - new<Type> - 返回新的对象
        - type - A concise alternative to getType and newType