Put WeetTests,UserTests,FollowerTests,TestRunner in the Witter/tests directory.

From this directory, copy your UserStore.java, WeetStore.java, FollowerStore.java to:

uk/ac/warwick/java/cs126/services

cp ~/Witter/UserStore.java uk/ac/warwick/java/cs126/services
etc

Then compile these files FROM THIS DIRECTORY (should be in Witter/tests):

javac -cp witter-models.jar uk/ac/warwick/java/cs126/services/*.java

Then compile UserTests.java and WeetTests.java and FollowerTests.java (you should still be in witter/tests):

javac -cp witter-models.jar:. UserTests.java WeetTests.java FollowerTests.java

Then finally compile TestRunner.java:

javac -cp witter-models.jar:. TestRunner.java

The run the test runner!

java -cp witter-models.jar:. TestRunner
