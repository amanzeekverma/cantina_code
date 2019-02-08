CODE_HOME=/home/zeek/codebase/cantina_excersize
JDK_HOME=/usr/bin

echo "compiling..."
jarList=$CODE_HOME/lib/gson-2.2.2.jar:$CODE_HOME/lib/httpclient-4.5.1.jar:$CODE_HOME/lib/httpcore-4.4.3.jar:$CODE_HOME/lib/commons-logging-1.2.jar:$CODE_HOME/lib/commons-lang-2.3.jar
echo $jarList
find $CODE_HOME/src/ -name '*.java' | xargs $JDK_HOME/javac -verbose -cp $jarList  -d $CODE_HOME/build/
echo "done"

