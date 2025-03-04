# ing-sw-2023-Gonnella-Mauro-Mazzoleni-Migliorini
# Final test Software Engineering 2023

**MyShelfie** Board Game is the final test of the **"Software Engineering"** course of **"Computer Science Engineering"**, held at Politecnico di Milano (2022/2023).

**Teacher**: Pierluigi San Pietro

## Group PSP54
- Guido Gonnella
- Pierantonio Mauro
- Samuele Mazzoleni
- Andrea Migliorini

## Documentation

### UML

The following UML class diagrams represent the initial model of the architecture as developed during the design phase and the final implementation.

- [UML initial design](https://github.com/guido-gonnella/ing-sw-2023-Gonnella-Mauro-Mazzoleni-Migliorini/blob/master/Deliverables/final/uml/UML_initial.png)
- [UML final design](https://github.com/guido-gonnella/ing-sw-2023-Gonnella-Mauro-Mazzoleni-Migliorini/blob/master/Deliverables/final/uml/UML_generated_non_details.png)

### JavaDoc
The following documentation includes a description for the majority of the classes and the method used. [Javadoc](https://guido-gonnella.github.io/ing-sw-2023-Gonnella-Mauro-Mazzoleni-Migliorini/)

### Coverage

Model Coverage result can be found at the following link: [Coverage result](https://github.com/guido-gonnella/ing-sw-2023-Gonnella-Mauro-Mazzoleni-Migliorini/blob/master/Deliverables/final/coverage/coverage_result.jpg)

## Communication protocol

The following sequence diagrams describe the communication between the server and the clients. [Sequence diagrams](https://github.com/guido-gonnella/ing-sw-2023-Gonnella-Mauro-Mazzoleni-Migliorini/tree/master/Deliverables/final/uml/Sequence%20diagram)

## Libraries and plugins

| Library |                                                                                                  Descriprion                                                                                                   |
|:--------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| Maven   | Software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information. |
| JUnit   |                                                                                           Framework for unit testing                                                                                           |
| JavaFX  |                                                                               Graphic library used to realize the user interface                                                                               |

## Implemented Functions

- Complete Rules
- Command Line Interface (CLI)
- GUI
- Socket Communication
- Multiple games

## Compiling

The JARs have been created with the help of Maven Shade Plugin.
Below are the precompiled JARs.
To compile the JARs independently, navigate to the folder ```MyShelfie/``` in the project directory and run the following command
```
mvn clean package
```
The compiled JARs will be placed in the folder ```target/``` with the names
```ServerApp.jar``` e ```ClientApp.jar```.

### Jar

Precompiled Jars can be found at the following link: [Jar](https://github.com/guido-gonnella/ing-sw-2023-Gonnella-Mauro-Mazzoleni-Migliorini/tree/master/Deliverables/final/jar)

## Execution

### Server

To launch the server enter the following command
```
java -jar ServerApp.jar
```

### Client

To launch the server enter the following command
```
java -jar ClientApp.jar
```
It is possible to add the parameter ``` -g ``` or ``` --gui ``` to use the GUI, otherwise
the game will be played on the CLI.
