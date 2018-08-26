Heroku Blog
===========
一个博客WEB应用，基于Spring MVC与Hibernate， 前台使用Bootstrap3， 并整合Spring Boot，可用于部署在Heroku等云端上，项目运行预览：[http://lzqwebsoft.net](http://lzqwebsoft.net "我的空间")。

使用支付宝，打赏一下作者，请他喝瓶饮料。

![二维码](https://user-images.githubusercontent.com/1137657/41339121-ad888ad8-6f26-11e8-92ab-eeba2cca8b49.jpg)


#### 部署开发环境
如果你想二次开发本应用，可以将其直接导入IDE开发（本应用使用Undertow作为容器运行）,并己迁移支持Sproing Boot 2.0打包管理.

##### 1. 下载
点击[https://github.com/lzqwebsoft/heroku-blog](https://github.com/lzqwebsoft/heroku-blog)页面中的<b>ZIP</b>链接将其打包成zip文件，下载到本地。或使用git命令将其克隆到本地：

`$git clone https://github.com/lzqwebsoft/heroku-blog.git`

##### 2. 安装maven
本应用使用maven管理，因此事先要确保您的机器上已经安装maven，在命令行下输入:

`$mvn --version`

查看是否输出如下信息：
```
Apache Maven 3.0.4 (r1232337; 2012-01-17 16:44:56+0800)
Maven home: D:\Program Files\apache-maven-3.0.4\bin\..
Java version: 1.6.0_10-rc2, vendor: Sun Microsystems Inc.
Java home: C:\Program Files\Java\jdk1.6.0_10\jre
Default locale: en_US, platform encoding: Cp1252
OS name: "windows vista", version: "6.1", arch: "x86", family: "windows"
```
如果提示的是找不到mvn命令，说明你还没有安装maven，或没有安装成功。

请到[http://maven.apache.org/download.cgi](http://maven.apache.org/download.cgi)下载maven，然后将maven的bin目录加入到系统的环境变量中。

##### 3. 导入IDE开发
本项目可以直接导入支持maven的JAVA IDE进行开发，如Eclipse和IntelliJ IDEA.建议使用IntelliJ IDEA。
由于本项目己迁移整合Spring Boot, 运行本项目可以直运行`com.herokuapp.lzqwebsoft.StartApplication`类中的`main`方法启动。
也可以在运行maven命令`mvn spring-bot:run`启动，还可以使用`mvn package`直接打包本项目为JAR包，使用`java -jar target/xxxx.jar`命令直接运行。

> 说明： 如果想使用传统的Spring MVC开发本项目，建议下载使用[v2.0](https://github.com/lzqwebsoft/heroku-blog/releases/tag/v2.0)版本，运行方式为`mvn jetty:run`.

#### 部署到Heroku云端

使用本项目的源码，只需做一点点的更改，就可以将其部署在Heroku云端，从而拥有一个真正的博客。

##### 1. 配置你的Heroku环境
首先你需要到Heroku官网上下载heroku客户端工具包，并申请一个heroku帐号，详情可以到Heroku的官网的[开发者中心](https://devcenter.heroku.com/articles/quickstart)，查看相应的博客，也可以到我的博客查看翻译后的文章：[http://blog.csdn.net/xianqiang1/article/category/1345606](http://blog.csdn.net/xianqiang1/article/category/1345606).

##### 2. 配置数据库信息
由于heroku提供的免费的数据库是PostgreSQL，因此需要修改数据库配置文件：`src/main/resources/spring-database-context.xml`，关于数据源的配署如下：
```xml
<bean id="dbUrl" class="java.net.URI">
    <constructor-arg value="#{systemEnvironment['DATABASE_URL']}"/>
</bean>
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="org.postgresql.Driver"/>
    <property name="url" value="#{ 'jdbc:postgresql://' + @dbUrl.getHost() + @dbUrl.getPath() }"/>
    <property name="username" value="#{ @dbUrl.getUserInfo().split(':')[0] }"/>
    <property name="password" value="#{ @dbUrl.getUserInfo().split(':')[1] }"/>
</bean>
```
再更改数据的驱动类为`org.hibernate.dialect.PostgreSQLDialect`，并关闭hibernate的SQL显示，如下：
```xml
<prop key="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</prop>
<prop key="hibernate.show_sql">false</prop>
```
注意这里需要修改`pom.xml`中的依赖的驱动程序。
当然了部署到heroku上的数据库是可以通过Add-ons功能更改的，也不一定要更改为PostgreSQL，这都要根据你为heroku上的应用添加的数据库来决定，详情可以参考heroku的帮助文档，这里个人还是比较推荐使用PostgreSQL数据库。

##### 3. ~~配置程序自启动Servlet类~~ (弃用)
由于本程序在启动时，需要一些初始化的数据，因此需要一种方法，确保程序在第一次初始化时，将一些必要的数据导入数据库，这就是编写自启动Servlet类的目地；默认情况下自启动Servlet类的配置是关闭的，需要到`src/main/webapp/WEB-INF/web.xml`文件中将其打开，如下：
```xml
<servlet>
    <servlet-name>AutoRunServlet</servlet-name> 
    <servlet-class>com.herokuapp.lzqwebsoft.servlet.InitDatabaseServlet</servlet-class>
    <load-on-startup>2</load-on-startup> 
</servlet>
```
从上面的配置可知控制程序自启动的Servlet类是`com.herokuapp.lzqwebsoft.servlet.InitDatabaseServlet`,它控制着登录本博客应用的初始帐号与密码，还有一些登录后的博客设置信息与管理的菜单。
默认情况下提供登录本博客的初始帐户名是websoft,密码是通过SHA1加密的123456。

> 注意这里迁移到Spring Boot，没有配置该Servlet类，如有需要需自己手动配置，如果可以直接操作数据库，建议直接执行里面的初始化SQL语句即可，该Servlet建议弃用。 

##### 4. 配置邮件服务
本博客拥有博客新评论邮件提示与邮件验证找回密码的功能，也就是说当你博客有网友的新评论或进行找回密码时，会由你事先配置好的邮件服务器中发送一份邮件到你指定的邮箱中，予以提示。<br />
控制新评论的邮箱是由`blog_infos`表中的邮件项控制，可到`com.herokuapp.lzqwebsoft.servlet.InitDatabaseServlet`中修改，默认情况下设置如下：
```java
stmt.executeUpdate("INSERT INTO blog_infos VALUES ('1', '飘痕', '心诚则灵', '关于内容', 'lzqwebsoft@gmail.com', '0', '2012-12-19 17:26:32');");
```
即当有新评论时，`lzqwebsoft@gmail.com`邮箱会收到提示。<br />
控制用户帐户的变更是由`users`表的邮件项控制，同样需要到`com.herokuapp.lzqwebsoft.servlet.InitDatabaseServlet`中修改，默认情况下设置如下：
```java
stmt.executeUpdate("INSERT INTO users VALUES ('1', 'websoft', '××××××××', '751939573@qq.com', '××××××××', '', '0','2012-12-17 16:02:26', '2012-12-13 16:43:03', '2012-12-17 16:02:06');");
```
即当用户有找回密码的操作时，邮件`751939573@qq.com`会收到提示。

下面最为重要的就是配置邮件服务，就是使用什么邮件来向上面两个邮箱发送邮件，关于服务器端的邮件配置，在`src/main/resources/mail-config.properties`文件中，如下：
```
mail.smtp.host=smtp.gmail.com
mail.smtp.auth=true
mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
mail.smtp.socketFactory.fallback=false
mail.smtp.port=465
mail.smtp.socketFactory.port=465

mail.address.from=lzqwebsoft@gmail.com
mail.address.username=
mail.address.password=
mail.isDebug=false
```
上面使用的配置邮件服务采用的是google的邮件服务，你可以根据个人的情况修改，可能对于你的邮箱，上面的有些项可能不是必须的，那么你可能还要修改`src/main/java/com/herokuapp/lzqwebsoft/util/MailUtil.java`文件中的邮件配置代码。

##### 5.配置七牛云服务器（可选）
七牛云提供了免费的10G个人账户的云存储加速，对于开发者来说非常的友好，由于本博客部署在国外，服务器的性能很差劲，为了减轻服务器的负担，加速国内用户的访问，因此增加了对于七牛云服务的支持，作用是前台用户未登录时，显示给用户的图片链接地址替换成为七牛云的，同时在后台编辑博客上传新图片后也会同步上传到七牛云上，这样做的目地就是加速国内用户访问图片。

使用七牛云的服务，只需要根据注册的七牛云API权限，配置`src/main/resources/qiniu-keys.properties`文件，如下：
```
# 七牛云匹配置
qiniu.bucket = 
qiniu.bucket.domain = 
qiniu.accessKey = 
qiniu.secretKey = 
# 华东: zone0 , 华北: zone1 , 华南: zone2, 北美: zoneNa0
qiniu.zone = zone0
```
对应的配置说明可以参见他的官网：[七牛云JAVA SDK开发文档](https://developer.qiniu.com/kodo/sdk/1239/java)

这里为了方便旧的博客图片也同步到七牛云空间，提供了一个工具action,配置好七牛云的API权限只需要访问：`http://localhost:9000/tools/images_upload.html`，就可以一次性将原图片表中的图片上传同步到对应的七牛云空间。这里的操作是通过检测是否在七牛云返回的key来判断是否同步到七牛云，因此那些上传有返回qiniu_key的图片行是不会重复上传的。

当然了你也可以选择不使用七牛云，只需将`src/main/java/com/herokuapp/lzqwebsoft/controller/ArticleController.java`中的替换七牛云图片的代码注释，大概在90行附近处：
```java
// 用户未登录，则替换文章中内容使用的IMG标签SRC属性改为七牛云
String domain = messageSource.getMessage("qiniu.bucket.domain", null, locale);
String content = article.getContent();
content = content.replaceAll("\\/images\\/show\\/(\\d{14}\\w{30}).html", domain + "$1");
article.setContent(content);
```

##### 6. 上传部署
决定部置在heroku的运行环境的两个决定文件是：`Procfile`与`system.properties`,需根据最新的heroku文档来修改(这里未测试,因为项目预览己转为虚拟主机).
根据上面的步骤修改后，最后就可以使用Git将本应用上传至Heroku上了，在上传之前最好先在本地跑一下，看是否成功；
关于使用Git部署上传Java应用可以参考翻译的官网博客：
[http://blog.csdn.net/xianqiang1/article/category/1345606](http://blog.csdn.net/xianqiang1/article/category/1345606)<br />
由于本项目还在开发中，可能还有一些BUG，欢迎聪明的你来拍砖。