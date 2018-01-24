package org.learning.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        if (args.length <= 1) {
            System.out.println("File and node names are required");

            //lterm
            //C:\Users\Anastasiia_Chernysho\epam_trainings\final_java_repo\java_learning\jsonparser\src\main\resources\test.json
            RequestArgs requestArgs = new RequestArgs().invoke();
            File source = requestArgs.getSource();
            String nodeName = requestArgs.getNodeName();

            getNodeValue(source, nodeName);
        } else {
            System.out.println(args[0]);
            File source = new File(args[0]);
            String nodeName = args[1];
            if (!source.exists()) {
                exit("File isn't found");
            }
            getNodeValue(source, nodeName);
        }
    }

    private static void getNodeValue(File source, String nodeName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(source);
            JsonNode node = root.findValue(nodeName);
            if (node == null){
                System.out.println("Node isn't found");
            } else {
                System.out.println(node);
            }
        } catch (IOException e) {
            System.out.println("Incorrect json structure. \n" + e.getMessage());
        }
    }

    private static void exit(String s) {
        System.out.println(s);
        System.exit(100);
    }

    private static class RequestArgs {
        private File source;
        private String nodeName;

        public File getSource() {
            return source;
        }

        public String getNodeName() {
            return nodeName;
        }

        public RequestArgs invoke() {
            Scanner input = new Scanner(System.in);
            System.out.println("Input path to file:");
            source = new File(input.nextLine());
            System.out.println("Input node name:");
            nodeName = input.nextLine();
            return this;
        }
    }
}
