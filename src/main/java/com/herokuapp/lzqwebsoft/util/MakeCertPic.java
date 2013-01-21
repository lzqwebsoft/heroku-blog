package com.herokuapp.lzqwebsoft.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 图片验证码工具类
 * 
 * @author johnny
 * 
 */
public class MakeCertPic {
    // 字符集
    private static char mapTable[] = 
        "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm".toCharArray();
    
    private Color getRandColor(int s, int e) {  
        Random random = new Random();  
        if (s > 255)  
            s = 255;  
        if (e > 255)  
            e = 255;  
        int r = random.nextInt(e - s) + s; // 随机生成RGB中的R值  
        int g = random.nextInt(e - s) + s; // 随机生成RGB中的G值  
        int b = random.nextInt(e - s) + s; // 随机生成RGB中的B值  
        return new Color(r, g, b);  
    }
    
    public String getCertPic(int width, int height, OutputStream os) {
        if (width <= 0)
            width = 100;
        if (height <= 0)
            height = 25;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        Random r = new Random();
        // 设置背景颜色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        Graphics2D g2d = (Graphics2D) g;
        // 渲染字体的平滑
        g2d.setRenderingHint (  
                RenderingHints.KEY_TEXT_ANTIALIASING,  
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        
        // 随机产生的验证码
        String sRand = "";
        String ctmp = "";
        for (int i = 0; i < 5; i++) {
            ctmp= String.valueOf(mapTable[(int)(mapTable.length*Math.random())]);
            sRand += ctmp;
            // 设置每个字符的随机颜色
            Color color = new Color(20 + r.nextInt(210), 20 + r.nextInt(210), 20 + r.nextInt(210));
            g2d.setColor(color);
            g2d.setFont(new Font("", Font.PLAIN, 30));
            // 设置每个字符的随机旋转
            AffineTransform at = new AffineTransform();
            int number = r.nextInt(3) - 1;
            double role = number * r.nextDouble() * (Math.PI / 4);
            // role:旋转角度,后面两个参数是设置围绕坐标点旋转
            int y = 25 - r.nextInt(15);
            if(y<=24)
                y = 24;
            else if(y>=(height-10))
                y = (height-10);
            at.setToRotation(role, 16 * i + 10, y);
            g2d.setTransform(at);

            g2d.drawString(ctmp, 16 * i + 10, y);
        }
        
        // 随机产生20个干扰点
        Random rand = new Random();
        for(int i=0; i<10; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            // 设置每个字符的随机颜色
            Color color = new Color(20 + r.nextInt(210), 20 + r.nextInt(210), 20 + r.nextInt(210));
            g2d.setColor(color);
            g.drawOval(x, y, 2, 2);
        }

        g2d.dispose();
        
        try {
            ImageIO.write(image, "JPEG", os);
            return sRand; 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
