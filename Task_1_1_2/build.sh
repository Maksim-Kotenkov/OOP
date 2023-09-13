javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.kotenkov.polynomial

# jar cf Heapsort.jar Heapsort.class
javac src/main/java/ru/nsu/kotenkov/polynomial/Polynomial.java -d ./build

java -cp ./build ru.nsu.kotenkov.polynomial.Polynomial
