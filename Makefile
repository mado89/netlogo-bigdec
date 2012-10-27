ifeq ($(origin JAVA_HOME), undefined)
  JAVA_HOME=/usr
endif

ifeq ($(origin NETLOGO), undefined)
  NETLOGO=/media/data/Uni/DA/netlogo-5.0
endif

ifeq ($(origin SCALA_HOME), undefined)
  SCALA_HOME=../..
endif

JAVAC = $(JAVA_HOME)/bin/javac
JAVAJAR = $(JAVA_HOME)/bin/jar



SRCS=$(wildcard src/at/dobiasch/*.java)

bigdec/bigdec.jar: $(SRCS) manifest.txt
	mkdir -p classes
	$(JAVAC) -g -encoding us-ascii -source 1.5 -target 1.5 -classpath $(NETLOGO)/NetLogoLite.jar -d classes $(SRCS)
	$(JAVAJAR) cmf manifest.txt bigdec/bigdec.jar -C classes .
	rm -r classes

