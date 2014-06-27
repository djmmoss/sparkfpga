package org.sparkfpga.jni

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object WordCount {
    def main(args: Array[String]) {
        val path = args(0);
        val conf = new SparkConf().setAppName("WordCount")
        val spark = new SparkContext(conf)
        val file = spark.textFile(path + "/data/words.txt")
        val counts = file.flatMap(line => line.split(" ")).map(testTheJNI(_)).reduceByKey((a,b) => a + b)
        counts.saveAsTextFile(path + "/data/jni_word_out")
        spark.stop()
    }

    def testTheJNI(word : String) : (String, Int) = {

        val jni = new TestJNI()

        val res = jni.passString(word)

        (word, 1)
    }
}
