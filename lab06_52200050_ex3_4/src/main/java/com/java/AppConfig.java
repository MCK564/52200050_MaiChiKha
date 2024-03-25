package com.java;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Bean
    @Qualifier("plainTextWriter")
    public TextWriter plainTextWriter() {
        return new PlainTextWriter();
    }

    @Bean
    @Qualifier("pdfTextWriter")
    public TextWriter pdfTextWriter() {
        return new PdfTextWriter();
    }


}
