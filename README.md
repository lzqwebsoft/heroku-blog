Heroku Blog
===========
一个博客WEB应用，基于Spring3 MVC与Hibernate3， 前台使用JQuery， 用于部署在Heroku上，项目运行预览：[http://lzqwebsoft.net](http://lzqwebsoft.net "我的空间")。

#### 部署开发环境
如果你想二次开发本应用，可以将其部署在Eclipse上（本应用使用Jetty作为容器运行）,如下：

##### 1. 下载
点击[https://github.com/lzqwebsoft/heroku-blog](https://github.com/lzqwebsoft/heroku-blog)页面中的<b>ZIP</b>链接将其打包成zip文件，下载到本地。或使用git命令将其克隆到本地：

`$git clone https://github.com/lzqwebsoft/heroku-blog.git`

##### 2. 安装maven
本应用使用maven管理，因此事先要确保您的机器上已经安装maven，在命令行下输入:

`$mvn --version`

查看是否输出如下信息：
<pre>
Apache Maven 3.0.4 (r1232337; 2012-01-17 16:44:56+0800)
Maven home: D:\Program Files\apache-maven-3.0.4\bin\..
Java version: 1.6.0_10-rc2, vendor: Sun Microsystems Inc.
Java home: C:\Program Files\Java\jdk1.6.0_10\jre
Default locale: en_US, platform encoding: Cp1252
OS name: "windows vista", version: "6.1", arch: "x86", family: "windows"
</pre>
如果提示的是找不到mvn命令，说明你还没有安装maven，或没有安装成功。

请到[http://maven.apache.org/download.cgi](http://maven.apache.org/download.cgi)下载maven，然后将maven的bin目录加入到系统的环境变量中。

##### 3. 在Eclipse中配置jetty并调试
+ 将下载的heroku-blog应用目录导入到Eclipse项目中。
+ 将M2_REPO加入到Eclipse的classpath中（这一步即是将Maven的仓库包导入为Eclipse的ClassPath），使用菜单：<br />
Window > Preferences. Select the Java > Build Path > Classpath Variables page；<br />接着点击New，新建M2_REPO变量，路径设为Maven的仓库路径，默认为：(windows)C:\Documents and Settings\（当前用户）\.m2\repository,(Linux)~\.m2\repository。
+ 配置一个外部工具，来运行Jetty：<br />选择菜单Run->External Tools->External Tools Configurations...;在左边选择Program，再右击点New.命名为jetty。<br />配置Location为mvn的完整目录，Windows下：D:\Program Files\apache-maven-3.0.4\bin\mvn.bat，Linux下：使用选择无后辍的mvn文件。<br />选择Working Directory为本项目。<br />Arguments填写：`-Djetty.port=9000 jetty:run`<br />再点Enviroment选择卡：加入MAVEN_OPTS变量，值为：`-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n`<br />最后点点APPLY，再关闭本对话框。设置好后，点击Run->External Tools->Origanize favirites...加入
+ 配置jetty调试: 选择菜单Run->Debug Configarutions....弹出对话框，在其中选择Remote Java Application，右击New，Name中输入heroku-blog，Project选择本应用，Host填localhost，port为4000，同时勾选Allow termination of remote VM，注意这里的port端口需与上一步中设置的`MAVEN_OPTS`变量端口一致;其他的默认，填好后Close。
+ 配置jetty stop: 选择菜单Run->External Tools->External Tools Configurations...，选择Program，右击New，Name为jetty-stop,Location为mvn.bat或mvn的完整路径，Working Directory为本项目。Arguments填写：`jetty:stop`。

这样运行本应用将只需选择jetty就行了，调试是在jetty启动后，再选heroku-blog，停止jetty只需选择jetty-stop.

运行成功后在浏览器中输入:[http://localhost:9000/heroku-blog](http://localhost:9000/heroku-blog)启动本程序。

**注意**，在项目下的pom.xml文件中检查是否存在，如下配置：
<pre>
&lt;build>
    &lt;plugins&gt;
        &lt;plugin&gt;
            &lt;groupId>org.mortbay.jetty&lt;/groupId&gt;
            &lt;artifactId>maven-jetty-plugin&lt;/artifactId&gt;
            &lt;configuration&gt;
              &lt;!-- 停止jetty --&gt;
              &lt;stopPort>9966&lt;/stopPort&gt;
              &lt;stopKey>foo&lt;/stopKey&gt;
          &lt;/configuration&gt;
        &lt;/plugin&gt;
    &lt;/plugins&gt;
&lt;/build>
</pre>

在使用本程序时还要注意配置数据库的连接信息（本地运行使用MySQL），根据个人情况配置：`src\main\resources\database.properties`文件。同时在运行前，需要先在SQL环境中执行databaseDesign目录中的几个sql脚本程序，来导入程序运行时的初始化数据。
更多关于Maven与Jetty在Eclipse中的开发配置，可以参考下列博客：<br>
[http://blog.csdn.net/whuslei/article/details/6647275](http://blog.csdn.net/whuslei/article/details/6647275)<br>
[http://www.blogjava.net/alwayscy/archive/2007/05/19/118584.html](http://www.blogjava.net/alwayscy/archive/2007/05/19/118584.html)
<br/>
#### 部署到Heroku云端
使用本项目的源码，只需做一点点的更改，就可以将其部署在Heroku云端，从而拥有一个真正的博客。

##### 1. 配置你的Heroku环境
首先你需要到Heroku官网上下载heroku客户端工具包，并申请一个heroku帐号，详情可以到Heroku的官网的[开发者中心](https://devcenter.heroku.com/articles/quickstart)，查看相应的博客，也可以到我的博客查看翻译后的文章：[http://blog.csdn.net/xianqiang1/article/category/1345606](http://blog.csdn.net/xianqiang1/article/category/1345606).

##### 2. 配置数据库信息
由于heroku提供的免费的数据库是PostgreSQL，因此需要修改数据库配置文件：`src/main/resources/spring-database-context.xml`，关于数据源的配署如下：
<pre>
&lt;bean id="dbUrl" class="java.net.URI"&gt;
    &lt;constructor-arg value="#{systemEnvironment['DATABASE_URL']}"/&gt;
&lt;/bean&gt;
&lt;bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close"&gt;
    &lt;property name="driverClassName" value="org.postgresql.Driver"/&gt;
    &lt;property name="url" value="#{ 'jdbc:postgresql://' + @dbUrl.getHost() + @dbUrl.getPath() }"/&gt;
    &lt;property name="username" value="#{ @dbUrl.getUserInfo().split(':')[0] }"/&gt;
    &lt;property name="password" value="#{ @dbUrl.getUserInfo().split(':')[1] }"/&gt;
&lt;/bean&gt;
</pre>
再更改数据的驱动类为`org.hibernate.dialect.PostgreSQLDialect`，并关闭hibernate的SQL显示，如下：
<pre>
&lt;prop key="hibernate.dialect"&gt;org.hibernate.dialect.PostgreSQLDialect&lt;/prop&gt;
&lt;prop key="hibernate.show_sql"&gt;false&lt;/prop&gt;
</pre>
当然了部署到heroku上的数据库是可以通过Add-ons功能更改的，也不一定要更改为PostgreSQL，这都要根据你为heroku上的应用添加的数据库来决定，详情可以参考heroku的帮助文档，这里个人还是比较推荐使用PostgreSQL数据库。

##### 3. 配置程序自启动Servlet类
由于本程序在启动时，需要一些初始化的数据，因此需要一种方法，确保程序在第一次初始化时，将一些必要的数据导入数据库，这就是编写自启动Servlet类的目地；默认情况下自启动Servlet类的配置是关闭的，需要到`src/main/webapp/WEB-INF/web.xml`文件中将其打开，如下：
<pre>
&lt;servlet&gt;
    &lt;servlet-name>AutoRunServlet&lt;/servlet-name&gt; 
    &lt;servlet-class&gt;com.herokuapp.lzqwebsoft.servlet.InitDatabaseServlet&lt;/servlet-class&gt;
    &lt;load-on-startup&gt;2&lt;/load-on-startup&gt; 
&lt;/servlet&gt;
</pre>
从上面的配置可知控制程序自启动的Servlet类是`com.herokuapp.lzqwebsoft.servlet.InitDatabaseServlet`,它控制着登录本博客应用的初始帐号与密码，还有一些登录后的博客设置信息与管理的菜单。
默认情况下提供登录本博客的初始帐户名是websoft,密码是通过SHA1加密的123456。

##### 4. 配置邮件服务
本博客拥有博客新评论邮件提示与邮件验证找回密码的功能，也就是说当你博客有网友的新评论或进行找回密码时，会由你事先配置好的邮件服务器中发送一份邮件到你指定的邮箱中，予以提示。<br />
控制新评论的邮箱是由`blog_infos`表中的邮件项控制，可到`com.herokuapp.lzqwebsoft.servlet.InitDatabaseServlet`中修改，默认情况下设置如下：
<pre>
stmt.executeUpdate("INSERT INTO blog_infos VALUES ('1', '飘痕', '心诚则灵', '关于内容', 'lzqwebsoft@gmail.com', '0', '2012-12-19 17:26:32');");
</pre>
即当有新评论时，`lzqwebsoft@gmail.com`邮箱会收到提示。<br />
控制用户帐户的变更是由`users`表的邮件项控制，同样需要到`com.herokuapp.lzqwebsoft.servlet.InitDatabaseServlet`中修改，默认情况下设置如下：
<pre>
stmt.executeUpdate("INSERT INTO users VALUES ('1', 'websoft', '31bde66d9873701bed3e0d0ffd626f9d235583', '751939573@qq.com', '8e04ee997d285749ecfcd280a3e1e9', '', '0','2012-12-17 16:02:26', '2012-12-13 16:43:03', '2012-12-17 16:02:06');");
</pre>
即当用户有找回密码的操作时，邮件`751939573@qq.com`会收到提示。

下面最为重要的就是配置邮件服务，就是使用什么邮件来向上面两个邮箱发送邮件，关于服务器端的邮件配置，在`src/main/resources/mail-config.properties`文件中，如下：
<pre>
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
</pre>
上面使用的配置邮件服务采用的是google的邮件服务，你可以根据个人的情况修改，可能对于你的邮箱，上面的有些项可能不是必须的，那么你可能还要修改`src/main/java/com/herokuapp/lzqwebsoft/util/MailUtil.java`文件中的邮件配置代码。

##### 5.配置七牛云服务器（可选）
七牛云提供了免费的10G个人账户的云存储加速，对于开发者来说非常的友好，由于本博客部署在国外，服务器的性能很差劲，为了减轻服务器的负担，加速国内用户的访问，因此增加了对于七牛云服务的支持，作用是前台用户未登录时，显示给用户的图片链接地址替换成为七牛云的，同时在后台编辑博客上传新图片后也会同步上传到七牛云上，这样做的目地就是加速国内用户访问图片。

使用七牛云的服务，只需要根据注册的七牛云API权限，配置`src/main/resources/qiniu-keys.properties`文件，如下：
<pre>
# 七牛云匹配置
qiniu.bucket = 
qiniu.bucket.domain = 
qiniu.accessKey = 
qiniu.secretKey = 
# 华东: zone0 , 华北: zone1 , 华南: zone2, 北美: zoneNa0
qiniu.zone = zone0
</pre>
对应的配置说明可以参见他的官网：[七牛云JAVA SDK开发文档](https://developer.qiniu.com/kodo/sdk/1239/java)

这里为了方便旧的博客图片也同步到七牛云空间，提供了一个工具action,配置好七牛云的API权限只需要访问：`http://localhost:9000/tools/images_upload.html`，就可以一次性将原图片表中的图片上传同步到对应的七牛云空间。这里的操作是通过检测是否在七牛云返回的key来判断是否同步到七牛云，因此那些上传有返回qiniu_key的图片行是不会重复上传的。

当然了你也可以选择不使用七牛云，只需将`src/main/java/com/herokuapp/lzqwebsoft/controller/ArticleController.java`中的替换七牛云图片的代码注释，大概在90行附近处：
<pre>
// 用户未登录，则替换文章中内容使用的IMG标签SRC属性改为七牛云
String domain = messageSource.getMessage("qiniu.bucket.domain", null, locale);
String content = article.getContent();
content = content.replaceAll("\\/images\\/show\\/(\\d{14}\\w{30}).html", domain + "$1");
article.setContent(content);
</pre>

##### 6. 修改pom.xml
由于部署在本地时使用的是mysql数据库，而pushing上传到Heroku云端使用的是PostgreSQL数据库，因些需要修改项目依赖的驱动架包，即需要对pom.xml文件进行一定的更改，如下上传Heroku上时应该使用如下pom.xml文件:

<pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd"&gt;
    &lt;modelVersion&gt;4.0.0&lt;/modelVersion&gt;
    &lt;groupId&gt;org.herokuapp.lzqwebsoft&lt;/groupId&gt;
    &lt;version&gt;1.0.1&lt;/version&gt;
    &lt;artifactId&gt;heroku-blog&lt;/artifactId&gt;
    &lt;packaging&gt;war&lt;/packaging&gt;
    
    &lt;properties&gt;
        &lt;spring.version&gt;3.1.3.RELEASE&lt;/spring.version&gt;
    &lt;/properties&gt;
    
    &lt;dependencies&gt;
        &lt;dependency&gt;
            &lt;!-- Logs --&gt;
            &lt;groupId&gt;log4j&lt;/groupId&gt;
            &lt;artifactId&gt;log4j&lt;/artifactId&gt;
            &lt;version&gt;1.2.17&lt;/version&gt;
            &lt;scope&gt;runtime&lt;/scope&gt;
        &lt;/dependency&gt;
        &lt;!-- Servlet --&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;org.eclipse.jetty&lt;/groupId&gt;
            &lt;artifactId&gt;jetty-servlet&lt;/artifactId&gt;
            &lt;version&gt;7.6.0.v20120127&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;javax.servlet&lt;/groupId&gt;
            &lt;artifactId&gt;servlet-api&lt;/artifactId&gt;
            &lt;version&gt;2.5&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;!-- JSTL标签库 --&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;javax.servlet&lt;/groupId&gt;
            &lt;artifactId&gt;jstl&lt;/artifactId&gt;
            &lt;version&gt;1.1.2&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;taglibs&lt;/groupId&gt;
            &lt;artifactId&gt;standard&lt;/artifactId&gt;
            &lt;version&gt;1.1.2&lt;/version&gt;
        &lt;/dependency&gt;
    
        &lt;!-- Jakarta Commons FileUplolad及Jakarta Commons io的类包，实现文件上传 --&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;commons-fileupload&lt;/groupId&gt;
            &lt;artifactId&gt;commons-fileupload&lt;/artifactId&gt;
            &lt;version&gt;1.2.2&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;commons-io&lt;/groupId&gt;
            &lt;artifactId&gt;commons-io&lt;/artifactId&gt;
            &lt;version&gt;2.4&lt;/version&gt;
        &lt;/dependency&gt;
        
        &lt;!--Java邮件支持--&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;com.sun.mail&lt;/groupId&gt;
            &lt;artifactId&gt;javax.mail&lt;/artifactId&gt;
            &lt;version&gt;1.4.5&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;javax.activation&lt;/groupId&gt;
            &lt;artifactId&gt;activation&lt;/artifactId&gt;
            &lt;version&gt;1.1.1&lt;/version&gt;
        &lt;/dependency&gt;
        
        &lt;!-- Spring3 框架 --&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;org.springframework&lt;/groupId&gt;
            &lt;artifactId&gt;spring-core&lt;/artifactId&gt;
            &lt;version&gt;${spring.version}&lt;/version&gt;
        &lt;/dependency&gt;
        
        &lt;dependency&gt;
            &lt;groupId&gt;org.springframework&lt;/groupId&gt;
            &lt;artifactId&gt;spring-beans&lt;/artifactId&gt;
            &lt;version&gt;${spring.version}&lt;/version&gt;
        &lt;/dependency&gt;
        
        &lt;dependency&gt;
            &lt;groupId&gt;org.springframework&lt;/groupId&gt;
            &lt;artifactId&gt;spring-context&lt;/artifactId&gt;
            &lt;version&gt;${spring.version}&lt;/version&gt;
        &lt;/dependency&gt;
 
        &lt;dependency&gt;
            &lt;groupId&gt;org.springframework&lt;/groupId&gt;
            &lt;artifactId&gt;spring-web&lt;/artifactId&gt;
            &lt;version&gt;${spring.version}&lt;/version&gt;
        &lt;/dependency&gt;
 
        &lt;dependency&gt;
            &lt;groupId&gt;org.springframework&lt;/groupId&gt;
            &lt;artifactId&gt;spring-webmvc&lt;/artifactId&gt;
            &lt;version&gt;${spring.version}&lt;/version&gt;
        &lt;/dependency&gt;
        
        &lt;!--使用Spring框架的orm进行sessionFactory的管理--&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;org.springframework&lt;/groupId&gt;
            &lt;artifactId&gt;spring-orm&lt;/artifactId&gt;
            &lt;version&gt;${spring.version}&lt;/version&gt;
            &lt;classifier/&gt;
        &lt;/dependency&gt;
        
        &lt;!--添加Hibernate支持--&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;org.hibernate&lt;/groupId&gt;
            &lt;artifactId&gt;hibernate-core&lt;/artifactId&gt;
            &lt;version&gt;3.6.4.Final&lt;/version&gt;
        &lt;/dependency&gt;
        
        &lt;!-- 使用的数据库连接池包 --&gt;
        &lt;!-- Hibernate gives you a choice of bytecode providers between cglib and javassist --&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;javassist&lt;/groupId&gt;
            &lt;artifactId&gt;javassist&lt;/artifactId&gt;
            &lt;version&gt;3.9.0.GA&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;commons-pool&lt;/groupId&gt;
            &lt;artifactId&gt;commons-pool&lt;/artifactId&gt;
            &lt;version&gt;1.5.4&lt;/version&gt;
            &lt;classifier/&gt;
            &lt;exclusions&gt;
                &lt;exclusion&gt;
                    &lt;groupId&gt;commons-logging&lt;/groupId&gt;
                    &lt;artifactId&gt;commons-logging&lt;/artifactId&gt;
                &lt;/exclusion&gt;
            &lt;/exclusions&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;commons-dbcp&lt;/groupId&gt;
            &lt;artifactId&gt;commons-dbcp&lt;/artifactId&gt;
            &lt;version&gt;1.3&lt;/version&gt;
            &lt;classifier/&gt;
            &lt;exclusions&gt;
                &lt;exclusion&gt;
                    &lt;groupId&gt;commons-logging&lt;/groupId&gt;
                    &lt;artifactId&gt;commons-logging&lt;/artifactId&gt;
                &lt;/exclusion&gt;
                &lt;exclusion&gt;
                    &lt;groupId&gt;commons-pool&lt;/groupId&gt;
                    &lt;artifactId&gt;commons-pool&lt;/artifactId&gt;
                &lt;/exclusion&gt;
                &lt;exclusion&gt;
                    &lt;groupId&gt;xerces&lt;/groupId&gt;
                    &lt;artifactId&gt;xerces&lt;/artifactId&gt;
                &lt;/exclusion&gt;
                &lt;exclusion&gt;
                    &lt;groupId&gt;xerces&lt;/groupId&gt;
                    &lt;artifactId&gt;xercesImpl&lt;/artifactId&gt;
                &lt;/exclusion&gt;
                &lt;exclusion&gt;
                    &lt;groupId&gt;xml-apis&lt;/groupId&gt;
                    &lt;artifactId&gt;xml-apis&lt;/artifactId&gt;
                &lt;/exclusion&gt;
            &lt;/exclusions&gt;
        &lt;/dependency&gt;

        &lt;!-- 七牛云空间 --&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;com.qiniu&lt;/groupId&gt;
            &lt;artifactId&gt;qiniu-java-sdk&lt;/artifactId&gt;
            &lt;version&gt;7.2.11&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;com.squareup.okhttp3&lt;/groupId&gt;
            &lt;artifactId&gt;okhttp&lt;/artifactId&gt;
            &lt;version&gt;3.3.1&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;com.google.code.gson&lt;/groupId&gt;
            &lt;artifactId&gt;gson&lt;/artifactId&gt;
            &lt;version&gt;2.6.2&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;com.qiniu&lt;/groupId&gt;
            &lt;artifactId&gt;happy-dns-java&lt;/artifactId&gt;
            &lt;version&gt;0.1.4&lt;/version&gt;
        &lt;/dependency&gt;
        
        &lt;!-- MYSQL驱动包 --&gt;
        &lt;!--&lt;dependency&gt;
            &lt;groupId&gt;mysql&lt;/groupId&gt;
            &lt;artifactId&gt;mysql-connector-java&lt;/artifactId&gt;
            &lt;version&gt;5.1.9&lt;/version&gt;
        &lt;/dependency&gt;--&gt;
        
        &lt;!-- PostgreSQL驱动包 --&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;postgresql&lt;/groupId&gt;
            &lt;artifactId&gt;postgresql&lt;/artifactId&gt;
            &lt;version&gt;9.0-801.jdbc4&lt;/version&gt;
        &lt;/dependency&gt;

    &lt;/dependencies&gt;

    &lt;build&gt;
        &lt;plugins&gt;
            &lt;!--&lt;plugin&gt;
                &lt;groupId&gt;org.mortbay.jetty&lt;/groupId&gt;
                &lt;artifactId&gt;maven-jetty-plugin&lt;/artifactId&gt;
                &lt;configuration&gt;
                  停止jetty --&gt;
                  &lt;!--&lt;stopPort&gt;9966&lt;/stopPort&gt;
                  &lt;stopKey&gt;foo&lt;/stopKey&gt;
              &lt;/configuration&gt;
            &lt;/plugin&gt;--&gt;
            &lt;plugin&gt;
                &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
                &lt;artifactId&gt;maven-dependency-plugin&lt;/artifactId&gt;
                &lt;version&gt;2.3&lt;/version&gt;
                &lt;executions&gt;
                    &lt;execution&gt;
                        &lt;phase&gt;package&lt;/phase&gt;
                        &lt;goals&gt;
                            &lt;goal&gt;copy&lt;/goal&gt;
                        &lt;/goals&gt;
                        &lt;configuration&gt;
                            &lt;artifactItems&gt;
                                &lt;artifactItem&gt;
                                    &lt;groupId&gt;org.mortbay.jetty&lt;/groupId&gt;
                                    &lt;artifactId&gt;jetty-runner&lt;/artifactId&gt;
                                    &lt;version&gt;7.4.5.v20110725&lt;/version&gt;
                                    &lt;destFileName&gt;jetty-runner.jar&lt;/destFileName&gt;
                                &lt;/artifactItem&gt;
                            &lt;/artifactItems&gt;
                        &lt;/configuration&gt;
                    &lt;/execution&gt;
                &lt;/executions&gt;
            &lt;/plugin&gt;
        &lt;/plugins&gt;
    &lt;/build&gt;
&lt;/project&gt;
</pre>

##### 7. 上传部署
根据上面的步骤修改后，最后就可以使用Git将本应用上传至Heroku上了，在上传之前最好先在本地跑一下，看是否成功；
关于使用Git部署上传Java应用可以参考翻译的官网博客：
[http://blog.csdn.net/xianqiang1/article/category/1345606](http://blog.csdn.net/xianqiang1/article/category/1345606)<br />
由于本项目还在开发中，可能还有一些BUG，欢迎聪明的你来拍砖。