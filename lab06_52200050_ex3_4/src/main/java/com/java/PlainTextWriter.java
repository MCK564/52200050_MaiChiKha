package com.java;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PlainTextWriter implements  TextWriter{
    @Override
    public void write(String fileName, String text) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName + ".txt"))) {
            writer.println(text);
            System.out.println("Plain text file written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
