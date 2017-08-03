package com.self.ddyoung.rice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by sanbian on 2017/8/2.
 */
@Component
@ConfigurationProperties(prefix = "com.self.ddyoung")
public class ApplicationProperties {

//    @Value("${com.self.ddyoung.title}")
    private String title;

//    @Value("${com.self.ddyoung.description}")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
