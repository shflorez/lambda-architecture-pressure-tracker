h1. System requirements

These are the versions of dependencies that the sample applications were tested against.
 
* Oracle ==JDK 1.7== (64 bit version).
* NetBeans 7.4.
* Apache Maven – version that is included with Netbeans 7.4.
* Apache Storm 0.9.
* Apache Zookeeper (required by Storm).
* Apache Pig 0.12.
* Redis 2.8.4.

h1. Steps required to run sample

h2. Dependency configuration

* Ensure that dependencies are properly configured and running. 
** Apache Zookeeper needs to be running
*** Navigate to *zookeeper$version/bin*.
*** Start ZooKeeper with <pre>./zkServer.sh start</pre> 
*** On Windows use <pre>zkServer.cmd start</pre>.
** Storm needs to be running.
*** Navigate to *storm-$version/bin*.
*** Run the following commands to start Storm.
**** <pre>./storm nimbus &</pre>
**** <pre>./storm supervisor &</pre>
*** To configure Storm on Windows please refer this "blog":http://ptgoetz.github.io/blog/2013/12/18/running-apache-storm-on-windows post. 
*** On Windows use the short version of paths that contain spaces (for instance, c:\program files\... should be shortened) when configuring ==JAVA_HOME==. We had trouble with paths that contain spaces when running Storm.
** Redis needs to be running.
*** Quick start information for Redis is available "here":http://redis.io/download.
** Installing Pig is optional. Setup details are "here":http://pig.apache.org/docs/r0.12.0/start.html#Pig+Setup. Installing Hadoop is not required for our purposes since we will just run Pig in local mode.
* For additional details on getting started with Zookeeper, Storm, Pig and Redis, please refer to getting started information on their project pages.

h2. Optional steps

* Building and running *generatedata* is optional since pre-generated data is available in the *batch* folder.
* Running MapReduce using *calculate-max.pig* available in the *batch* folder is also optional. A pre-generated view for the batch layer is also made available under the *batch* folder. The file containing this view is named *part-r-00000*.

If you choose to run the Pig script you can run in local mode as below.

<pre>
pig -x local calculate-max.pig
</pre>

h2. Required steps

* Build the *utils* project. The *realtime* and *query* projects depend on *utils*. *utils* can be built with Maven or an ==IDE== such as Netbeans or Eclipse.
* The Storm project available under the *realtime* folder. *realtime* can be built with Maven or an ==IDE== such as Netbeans or Eclipse. Once built you can deploy the resulting jar file on your local Storm development cluster using the command below. Please note that minor code changes may be needed to deploy to an actual non-development Storm cluster. We tested with a local development cluster.

<pre>
storm jar realtime-1.0-SNAPSHOT-jar-with-dependencies.jar com.syncfusion.realtime.MaxTrackerTop
</pre>

* Once the storm job is completed (it shuts down automatically), or even when it is running you can build and run the *query* sample. The *query* sample writes top ten pressure values form the realtime and batch views to the console. *query* can be built with Maven or an ==IDE== such as Netbeans or Eclipse.
