**项目说明** 
- 采用SpringBoot、MyBatis搭建。
- 应用基础框架架构代码
- 支持MySQL、Oracle、SQL Server、PostgreSQL等主流数据库

**系统初始账号**
- 系统管理员(负责系统管理、用户增删) 账号:administrator 初始密码:Spiccpiinfo@1821!
- 审计员(负责日志审计和日志备份) 账号:comptoller 初始密码:Spiccpiinfo@1821!
- 安全管理员(用户角色权限分配) 账号:security 初始密码:Spiccpiinfo@1821!


**整体模块划分说明**
# 整体模块划分

iot-platform

说明:模块划分。

1. iot-platform-common

   说明:公共模块,提供数据源切换、mybatis、kafka、websocket的统一工具类。

   (1) iot-platform-common-authnz  

   (2) iot-platform-base 

   (3) iot-platform-sysmgt  

   (4) iot-platform-sysmgt-api  

   (5) iot-platform-common-dynamic-datasource 

   (6) iot-platform-common-message-direct 

   (7) iot-platform-common-message-kafka  

   (8) iot-platform-common-mybatis

   (9) iot-platform-common-tools

   (10) iot-platform-websocket-client

   (11) iot-platform-websocket-server 

2. iot-platform-modules

   说明:前后端页面上业务部分的接口模块,根据需求拆分成不同的子模块。

   (1) iot-platform-logdict  字典和日志管理

3. iot-platform-polymerize

   说明: 沧州项目配置文件存放和启动模块,依赖中根据需求导入不同的业务模块。

4. iot-platform-vendors

   说明:对接厂商接口模块.根据不同设备分不同子模块,处理完的数据通过消息方式和业务模块交互。
   
   

###### 启动项目前,请先修改application.yml中spring.profiles.active的值和对应application-${active}.yml中redis、influxdb、datasource等url地址。

