@echo off
rem /**
rem  * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
rem  * No deletion without permission, or be held responsible to law.
rem  *
rem  * Author: ThinkGem@163.com
rem  */
echo.
echo [信息] 初始化数据库。
echo.
echo [信息] 本操作主要用于首次安装 JeeSite 或后安装 Module 的数据表初始化，若模块已安装会自动忽略。
echo.
echo [信息] 官方默认提供的初始化数据库工具是比较安全的，她没有包含删除您的业务数据表及数据的脚本。
echo.
echo [信息] 如果你是升级到该版本，不排除你是否升级完整，为安全起见，建议先备份数据库后再操作。
echo.
pause

%~d0
cd %~dp0

cd ..

call mvn clean compile -Dmaven.test.skip=true -U
echo.
echo [信息] 依赖下载完成，下面开始初始化数据库。
echo.
pause

set "MAVEN_OPTS=%MAVEN_OPTS% -Xms512m -Xmx1024m"
call mvn test -Dmaven.test.skip=false -Dtest=com.jeesite.test.InitData

cd bin
pause