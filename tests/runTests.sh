javac -Xlint:unchecked -cp witter-models.jar uk/ac/warwick/java/cs126/services/*.java
javac -Xlint:unchecked -cp witter-models.jar:. UserTests.java WeetTests.java FollowerTests.java
javac -Xlint:unchecked -cp witter-models.jar:. TestRunner.java
java -cp witter-models.jar:. TestRunner
