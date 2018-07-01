package com.compute.bdd.cucumber.run;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/cucumber" }, features = {
		"src/test/resources" }, glue = { "com.compute.bdd.cucumber.glue" })
public class RunBddTests {

}
