package org.sparkfpga.fpga

// Spark Packages
// Need to include all - allows access to the full range of functions - spark people call it 'pimp up my library'
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

// Hadoop Packages - For Serialize and Deserialize
import org.apache.hadoop.io._
import java.io._

// XDMAK7 JNI
import org.sparkfpga.XdmaK7

object WordCount {
    def main(args: Array[String]) {
        val path = args(0);
        val conf = new SparkConf().setAppName("WordCountFPGA")
        val spark = new SparkContext(conf)
        val file = spark.textFile(path + "/data/words.txt")
        val counts = file.flatMap(line => line.split(" ")).map(toFPGA(_)).reduceByKey((a,b) => a + b)
        counts.saveAsTextFile(path + "/data/hw_word_out")
        spark.stop()
    }

    def toFPGA(word : String) : (String, Int) = {

        // Serialize Input
        val writable = new Text(word)
        val inBytes = serialize(writable)

        // Get XDMAK7 JNI
        val device = new XdmaK7()
        // Initialise FPGA
        device.init
        device.startTest(0, XdmaK7.ENABLE_LOOPBACK, 32768)

        // Write to Device
        device.write(0, inBytes, 0, inBytes.length)

        // Output Byte Array
        val outBytes = new Array[Byte](inBytes.length)

        // Read from Device
        device.readFully(0, outBytes)

        val newWritable = new Text()
        deserialize(newWritable, outBytes)

        device.stopTest(0, XdmaK7.ENABLE_LOOPBACK, 32768)
        device.flush

        (newWritable.toString, 1)
    }

    def serialize(writable : Writable) : Array[Byte] = {
        val out = new ByteArrayOutputStream()
        val dataOut = new DataOutputStream(out)
        writable.write(dataOut)
        dataOut.close()
        out.toByteArray()
    }

    def deserialize(writable : Writable, bytes : Array[Byte]) : Array[Byte] = {
        val in = new ByteArrayInputStream(bytes)
        val dataIn = new DataInputStream(in)
        writable.readFields(dataIn)
        dataIn.close()
        bytes
    }

}
