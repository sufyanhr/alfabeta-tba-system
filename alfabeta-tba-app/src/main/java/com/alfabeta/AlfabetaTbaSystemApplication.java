package com.alfabeta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlfabetaTbaSystemApplication {

    // 👇 يمنع الإنشاء اليدوي لهذا الكلاس (تحسين نمطي فقط)
    private AlfabetaTbaSystemApplication() {
        throw new IllegalStateException("Main class should not be instantiated");
    }

    public static void main(String[] args) {
        SpringApplication.run(AlfabetaTbaSystemApplication.class, args);
    }
}
