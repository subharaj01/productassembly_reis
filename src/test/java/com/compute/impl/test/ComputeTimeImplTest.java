package com.compute.impl.test;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.compute.ComputeTime;
import com.compute.constants.ComputeConstants;
import com.compute.impl.ComputeTimeImpl;

public class ComputeTimeImplTest {
	
	@Test 
	public void testE2EOutputWhenMachineThreeBoltSix() throws Exception {
		ComputeTime compute=new ComputeTimeImpl();
		Map<String,Long> result=compute.computeTimeToAssemble(3, 6, 60);
		Assert.assertTrue("Total Products should be 3", result.get(ComputeConstants.TOTAL_PRODUCTS).longValue()==3);
		Assert.assertTrue("Total Time Taken should be 60 seconds ", result.get(ComputeConstants.TOTAL_TIME).longValue()==60);
	}
	
	@Test 
	public void testE2EOutputWhenMachineTwoBoltFour() throws Exception {
		ComputeTime compute=new ComputeTimeImpl();
		Map<String,Long> result=compute.computeTimeToAssemble(2, 4, 60);
		Assert.assertTrue("Total Products should be 2", result.get(ComputeConstants.TOTAL_PRODUCTS).longValue()==3);
		Assert.assertTrue("Total Time Taken should be 60 seconds ", result.get(ComputeConstants.TOTAL_TIME).longValue()==60);
	}
	
	@Test 
	public void testE2EOutputWhenMachineFourBoltEight() throws Exception {
		ComputeTime compute=new ComputeTimeImpl();
		Map<String,Long> result=compute.computeTimeToAssemble(4, 8, 10);
		Assert.assertTrue("Total Products should be 4", result.get(ComputeConstants.TOTAL_PRODUCTS).longValue()==4);
		Assert.assertTrue("Total Time Taken should be 20 seconds ", result.get(ComputeConstants.TOTAL_TIME).longValue()==20);
	}
	
	@Test 
	public void testFillConveyorBeltWithGoods() throws Exception {
		ComputeTimeImpl compute=new ComputeTimeImpl();
		compute.fillConveyorBeltWithGoods(2, 4);
		Assert.assertTrue("goods should not be empty", !ComputeTimeImpl.isGoodsFinished());
	}
	
	@Test 
	public void testComputeResult() throws Exception {
		ComputeTimeImpl compute=new ComputeTimeImpl();
		compute.fillConveyorBeltWithGoods(2, 4);
		Map<String,Long> result=compute.computeResult(5);
		Assert.assertTrue("Total Products should be 2", result.get(ComputeConstants.TOTAL_PRODUCTS).longValue()==2);
		Assert.assertTrue("Total Time Taken should be 5 seconds ", result.get(ComputeConstants.TOTAL_TIME).longValue()==5);
	}
}
