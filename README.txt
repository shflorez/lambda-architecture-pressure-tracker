System requirements

These were the versions of dependencies that the sample was tested against.
 
* Oracle JDK 1.7 (64 bit version)
* NetBeans 7.4
* Apache Maven – version that is included with Netbeans 7.4
* Apache Storm 0.9 (if running on Windows please refer 
http://ptgoetz.github.io/blog/2013/12/18/running-apache-storm-on-windows/). Also, on Windows use the short version of
paths that contain spaces (c:\program files\...) when configuring JAVA_HOME. We had trouble with paths that contain
spaces.
* Apache Zookeeper (required by Storm)
* Apache Pig 0.12
* Redis 2.8.4

Steps required to test sample

* Ensure that dependencies are configured and running. For details please refer to getting started information on each 
of the dependency project pages.
* Building and running "generatedata" is optional since pre-generated data is available in the batch folder.
* Running MapReduce using "calculate-max.pig" available in the "batch" folder is also optional. A pre-generated view 
for the batch layer is also made available under the "batch" folder. The file containing this view is named
"part-r-00000".

If you choose to run the Pig script you can run in local mode as below.

pig -x local calculate-max.pig

* Build the "utils" project. The "realtime" and "query" projects depend on "utils". "utils" can be built with Maven or 
an IDE such as Netbeans or Eclipse.
* The Storm project available under the "realtime" folder. "realtime" can be built with Maven or an IDE such as 
Netbeans or Eclipse. Once built you can deploy the resulting jar file on your local Storm development cluster using
the command below. Please note that minor code changes may be needed to deploy to an actual non-development Storm
cluster. We tested with a local development cluster.

storm jar realtime-1.0-SNAPSHOT-jar-with-dependencies.jar com.syncfusion.realtime.MaxTrackerTop

* Once the storm job is completed (it shuts down automatically), or even when it is running you can build and run the 
"query" sample. The "query" sample writes top ten pressure values form the realtime and batch views to the console.
"query" can be built with Maven or an IDE such as Netbeans or Eclipse.
