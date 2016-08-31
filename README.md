#Mybatis-Source-Learning


### 简介

关于Mybatis的源码学习

```
annotations - 本包定义了Mybatis框架中的24个注解，本包只被builder.annotation包的
            MapperAnnotationBuilder类引用
binding     - 映射绑定，mapper.xml等映射文件相关实体的抽象
builder     - 解析Mybatis的配置文件和映射文件，包括Xml格式和Annotation格式2种配置
cache       - 包含了Mybatis框架的缓存接口定义和实现，PerpetualCache直接实现了Cache接口，
            其它缓存类实现采用装饰模式实现。采用装饰模式，一个个包装起来，形成一个链，
            典型的就是SynchronizedCache->LoggingCache->SerializedCache->LruCache->PerpetualCache，
            通过链起来达到功能增加。缓存框架按照 Key-Value方式存储，Key的生成采取规则为：
            [hashcode:checksum:mappedStementId:offset:limit:executeSql:queryParams]
            只引用了Mybatis的io包的Resources，不依赖于任何第三方库。Mybatis的其它包
            大量引用了本包中的类和接口，即严重依赖于本包。
cursor      -
datasource  - 数据源相关接口和类，主要引用了Mybatis的reflection的ExceptionUtil类和
            loggin包的Log接口和LogFactory类。Mybatis的session包的Configuration类，
            builder.xml包的XMLConfigBuilder类引用了本包中的类和接口。
exceptions  - Mybatis框架中的异常，只依赖于Mybatis的executor的ErrorContext，Mybatis
            的其它包大量引用了本包中的类和接口，即严重依赖于本包。
executor    - 执行器接口和实现类及周边类和接口
io
```

### 技术交流

博客地址：[http://mynawang.top][1]

QQ群：专注的程序猿 282087535 [立即加入][2]


  [1]: http://mynawang.top
  [2]: http://shang.qq.com/wpa/qunwpa?idkey=632f7c11e0cb5dfc02231352205d9921c50e849a343e4010e4df1c25f59d2e90