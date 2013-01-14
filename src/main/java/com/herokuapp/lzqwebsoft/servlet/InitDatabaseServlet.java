package com.herokuapp.lzqwebsoft.servlet;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

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
            stmt.executeUpdate("INSERT INTO users VALUES ('1', 'websoft', '31bde66d9873701bed3e0d0ffd626f9d235583', '751939573@qq.com', '8e04ee997d285749ecfcd280a3e1e9', '2012-12-17 16:02:26', '2012-12-13 16:43:03', '2012-12-17 16:02:06');");
            // 初始化菜单
            stmt.executeUpdate("INSERT INTO menus VALUES ('1', '发表博客', 'new.html', '1', '2012-12-13 18:15:08', '2012-12-13 18:15:11');");
            stmt.executeUpdate("INSERT INTO menus VALUES ('2', '修改密码', 'change_password.html', '1', '2012-12-13 18:16:44', '2012-12-13 18:16:46');");
            stmt.executeUpdate("INSERT INTO menus VALUES ('3', '设&nbsp;&nbsp;&nbsp;置', 'set.html', '1', '2012-12-13 18:17:57', '2012-12-13 18:18:00');");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
