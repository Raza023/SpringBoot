# QR Code Generator

A simple Java application that generates QR codes and saves them as PNG images.

## Project Description

This project demonstrates how to generate QR codes using the QRGen library. The application converts text input into a QR code image with customizable dimensions.

## Getting Started

### Prerequisites

-   Java 17 or higher
-   QRGen library (included in `lib` folder)

### Running the Application

1.  Compile the project (if needed):
    
    ```bash
    javac -cp lib/*:src -d bin src/App.java
    ```
    
2.  Run the application:
    
    ```bash
    java -cp lib/*:bin App
    ```
    

The application will generate a QR code image named `qrfile.png` in the project root directory.

## Folder Structure

The workspace contains the following folders:

-   `src`: Contains the source code (App.java)
-   `lib`: Contains external dependencies (QRGen library)
-   `bin`: Contains the compiled output files

## Customization

You can modify the QR code generation in `App.java`:

-   Change the text content by modifying the `content` variable
-   Adjust the QR code size by changing the dimensions in `.withSize(250, 250)`
-   Change the output filename by modifying the `file` variable

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).