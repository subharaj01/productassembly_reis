package com.compute.driver;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.compute.ComputeTime;
import com.compute.constants.ComputeConstants;
import com.compute.impl.ComputeTimeImpl;

public class DriverProgramme {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ComputeTime compute=new ComputeTimeImpl();
		Map<String,Long> result=compute.computeTimeToAssemble(3, 6, 60);
		System.out.println("Total Products ="+result.get(ComputeConstants.TOTAL_PRODUCTS));
		System.out.println("Total Time Taken ="+result.get(ComputeConstants.TOTAL_TIME));
	}
}
