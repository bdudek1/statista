package com.example.statista.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

public class Input {

    private static final Logger logger = LoggerFactory.getLogger(Input.class);
    private long id;
    private String content;

    @Value(",")
    private String separator;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeparator() { return separator; }

    public void setSeparator(String separator) { this.separator = separator; }

    public String getContent() {
        logger.info("input get content");
        return content;
    }

    public void setContent(String content) {
        logger.info("input set content");
        this.content = content;
    }

}