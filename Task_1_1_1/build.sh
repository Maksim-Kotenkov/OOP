javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.kotenkov.heapsort

# jar cf Heapsort.jar Heapsort.class
javac src/main/java/ru/nsu/kotenkov/heapsort/Heapsort.java -d ./build

java -cp ./build ru.nsu.kotenkov.heapsort.Heapsort
