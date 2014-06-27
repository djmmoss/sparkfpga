name := "SparkFPGA"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.0.0"

libraryDependencies ++= Seq(
    "org.apache.hadoop" % "hadoop-common" % "2.0.0-cdh4.3.0" % "compile" exclude ("org.slf4j", "slf4j-api") exclude ("com.sun.jmx", "jmxri") exclude ("com.sun.jdmk", "jmxtools") exclude ("javax.jms", "jms") exclude ("org.slf4j", "slf4j-log4j12") exclude("hsqldb","hsqldb"),
    "org.apache.hadoop" % "hadoop-client" % "2.0.0-mr1-cdh4.3.0" % "compile" exclude ("com.sun.jmx", "jmxri") exclude ("com.sun.jdmk", "jmxtools") exclude ("javax.jms", "jms") exclude ("org.slf4j", "slf4j-log4j12") exclude("hsqldb","hsqldb"),
    "org.apache.hadoop" % "hadoop-test" % "2.0.0-mr1-cdh4.3.0" % "test",
    "org.apache.hadoop" % "hadoop-minicluster" % "2.0.0-mr1-cdh4.3.0" % "test",
    "org.apache.hadoop" % "hadoop-common" % "2.0.0-cdh4.3.0" % "test",
    "org.apache.hadoop" % "hadoop-hdfs" % "2.0.0-cdh4.3.0" % "test" exclude ("commons-daemon", "commons-daemon"),
    "org.apache.hadoop" % "hadoop-hdfs" % "2.0.0-cdh4.3.0" % "test" classifier "tests",
    "org.apache.hadoop" % "hadoop-common" % "2.0.0-cdh4.3.0" % "compile" classifier "tests",
    "org.scalanlp" %% "breeze" % "0.8.1",
    "org.scalanlp" %% "breeze-natives" % "0.8.1"
)



resolvers ++= Seq("Akka Repository" at "http://repo.akka.io/releases/",
    "Cloudera Repo" at "https://repository.cloudera.com/artifactory/cloudera-repos/", 
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
    "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/")
