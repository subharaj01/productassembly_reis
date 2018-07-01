package com.compute.exception.test;

import org.junit.Test;

import com.compute.ComputeTime;
import com.compute.exception.ValidationException;
import com.compute.impl.ComputeTimeImpl;

public class ValidationExceptionTest {

	@Test(expected = ValidationException.class)
	public void validateCaseForNoBolts() throws Exception {
		ComputeTime compute = new ComputeTimeImpl();
		compute.computeTimeToAssemble(3, 0, 10);
	}

	@Test(expected = ValidationException.class)
	public void validateCaseForNoMachine() throws Exception {
		ComputeTime compute = new ComputeTimeImpl();
		compute.computeTimeToAssemble(0, 2, 10);
	}

	@Test(expected = ValidationException.class)
	public void validateCaseForExcessBolts() throws Exception {
		ComputeTime compute = new ComputeTimeImpl();
		compute.computeTimeToAssemble(3, 8, 10);
	}

	@Test(expected = ValidationException.class)
	public void validateCaseForExcessMachines() throws Exception {
		ComputeTime compute = new ComputeTimeImpl();
		compute.computeTimeToAssemble(5, 8, 10);
	}
}
