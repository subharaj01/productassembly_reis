package com.compute;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ComputeTime {
	Map<String,Long> computeTimeToAssemble(int machines, int bolts, int timeToAssemble) throws InterruptedException, ExecutionException ;
}
