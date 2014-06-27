package org.sparkfpga.software

import org.apache.hadoop.io._
import java.io._

object CommunicationTest {

    def main(args: Array[String]) : Unit = {

        // Integer Serialize
        val writableInt = new IntWritable(163)
        val bytesInt = serialize(writableInt)
        assert(bytesInt.length == 4)

        // Text Writable
        val writableText = new Text("Test")
        val bytesText = serialize(writableText)
        assert(bytesText.length == 5)

        // Integer Deserialize
        val newWritableInt = new IntWritable()
        deserialize(newWritableInt, bytesInt)
        assert(newWritableInt.get == 163)

        // Integer Deserialize
        val newWritableText = new Text()
        deserialize(newWritableText, bytesText)
        assert(newWritableText.toString == "Test")

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
