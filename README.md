heroku blog
===========
一个博客WEB应用，基于Spring3 MVC与Hibernate3， 前台使用JQuery， 用于部署在Heroku上，项目运行预览：[http://lzqwebsoft.herokuapp.com](http://lzqwebsoft.herokuapp.com, "我的空间")。

#####部署开发环境
如果你想二次开发本应用，可以将其部署在Eclipse上（本应用使用Jetty作为容器运行）,如下：

######1. 下载
点击[https://github.com/lzqwebsoft/heroku-blog](https://github.com/lzqwebsoft/heroku-blog)页面中的<b>ZIP</b>链接将其打包成zip文件，下载到本地。或使用git命令将其克隆到本地：

`$git clode https://github.com/lzqwebsoft/heroku-blog.git`

######2. 安装maven
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
如果提示的是找不到git命令，说明你还没有安装maven，或没有安装成功。

请到[http://maven.apache.org/download.cgi](http://maven.apache.org/download.cgi)下载maven，然后将maven的bin目录加入到环境的环境变量中。
######3. 在Eclipse中配置jetty进行调试
+ 将下载的heroku-blog应用目录导入到Eclipse项目中。
+ 将M2_REPO加入到Eclipse的classpath中（这一步即是将Maven的仓库包导入为Eclipse的ClassPath），使用菜单：<br />
Window > Preferences. Select the Java > Build Path > Classpath Variables page；<br />接着点击New，新建M2_REPO变量，路径设为Maven的仓库路径，默认为：(windows)C:\Documents and Settings\（当前用户）\.m2\repository,(Linux)~\.m2\repository。
+ 配置一个外部工具，来运行Jetty：<br />选择菜单Run->External Tools->External Tools Configurations...;在左边选择Program，再右击点New.命名为jetty。<br />配置Location为mvn的完整目录，Windows下：D:\Program Files\apache-maven-3.0.4\bin\mvn.bat，Linux下：使用选择无后辍的mvn文件。<br />选择Working Directory为本项目。<br />Arguments填写：`-Djetty.port=9000 jetty:run`<br />再点Enviroment选择卡：加入MAVEN_OPTS变量，值为：`-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n`<br />最后点点APPLY，再关闭本对话框。设置好后，点击Run->External Tools->Origanize favirites...加入
+ 配置jetty调试: 选择菜单Run->Debug Configarutions....弹出对话框，在其中选择Remote Java Application，右击New，Name中输入heroku-blog，Project选择本应用，Host填localhost，port为9000。其实的默认，填好后Close。
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

再使用本程序时还要注意配置数据库连接（使用MySQL），根据个人情况配置：src\main\resources\database.properties文件。