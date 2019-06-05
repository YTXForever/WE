# LINUX
## linux 体系结构

## 命令find
*http://blog.chinaunix.net/uid-24648486-id-2998767*

**find path [options] params**

- find ~ -name "hello.java" ：精确查询
- find ~ -name "hello*" ：模糊匹配
- find ~ -iname "hello*":不区分大小写
- find / -name "hello.*"：在/目录下查找
- find / -size +1M :查找/目录下大小在1M以上的文件

## 命令grep 
*https://www.cnblogs.com/qianjinyan/p/9244746.html*

**grep [options] pattern file** ,file支持正则表达式
- -i 忽略字符大小写的差别
- -v 反转查找
- -C 显示匹配列之前后的内容
- -A 显示匹配行之后的内容
- -B 显示匹配行之前的内容
- -E 使用正则表达式
- -o 只输出文件中匹配到的部分
## 管道操作符 |
** **
## awk
**awk [options] "cmd" file**

*常见用法*
- awk 'print $1,$2' XXX.log
- awk '$1=="tcp" && $2==1 {print $0}' XXX.log ($0:表示整行)
- awk '{arr[$1]++} END {for(key in arr) print key,arr[key] }' XXX.log
## sed

1. sed -i 's/^Str/String/g' file ：
    - -i表示在原文件上操作，不加-i则不会变更原文件;
    - 第一个"/"之前的s表示是对字符串进行操作;
    - ^表示以后面的字符串为前缀;
    - Str表示被替换的字符串;
    - String表示替换为的目标字符串;
    - 最后一个"/"之后的g表示全文替换，如果不加，则对每一行数据只替换第一个符合条件的。

2. 以Str为前缀：^Str；以Str为后缀：Str$
3. 删除空行：sed -i '/^ *$/d' file:最后一个'/'之后的d表示删除







    
















