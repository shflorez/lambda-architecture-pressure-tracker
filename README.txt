System requirements

These were the versions of dependencies that the sample was tested against.
 
* Oracle JDK 1.7 (64 bit version)
* NetBeans 7.4
* Apache Maven – version that is included with Netbeans 7.4
* Apache Storm 0.9 (if running on Windows please refer http://ptgoetz.github.io/blog/2013/12/18/running-apache-storm-on-windows/)
* Apache Zookeeper (required by Storm)
* Apache Pig 0.12
* Redis 2.8.4

Steps required to test sample

* Ensure that dependencies are configured and running. For details please refer to getting started information on each of the dependency project pages.
* Building and running "generatedata" is optional since pre-generated data is available in the batch folder.
* Running MapReduce using "calculate-max.pig" available in the "batch" folder is also optional. A pre-generated view for the batch layer is also made available under the "batch" folder. The file containing this view is named "part-r-00000".

If you choose to run the Pig script you can run in local mode as below.

pig -x local calculate-max.pig

* The Storm project available under the "realtime" folder can be build using NetBeans or Maven. Once built you can deploy the resulting jar file on your local Storm development cluster using the command below. Please note that minor code changes may be needed to deploy to an actual non-development Storm cluster.

storm jar realtime-1.0-SNAPSHOT-jar-with-dependencies.jar com.syncfusion.realtime.MaxTrackerTop

