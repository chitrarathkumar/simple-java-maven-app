title HSQL
set HOME=.
set CLASSPATH=.;%HOME%\lib\hsqldb.jar
SET DESIGNER_HOME=D:\Bala\Releases\Exe\6_0_0\Volante_6_0_0_2018_07_12
call "%DESIGNER_HOME%\bin\setenv.bat"
"%JRE_HOME%\bin\java"  org.hsqldb.Server -database database\volante