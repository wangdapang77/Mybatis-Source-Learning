#Mybatis-Source-Learning


### 简介

关于Mybatis的源码学习


Mybatis内部分三个层：接口层-核心层-基础层

1.基础层-logging
-----------------
org.apache.ibatis.logging

org.apache.ibatis.logging.commons

org.apache.ibatis.logging.jdbc

org.apache.ibatis.logging.jdk14

org.apache.ibatis.logging.log4j

org.apache.ibatis.logging.log4j2

org.apache.ibatis.logging.nologging

org.apache.ibatis.logging.slf4j

org.apache.ibatis.logging.stdout


2.基础层-IO
-----------------
org.apache.ibatis.io

通过类加载器在jar包中寻找一个package下满足条件(比如某个接口的子类)的所有类


3.基础层-reflection(反射)
-------------------------
org.apache.ibatis.reflection

org.apache.ibatis.reflection.factory

org.apache.ibatis.reflection.invoker

org.apache.ibatis.reflection.property

org.apache.ibatis.reflection.wrapper

可以参考MetaObjectTest来跟踪调试，基本上用到了reflection包下所有的类


4.基础层-exceptions(异常)
-------------------------
org.apache.ibatis.exceptions


5.基础层-type(类型处理器)
-------------------------
org.apache.ibatis.type

实现java和jdbc中的类型之间转换

源码分析可以参考http://www.cnblogs.com/sunzhenchao/archive/2013/04/09/3009431.html


6.核心层-builder(构建)
-------------------------
org.apache.ibatis.builder

org.apache.ibatis.builder.annotation

org.apache.ibatis.builder.xml


7.核心层-mapping(映射)
-------------------------
org.apache.ibatis.mapping


8.核心层-scripting(脚本)
-------------------------
org.apache.ibatis.scripting

org.apache.ibatis.scripting.defaults

org.apache.ibatis.scripting.xmltags


9.核心层-parsing(解析)
-------------------------
org.apache.ibatis.parsing

xml解析，${} 格式的字符串解析

源码分析可以参考http://www.cnblogs.com/sunzhenchao/p/3161093.html


10.核心层-cache(缓存)
-------------------------
org.apache.ibatis.cache

org.apache.ibatis.cache.decorators

org.apache.ibatis.cache.impl


11.核心层-executor(执行器)
-------------------------
org.apache.ibatis.executor

org.apache.ibatis.executor.keygen

org.apache.ibatis.executor.loader

org.apache.ibatis.executor.loader.cglib

org.apache.ibatis.executor.loader.javassist

org.apache.ibatis.executor.parameter

org.apache.ibatis.executor.result

org.apache.ibatis.executor.resultset

org.apache.ibatis.executor.statement


12.核心层-plugIn(插件)
-------------------------
org.apache.ibatis.plugin


13.核心层-transaction(事务)
-------------------------
org.apache.ibatis.transaction

org.apache.ibatis.transaction.jdbc

org.apache.ibatis.transaction.managed


14.核心层-datasource(数据源)
-------------------------
org.apache.ibatis.datasource

org.apache.ibatis.datasource.jndi

org.apache.ibatis.datasource.pooled

org.apache.ibatis.datasource.unpooled


15.核心层-binging(绑定)
-------------------------
org.apache.ibatis.binding


16.核心层-annonation(注解)
-------------------------
org.apache.ibatis.annotations


17.核心层-jdbc(jdbc单元测试工具)
-------------------------
org.apache.ibatis.jdbc


18.接口层-session(会话)
-------------------------
org.apache.ibatis.session

org.apache.ibatis.session.defaults



```
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
```


### 技术交流

博客地址：[http://mynawang.top][1]

QQ群：专注的程序猿 282087535 [立即加入][2]


  [1]: http://mynawang.top
  [2]: http://shang.qq.com/wpa/qunwpa?idkey=632f7c11e0cb5dfc02231352205d9921c50e849a343e4010e4df1c25f59d2e90