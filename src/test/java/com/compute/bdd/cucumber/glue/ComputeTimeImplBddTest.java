package com.compute.bdd.cucumber.glue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;

import com.compute.constants.ComputeConstants;
import com.compute.impl.ComputeTimeImpl;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ComputeTimeImplBddTest {

	ComputeTimeImpl computeTimeImpl=new ComputeTimeImpl();
	Map<String,Long> result=new HashMap<>();
    
    @Given("^there are (\\d+) machines and (\\d+) bolts in conveyer belt$")
    public void there_are_machines_and_bolts_in_conveyer_belt(int machine, int bolt) {
    	computeTimeImpl.fillConveyorBeltWithGoods(machine, bolt);
    }
    
    @When("^each worker take (\\d+) seconds to assemble one product$")
    public void each_worker_take_seconds_to_assemble_one_product(int time) throws InterruptedException, ExecutionException {
    	result=computeTimeImpl.computeResult(time);
    }

    @Then("^the total product created will be (\\d+)$")
    public void the_total_product_created_will_be(int product) {
    	Assert.assertTrue("Total Products should be 3", result.get(ComputeConstants.TOTAL_PRODUCTS).longValue()==product);
    }
    
    @And("^the total time taken will be (\\d+) seconds$")
    public void the_total_time_taken_will_be_seconds(int totalTime) {
    	Assert.assertTrue("Total Time Taken should be 60 seconds ", result.get(ComputeConstants.TOTAL_TIME).longValue()==totalTime);
    }
}
