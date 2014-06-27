package org.sparkfpga.software

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object WordCount {
    def main(args: Array[String]) {
        val path = args(0);
        val conf = new SparkConf().setAppName("WordCount")
        val spark = new SparkContext(conf)
        val file = spark.textFile(path + "/data/words.txt")
        val counts = file.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey((a,b) => a + b)
        counts.saveAsTextFile(path + "/data/sw_word_out")
        spark.stop()
    }
}
