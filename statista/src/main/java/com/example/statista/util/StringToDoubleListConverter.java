package com.example.statista.util;

import com.example.statista.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Scope("prototype")
@Component
public class StringToDoubleListConverter implements Converter<String, List<Double>>{

    private static final Logger logger = LoggerFactory.getLogger(StringToDoubleListConverter.class);

    @Value(",")
    private String separator;

    public StringToDoubleListConverter(String separator){ this.separator = separator; }

    public StringToDoubleListConverter(){}

    public String getSeparator() { return separator; }

    public void setSeparator(String separator) { this.separator = separator; }

    @RolesAllowed({ "ROLE_USER", "ROLE_ADMIN" })
    @Override
    public List<Double> convert(String source) {
        if(source.contains(separator)){
            List<Double> doubleList = new ArrayList<>();
            Arrays.stream(source.split(separator))
                    .forEach(a -> doubleList.add(Double.parseDouble(a)));
            logger.info("Converted values = " + doubleList);
            return doubleList;
        }else{
            throw new InvalidInputException();
        }
    }
}
