HOME=.
export CLASSPATH=.:$HOME/lib/hsqldb.jar
source "../../bin/setenv.sh"
java org.hsqldb.Server -database database/volante
