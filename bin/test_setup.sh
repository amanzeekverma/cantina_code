CODE_HOME=/home/zeek/codebase/cantina_excersize
JRE_HOME=/usr/bin
echo "Testing java version"
$JRE_HOME/java -version

jarList=$CODE_HOME/lib/gson-2.2.2.jar:$CODE_HOME/lib/httpclient-4.5.1.jar:$CODE_HOME/lib/httpcore-4.4.3.jar:$CODE_HOME/lib/commons-logging-1.2.jar:$CODE_HOME/lib/commons-lang-2.3.jar
$JRE_HOME/java -cp $jarList:$CODE_HOME/build/ com.cantina.main.TestExecutability
