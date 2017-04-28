all:
	javac */*.java
clean:
	rm -f javac */*.class
keys:
	-mkdir keys
	-rm -r keys/*
run: keys
	mvn exec:java 
