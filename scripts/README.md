# Linux服务脚本

对于采用service系统，将myblog赋予执行权限，复制到`/etc/init.d/`目录下，这时博客系统就可以实行开机启动
```bash
sudo cp myblog /etc/init.d/
sudo chmod +x /etc/init.d/myblog

# 设置开机启动
sudo chkconfig myblog on

# 启动博客
sudo service myblog start
# 停止博客
sudo service myblog stop
```


对于须用systemd的系统，将myblog.service赋予执行权限，复制到`/lib/systemd/system/`目录下，并enable服务
```bash
sudo cp myblog.service /lib/systemd/system/
sudo chmod +x /lib/systemd/system/myblog.service

# 设置开机启动
sudo systemctl enable myblog

# 启动博客
sudo systemctl start myblog
# 停止博客
sudo systemctl stop myblog
```

