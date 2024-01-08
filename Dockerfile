# 指定带有jdk17环境的镜像
FROM openjdk:17

COPY ./target/micro-0.0.1-SNAPSHOT-exec.jar .
# 声明容器中的 /root/httpServer/log 目录为挂载点
#VOLUME /root/httpServer/log
# 设置工作目录

# 绑定端口
EXPOSE 9099
# 设置启动命令
ENTRYPOINT ["java","-jar","micro-0.0.1-SNAPSHOT-exec.jar"]
