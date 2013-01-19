package com.herokuapp.lzqwebsoft.servlet;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 随服务器启动的Servlet用于向数据库中插入一些初始化的值
 * @author johnny
 *
 */
public class InitDatabaseServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    public void init() throws ServletException { 
        try {
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            // 初始化用户
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            if(!rs.next()) {
            	stmt.executeUpdate("INSERT INTO users VALUES ('1', 'websoft', '31bde66d9873701bed3e0d0ffd626f9d235583', '751939573@qq.com', '8e04ee997d285749ecfcd280a3e1e9', '2012-12-17 16:02:26', '2012-12-13 16:43:03', '2012-12-17 16:02:06');");
            }
            // 初始化菜单
            rs = stmt.executeQuery("SELECT * FROM menus");
            if(!rs.next()) {
            	stmt.executeUpdate("INSERT INTO menus VALUES ('1', '发表博客', 'article/new.html', '1', '2012-12-13 18:15:08', '2012-12-13 18:15:11');");
                stmt.executeUpdate("INSERT INTO menus VALUES ('2', '修改密码', 'change_password.html', '1', '2012-12-13 18:16:44', '2012-12-13 18:16:46');");
                stmt.executeUpdate("INSERT INTO menus VALUES ('3', '设&nbsp;&nbsp;&nbsp;置', 'set.html', '1', '2012-12-13 18:17:57', '2012-12-13 18:18:00');");
            }
            // 初始化博客信息
            rs = stmt.executeQuery("SELECT * FROM blog_infos");
            if(!rs.next()) {
            	stmt.executeUpdate("INSERT INTO blog_infos VALUES ('1', '飘痕', '心诚则灵', '<h2 style=\"margin:0 0 5px 10px;\">Welcome you access my zone!</h2>\r\n<p style=\"margin:0 0 5px 15px;\">In building...</p>\r\n<p style=\"margin:0 0 5px 15px;\">Connect with me: <a style=\"color:red;\" href=\"https://twitter.com/lzqwebsoft\">Twitter</a></p>\r\n<p style=\"margin:0 0 5px 15px;\">本站点使用Heroku云平台建立，仅用于学习Java。</p>\r\n<p style=\"margin:0 0 5px 15px;\">基于Spring3.0 MVC 与 Hibernate3.6。</p>\r\n<hr style=\"margin-bottom:5px;\" />\r\n<p style=\"font-size:12px;margin-bottom:20px;text-align:center;\">Copyright © 2012,Powered by <a style=\"color:red;\" href=\"http://www.heroku.com\">Heroku</a></p>', 'lzqwebsoft@gmail.com', '0', '2012-12-19 17:26:32');");
            }
        } catch(Exception e) {
        	Log log = LogFactory.getLog(InitDatabaseServlet.class);
            log.error(e.getMessage(), e);
        }
    }
    
    private Connection getConnection(){
        try {
        	URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

            return DriverManager.getConnection(dbUrl, username, password);
        } catch(URISyntaxException e) {
        	Log log = LogFactory.getLog(InitDatabaseServlet.class);
            log.error(e.getMessage(), e);
        } catch(SQLException e) {
        	Log log = LogFactory.getLog(InitDatabaseServlet.class);
            log.error(e.getMessage(), e);
        }
        return null;
    }
}