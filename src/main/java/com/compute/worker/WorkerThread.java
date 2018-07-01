package com.compute.worker;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.compute.constants.ComputeConstants;
import com.compute.impl.ComputeTimeImpl;
import com.compute.model.GoodsModel;

public class WorkerThread implements Callable<Long> {

	private int timeToAssemble;
	static Log log = LogFactory.getLog(WorkerThread.class.getName());

	public WorkerThread(int timeToAssemble) {
		this.timeToAssemble = timeToAssemble * 1000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Long call() throws Exception {
		Long count = 0L;
		GoodsModel bolt1 = null;
		GoodsModel bolt2 = null;
		GoodsModel machine = null;
		while (!ComputeTimeImpl.isGoodsFinished()) {
			// the pickup of items has from belt has to be synchronized.
			// otherwise there can be a case that belt has 2 bolts and 1 machine
			// and each 3 worker has taken each of them. Hence,assembly is not
			// possible although we have sufficient unfinished goods
			synchronized (ComputeTimeImpl.class) {
				bolt1 = ComputeTimeImpl.pickupOneGood(ComputeConstants.BOLT);
				if (bolt1 != null) {
					machine = ComputeTimeImpl.pickupOneGood(ComputeConstants.MACHINE);
					bolt2 = ComputeTimeImpl.pickupOneGood(ComputeConstants.BOLT);
				}
			}
			count = count + assemble(bolt1, bolt2, machine);
		}
		return count;
	}

	/**
	 * here, we are doing the assembly which is done in parallel using 3 workers
	 * 
	 * @param bolt1
	 * @param bolt2
	 * @param machine
	 * @return
	 * @throws InterruptedException
	 */
	private Long assemble(GoodsModel bolt1, GoodsModel bolt2, GoodsModel machine) throws InterruptedException {
		if (bolt1 != null && bolt2 != null && machine != null) {
			try {
				Thread.sleep(this.timeToAssemble);
			} catch (InterruptedException e) {
				log.error(e);
				throw e;
			}
			return 1L;
		}
		return 0L;
	}
}