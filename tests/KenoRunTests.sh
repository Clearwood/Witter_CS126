rm -f ./uk/ac/warwick/java/cs126/services/*
cp ../src/*.java ./uk/ac/warwick/java/cs126/services/
javac -cp witter-models.jar uk/ac/warwick/java/cs126/services/*.java
javac -cp witter-models.jar:. UserTests.java WeetTests.java FollowerTests.java
javac -cp witter-models.jar:. TestRunner.java
java -cp witter-models.jar:. TestRunner
