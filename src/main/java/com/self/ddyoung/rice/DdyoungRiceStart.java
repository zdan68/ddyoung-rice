package com.self.ddyoung.rice;

import com.self.ddyoung.rice.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:sanbian@pamirs.top">Sanbian</a>
 * @version 1.0
 * @since 17/8/1 下午4:56
 */
@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class DdyoungRiceStart {

    @Autowired
    private ApplicationProperties applicationProperties;

    @RequestMapping("/")
    String home() {
        return applicationProperties.getTitle() + applicationProperties.getDescription();
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DdyoungRiceStart.class, args);
    }
}
