SBT = sbt

# Environment Variable
SP = $(SPARK_HOME)
PACK_JAR = target/scala-2.10/sparkfpga_2.10-1.0.jar
PACK=org.sparkfpga

# Spark Arguments
MASTER_URL = local # Run on local machine - change to run on a different machine

# Hardware Arguments
XDMAK7_JNI =  # /path/to/xdmak7.so Make sure the library is distributed across all of the nodes

# default
default: example-WordCount

# Builders
example-%: guard-SP guard-MASTER_URL package
	$(SP)/bin/spark-submit --class "$(PACK).software.$*" --master $(MASTER_URL) $(PACK_JAR) $(PWD)

fpga-%: guard-SP guard-XDMAK7_JNI guard-MASTER_URL package
	$(SP)/bin/spark-submit --class "$(PACK).fpga.$*" --driver-library-path $(XDMAK7_JNI) --master $(MASTER_URL) $(PACK_JAR) $(PWD)

package:
	$(SBT) package

# Tests
TESTJNI = $(PWD)/src/main/scala/org/sparkfpga/jni

tests: commtest jnitest diff clean

jnitest: guard-SP guard-MASTER_URL guard-TESTJNI package
	cd $(TESTJNI); make libTestJNI.so
	$(SP)/bin/spark-submit --class "$(PACK).jni.WordCount" --driver-library-path $(TESTJNI) --master local $(PACK_JAR) $(PWD)
	cd $(TESTJNI); make clean

guard-%:
	@ if [ "${${*}}" == "" ]; then \
		echo "Environment variable $* not set"; \
		exit 1; \
	fi

commtest:
	$(SBT) "run-main $(PACK).software.CommunicationTest"

# Test the Outputs
diff:
	diff data/target_words.txt data/jni_word_out/part-00000 || echo "Error: JNI Output doesn't match"


# Clean Up
clean:
	$(SBT) clean
	$(RM) -rf project
	$(RM) -rf data/*_out/
