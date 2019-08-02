# studyDocument
http://uyi2.com/





https://mp.weixin.qq.com/s?__biz=MzI1NDQ3MjQxNA==&mid=2247489402&idx=2&sn=af5c3bb38717e828d92ed48874f03fe8&chksm=e9c5eecbdeb267dd3d05c159bdb9c611f24c4ca7fb7dafa12daf459becb0ef4fab7bcc2d1a67&mpshare=1&scene=1&srcid=0627KrO2YmHRbTUJV2EqXGNH#rd




http://yun.java1234.com/article/457





http://blog.cuzz.site/2019/04/16/Java%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B/



https://unbug.github.io/codelf  变量名



https://liuyanzhao.com/8662.html   Jenkins


#!/bin/bash 

#export BUILD_ID=dontKillMe这一句很重要，这样指定了，项目启动之后才不会被Jenkins杀掉。
export BUILD_ID=dontKillMe

#指定最后编译好的jar存放的位置
www_path=/opt/jenkins/test/

#Jenkins中编译好的jar位置
jar_path=/root/.jenkins/workspace/framework/target

#Jenkins中编译好的jar名称
jar_name=framework.jar

#获取运行编译好的进程ID，便于我们在重新部署项目的时候先杀掉以前的进程
pid=$(cat /opt/jenkins/shaw-test-web.pid)

#进入指定的编译好的jar的位置
cd  ${jar_path}

#将编译好的jar复制到最后指定的位置
cp  ${jar_path}/${jar_name} ${www_path}

#进入最后指定存放jar的位置
cd  ${www_path}

#杀掉以前可能启动的项目进程
kill -9 ${pid}

#启动jar，指定SpringBoot的profiles为test,后台启动
java -jar -Dspring.profiles.active=test ${jar_name} &

#将进程ID存入到shaw-web.pid文件中
echo $! >/opt/jenkins/shaw-test-web.pid









https://www.ymq.io/2017/07/19/git/
