title HSQL
set HOME=.
set CLASSPATH=.;%HOME%\lib\hsqldb.jar
call ..\..\bin\setenv.bat
%JRE_HOME%\bin\java  org.hsqldb.Server -stop
