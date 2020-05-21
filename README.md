# mockito_demo
Mockito、PowerMock demo示例

> 本demo主要介绍Mockito的基本用法及PowerMock对静态方法的mock。

## demo示例内容
- mock没返回值的方法
- 参数的三类匹配情况：精确、模糊、自定义
- mock final类型方法或类
- 自定义返回结果
- 设置抛出异常
- 连续的方法调用设置不同的行为
- 测试方法的调用次数
- mock静态方法
- 函数类型参数匹配：仅可匹配类型(精确匹配不知道怎么实现)

## pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.demo</groupId>
  <artifactId>mockito_demo</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>mockito_demo</name>
  <!-- FIXME change it to the project's website -->
  <url>https://github.com/byrc/mockito_demo</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>

    <mockito.version>2.26.0</mockito.version>
    <powermock.version>2.0.7</powermock.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    <!-- mockito jar引入 -->
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-core</artifactId>
      <version>${mockito.version}</version>
      <scope>test</scope>
    </dependency>
    <!-- powermock jar引入 -->
    <dependency>
      <groupId>org.powermock</groupId>
      <artifactId>powermock-api-mockito2</artifactId>
      <version>${powermock.version}</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.powermock</groupId>
      <artifactId>powermock-module-junit4</artifactId>
      <version>${powermock.version}</version>
      <scope>test</scope>
    </dependency>
    <!-- 工具 jar引入 -->
    <dependency>
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
      <version>28.2-jre</version>
    </dependency>
    <!-- lombok jar引入 -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.12</version>
    </dependency>

  </dependencies>
  
  <repositories>
    <repository>
      <releases>
        <enabled>true</enabled>
      </releases>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>
      <id>alimaven</id>
      <url>https://maven.aliyun.com/nexus/content/groups/public/</url>
    </repository>
  </repositories>
    
    ……

</project>
```

## 参考
- Mockito的GitHub：[https://github.com/mockito/mockito](https://github.com/mockito/mockito)
- PowerMock的GitHub：[https://github.com/powermock/powermock](https://github.com/powermock/powermock)
- Mockito的文档：[https://javadoc.io/static/org.mockito/mockito-core/3.3.3/org/mockito/Mockito.html](https://javadoc.io/static/org.mockito/mockito-core/3.3.3/org/mockito/Mockito.html)