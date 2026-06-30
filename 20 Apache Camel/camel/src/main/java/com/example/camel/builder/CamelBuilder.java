package com.example.camel.builder;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelBuilder extends RouteBuilder {

    /**
     * Configure the Camel routes. Copying data from one directory to another.
     *
     * @throws Exception if an error occurs during route configuration
     */
    @Override
    public void configure() throws Exception {
        //to see your working directory
        //System.out.println("Working Directory: " + System.getProperty("user.dir"));

        //Copying data from one directory to another using Apache Camel
        //copyAllFilesFromOneDirectoryToAnother();

        //Moving files from input to output directory using Apache Camel and keeps the file in the input>.camel dir
        from("file:20 Apache Camel/camel/input")
                .log("Moving file: ${header.CamelFileName}")
                .to("file:20 Apache Camel/camel/output")
                .log("Moved file: ${header.CamelFileName}");

    }

    private void copyAllFilesFromOneDirectoryToAnother() {
        from("file:20 Apache Camel/camel/input?noop=true")
                .log("Processing file: ${header.CamelFileName}")
                .to("file:20 Apache Camel/camel/output")
                .log("Copied file: ${header.CamelFileName}");
    }
}
