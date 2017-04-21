all:
	javac */*.java
clean:
	rm -f javac */*.class		
run:
	-rm -r keys/*
	mvn exec:java
