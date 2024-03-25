package com.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        TextWriter plainTextWriter = (TextWriter) context.getBean("plainTextWriter");
        plainTextWriter.write("test", "This is a plain text file.");

        TextWriter pdfTextWriter = (TextWriter) context.getBean("pdfTextWriter");
        pdfTextWriter.write("test", "This is a PDF file.");

        context.close();
    }
}