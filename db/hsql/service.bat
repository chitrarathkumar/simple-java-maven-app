net stop HSQLService
hsql.exe -uninstall HSQLService
set CURRENTDIR=F:\Volante\runtime\hsql
set LIB=%CURRENTDIR%\lib
set DATABASE=%CURRENTDIR%\database\volante
set JRE=F:\JDK\jre7
hsql.exe -install HSQLService "%JRE%\bin\server\jvm.dll" -jvm_option -Xms116m -Xmx228m -Djava.class.path=".;%LIB%\hsqldb.jar" -jvm_option -Dsystem.drive="c:" -start org.hsqldb.Server -params -database %DATABASE%  -out "%CURRENTDIR%/service.out" -err "%CURRENTDIR%/service.err"
net start HSQLService 