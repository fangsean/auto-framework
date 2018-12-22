#### auto-framework Demo

###### 依赖结构
:mvn dependency:tree -Doutput=*.txt

###### 内存调参
:-Xms64m  -Xmx64m  -XX:+PrintHeapAtGC  -XX:+HeapDumpOnOutOfMemoryError