# About
This is a command line program built using Java. It prints out a paginated list of open foodtrucks in San Francisco that are accessed from San Francisco government's API.

# Technologies used
- Java (can be run on JDK 1.7 and above)
- JSON API

# Pre-requisites
- JDK 1.7+
- Command line tool

# Building/Running the project
- Clone the repository on your local system.
- Using a command line tool, navigate to the "java" directory from the project root.
- For Windows, 
  - Use the following command to build the application,
    ```
    javac -cp "..\..\libs\*;". FoodTruckMain.java
    ```
  - Use the following command to run the application,
    ```
    java -cp "..\..\libs\*;". FoodTruckMain
    ```
- For Unix (Mac OS/Ubuntu), 
  - Use the following command to build the application,
    ```
    javac -cp "../../libs/*:". FoodTruckMain.java
    ```
  - Use the following command to run the application,
    ```
    java -cp "../../libs/*:". FoodTruckMain
    ```


