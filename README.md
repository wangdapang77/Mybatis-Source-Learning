#Mybatis-Source-Learning


### 简介

关于Mybatis的源码学习

annotations -》 本包定义了Mybatis框架中的24个注解，本包只被builder.annotation包的
                MapperAnnotationBuilder类引用

binding     -》 映射绑定，mapper.xml等映射文件相关实体的抽象

builder     -》 解析Mybatis的配置文件和映射文件，包括Xml格式和Annotation格式2种配置

cache       -》 包含了Mybatis框架的缓存接口定义和实现，PerpetualCache直接实现了Cache接口，
                其它缓存类实现采用装饰模式实现。采用装饰模式，一个个包装起来，形成一个链，
                典型的就是SynchronizedCache->LoggingCache->SerializedCache->LruCache->PerpetualCache，
                通过链起来达到功能增加。缓存框架按照 Key-Value方式存储，Key的生成采取规则为：
                [hashcode:checksum:mappedStementId:offset:limit:executeSql:queryParams]
                只引用了Mybatis的io包的Resources，不依赖于任何第三方库。Mybatis的其它包
                大量引用了本包中的类和接口，**即严重依赖于本包**

cursor      -》

datasource  -》 数据源相关接口和类，主要引用了Mybatis的reflection的ExceptionUtil类和
                loggin包的Log接口和LogFactory类。Mybatis的session包的Configuration类，
                builder.xml包的XMLConfigBuilder类引用了本包中的类和接口

exceptions  -》 Mybatis框架中的异常，只依赖于Mybatis的executor的ErrorContext，Mybatis
                的其它包大量引用了本包中的类和接口，**即严重依赖于本包**

executor    -》 执行器接口和实现类及周边类和接口

io          -》 主要包含了资源加载和访问相关的类，只引用了Mybatis的logging包的Log接口和
                LogFactory类，Mybatis的其它包大量引用了本包中的类和接口，**即严重依赖于本包**

jdbc        -》 JDBC和SQL相关的类

logging     -》 把日志抽象成Log接口，该接口有7种实现：Apache Commons Logging、
                JDBC Logging、Java Util Logging、Log4j、No Logging、Slf4J、Stdout
                一个接口多种实现是框架的一贯作风，主要依赖了，Mybatis的reflection包
                的ExceptionUtil和io包的Resources，以及第三方的Log4j,Slf4j,CommonsLogging
                Mybatis的其它包大量引用了本包中的类和接口，**即严重依赖于本包**

mapping     -》 Mybatis配置文件-映射文件相关的类

parsing     -》 解析配置文件的核心类和接口

plugin      -》 插件相关接口和类

reflection  -》 反射相关类

scripting   -》 脚本解析相关类

session     -》 会话相关类，提供对外核心接口

transaction -》 Transaction接口是对事务的抽象，有2种实现方式：1.JdbcTransaction,jdbc:手动管理
                2.ManagedTransaction,managed:container manage the full lifecycle of the transaction
                TransactionFactory接口定义了生成Transaction接口(实现类)的若干方法。
                该接口有2种实现方式：
                1.JdbcTransactionFactory,Creates {@link JdbcTransaction} instances。
                2.ManagedTransactionFactory，Creates {@link ManagedTransaction} instances。
                本包主要依赖了Mybatis session包的TransactionIsolationLevel和exceptions包的
                PersistenceException。Mybatis的其它包大量引用了本包中的类和接口，**即严重依赖于本包**。

type        -》 类型处理，包含了类型处理器接口TypeHandler，父类BaseTypeHandler,以及若干个子类

### 技术交流

博客地址：[http://mynawang.top][1]

QQ群：专注的程序猿 282087535 [立即加入][2]


  [1]: http://mynawang.top
  [2]: http://shang.qq.com/wpa/qunwpa?idkey=632f7c11e0cb5dfc02231352205d9921c50e849a343e4010e4df1c25f59d2e90