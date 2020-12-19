package com.example.statista.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Scope("prototype")
@Component
public class StringToDoubleListConverter implements Converter<String, List<Double>>,
                                                    DisposableBean, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(StringToDoubleListConverter.class);

    @Value(",")
    private String separator;

    public StringToDoubleListConverter(String separator){ this.separator = separator; }

    public StringToDoubleListConverter(){}

    public String getSeparator() { return separator; }

    public void setSeparator(String separator) { this.separator = separator; }

    @Override
    public List<Double> convert(String source) {
        List<Double> doubleList = new ArrayList<>();
        Arrays.stream(source.split(separator))
                .forEach(a -> doubleList.add(Double.parseDouble(a)));
        logger.info("Converted values = " + doubleList);
        return doubleList;
    }

    @Override
    public void destroy() throws Exception {
        logger.info("Converter destroyed.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Converter initialised.");
    }
}
