JUNIT = ../lib/junit-platform-console-standalone-1.13.0.jar
SRC_DIR ?= .

SRC = $(SRC_DIR)/Bowling.java $(SRC_DIR)/BowlingTest.java
CLASSES = $(notdir $(SRC:.java=.class))

all: test

compile:
	javac -cp .:$(JUNIT) $(SRC)

test: compile
	java -jar $(JUNIT) --class-path $(SRC_DIR) --scan-class-path

clean:
	rm -f $(CLASSES)