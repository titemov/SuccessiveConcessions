# SuccessiveConcessions
Successive Concessions Method Java. 

[Simplex method](https://github.com/Developonz/SimplexMethod)

## System requirements
Java version 23.0.1

JavaFX version 23.0.1

## Compilation

1. Download latest [JavaFx](https://gluonhq.com/products/javafx/) library and extract contents.

2. Make sure you have javac.exe compiler `C:\Users\USER\.jdks\jdk-{version}\bin\javac.exe`

3. To compile, open command line in __source code directory__ and write  
   `javac.exe --module-path {Path to your javafx lib folder} --add-modules=javafx.controls Main.java`

    - For example if JavaFx contents extracted to `С:\Java`
      Then command should look like this:  
      `javac.exe --module-path "C:\Java\javafx-sdk-23.0.1\lib" --add-modules=javafx.controls Main.java` 
    - If you have problems with javac.exe, try to write __absolute path__ to javac.exe
      

4. After compilation run `Main` file:  
   `java --module-path {Path to your javafx lib folder} --add-modules=javafx.controls Main`
