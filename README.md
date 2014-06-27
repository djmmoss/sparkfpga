# Spark FPGA

## Setup

Ensure that Spark 1.0 is installed, and that the SPARK_HOME environment variable is set.

For each program there are two separate implementation, software and fpga.

To run any software example type:

```
$ make software-(NAME)
```

To run an fpga example, first make sure that the XDMAK7 JNI shared library has been distributed amoungst the nodes, and that the XDMAK7_JNI flags as been set to it directory.

```
$ make fpga-(NAME)
```

## List of Example Program

This is a list of example programs:

 * WordCount


Use either of the two commands above to execute them, replacing (NAME) with the example program of choice.

## Testing

There are two testing scripts, both that run with:

```
$ make tests
```

This will run a serialization and deserialization test, as well as a jni test that will compare with the expected output.

