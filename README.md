# IBook

#离线和在线小说

1.[如何获取全部文件的路径](https://github.com/chaychan/BlogFileResource)

2.注意权限。

3.[如何通过file获取file信息，File的文档：](https://blog.csdn.net/liumeng920/article/details/79412784)

4.如何读取大文件：

5.如何切割文件：

6.[如何翻页：](https://blog.csdn.net/LIXIAONA_1101/article/details/86477512)

7.分页思路：

1.分段读取，都一部分在文件里显示一部分。

2.存入数据库
	   
阶段性任务：实现scrollview 的界面刷新。//未完成，但是没必要

阶段性任务：实现不同文件类型的读入和存储：建议使用map，通过键值对来实现一对一的数据存储。使用了string和arraylist。

阶段性任务：数据库！

[判重后添加数据库，保证数据的唯一性：](https://blog.csdn.net/danwuxie/article/details/88805032)

Loading:https://blog.csdn.net/yujan_2015/article/details/77507446

第一阶段：数据导入任务-完成。

1.按页打印，先分成多少页，在一页一页的显示。？？？

1.如何分页，按行读取后Arraylist里每一个存储单元的内容不一。

2.是否可以一边读取文件一边打印？？

3.textview怎样才能刚刚好每页都填满？

4.怎样按章节分段？？？

第二阶段：数据展示任务-部分完成

1.目录界面，通过点击checkbox导入指定文件。//用adapter设置item的点击事件

利用数据库记录Boolean值

2.设计书架界面：

使用瀑布流：recyclerview的taggeredGridLayoutManager的adapter配置一个网格状的界面

3.设计总体布局：底部和头部：！！！！！

第三阶段：主要界面设计-部分完成

1.数据优化：对textview的数据加载？？？

题外：

禁止重复添加文件

瀑布流：

[长按删除，点击进入:](https://blog.csdn.net/qq_40869894/article/details/79627926)

网页模块：

[在网页里返回：](https://blog.csdn.net/qq_38373150/article/details/79739326)
[在android里跳转：](https://blog.csdn.net/yhy123456q/article/details/53580283)
[去广告：](https://blog.csdn.net/sinat_34015620/article/details/72845541)
