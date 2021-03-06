package com.example.statista.controllers;

import com.example.statista.entities.DataSet;
import com.example.statista.util.Input;
import com.example.statista.util.StringToDoubleListConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InputController {
    private static final Logger logger = LoggerFactory.getLogger(InputController.class);

    @Autowired
    StringToDoubleListConverter converter;
    List<DataSet> dataSetList = new ArrayList<>();

    @GetMapping("/input")
    public String greetingForm(Model model) {
        model.addAttribute("input", new Input());
        logger.info("/input get method");
        return "input";
    }

    @PostMapping("/input")
    public String greetingSubmit(@ModelAttribute Input input, Model model) {
        //model.addAttribute("input", input);
        converter.setSeparator(input.getSeparator().equals("") ? "," : input.getSeparator());
        dataSetList.add(new DataSet(converter.convert(input.getContent())));
        model.addAttribute("dataSets", dataSetList);
        logger.info("/input post method");
        return "input";
    }

}
