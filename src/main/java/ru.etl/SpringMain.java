package ru.etl;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.Arrays;

public class SpringMain {

    public static void main(String[] args) throws IOException {

        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
        }

        System.out.printf("I'm alive!!!");

//        printFile("E:\\aenaflight_2017_01_dump_20180327_1125.sql");
        copyFile("E:\\aenaflight_2017_01_dump_20180327_1125.sql", "E:\\copy_aenaflight.txt");

    }

    public static void printFile(String filename) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String line = new String();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static void copyFile(String filename, String fileoutput) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileoutput)))) {
            String line = new String();
            int numberLines = 1000;
            while ((line = reader.readLine()) != null && numberLines-- > 0) {
                System.out.println(line);
                writer.newLine();
                writer.write(line);
            }
        }
    }
}
