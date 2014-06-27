# Target Compiler
JAVAH = javah
JAVAC = javac
CC = g++

# Libraries
J_HOME=$(JAVA_HOME)

# Compiler Flags:
CFLAGS = -Wall
LIBFLAGS = -std=c++11 -shared -fPIC
INCFLAGS = -I/usr/include -I$(J_HOME)/include -I$(J_HOME)/include/linux

#File Names
SOURCES=$(wildcard *.java)
HEADERS=$(SOURCES:.java=.h)
CLASSES=$(SOURCES:.java=.class)
LIBRARY=$(SOURCES:.java=.so)

# JNI Make Commands
lib%.so : %.class %.h
	$(CC) $(LIBFLAGS) $(CFLAGS) $(INCFLAGS) $*.cpp -o lib$*.so

# Make Class Files
%.class: %.java
	$(JAVAC) -d . $*.java

# Make Header Files
%.h: %.java %.class
	$(JAVAH) org.sparkfpga.jni.$*

guard-%:
	@ if [ "${${*}}" == "" ]; then \
		echo "Environment variable $* not set"; \
		exit 1; \
	fi


# Clean Up	
clean:
	$(RM) -rf *.h *.so org

