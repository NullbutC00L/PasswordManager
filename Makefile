all:
	javac */*.java
clean:
	rm -f javac */*.class		
run:
	-mkdir keys
	-rm -r keys/*
	mvn exec:java
