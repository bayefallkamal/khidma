package com.kamal.khidma.cucumber.stepdefs;

import com.kamal.khidma.KhidmaApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = KhidmaApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
