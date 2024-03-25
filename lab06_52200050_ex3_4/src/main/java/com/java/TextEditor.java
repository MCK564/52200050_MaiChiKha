package com.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TextEditor {
    private final TextWriter textWriter;
    @Autowired
    public TextEditor(@Qualifier("plainTextWriter") TextWriter textWriter) {
        this.textWriter = textWriter;
    }

    public void input(String text) {

    }

    public void save(String fileName) throws IOException {
        // Save text logic
        textWriter.write(fileName, "Text content to be saved");
    }
}