# Linux

1.常用linux命令

ls -r  按创建时间倒序展示

chmod -R 777 *    修改权限         

tar -zcvf yangjingying.gz --exclude /opt/web/dianshangwuxian_finance_beauty_web/work --exclude /opt/web/dianshangwuxian_finance_beauty_web/logs /opt/web/dianshangwuxian_finance_beauty_web   去除目录下两个目录，压缩

tar -zxvf test1.tar  将文件解压

2、查找指定文件

find -name xxx.txt  在当前目录及子目录查找文件

find / -iname "target*"  支持通配符(忽略大小写)

3.文本检索

grep -v "sss" 检索的内容不包含 sss

greo -o "engine\\[[0-9]*]\\"   grep内容匹配正则表达式

4.awk命令

5.sed

sed   's/^str/string/' xxx.log 替换字符串，到控制台，并不实际改变文本

sed -i ''   's/^str/string/' xxx.log   变更至文本





练习 LINUX线上问题演练10题

