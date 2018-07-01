package com.compute.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.compute.ComputeTime;
import com.compute.constants.ComputeConstants;
import com.compute.exception.ValidationException;
import com.compute.model.GoodsModel;
import com.compute.worker.WorkerThread;

public class ComputeTimeImpl implements ComputeTime {

	private static LinkedList<GoodsModel> goods = new LinkedList<>();

	static Log log = LogFactory.getLog(ComputeTimeImpl.class.getName());

	@Override
	public Map<String,Long> computeTimeToAssemble(int machines, int bolts, int timeToAssemble) throws InterruptedException, ExecutionException {
		fillConveyorBeltWithGoods(machines, bolts);
		return computeResult(timeToAssemble);
	}

	/**
	 * actual logic of computation
	 * we have used executor service of 3 worker thread here
	 * 
	 * @param timeToAssemble
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Map<String,Long> computeResult(int timeToAssemble) throws InterruptedException, ExecutionException {
		Map<String,Long> result = new HashMap<>();
		List<Future<Long>> list = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(3);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 3; i++) {
			Callable<Long> worker = new WorkerThread(timeToAssemble);
			Future<Long> future = executor.submit(worker);
			list.add(future);
		}

		Long count = 0L;
		for (Future<Long> fut : list) {
			try {
				count = count + fut.get();
			} catch (InterruptedException | ExecutionException e) {
				log.error(e);
				throw e;
			}
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
			log.trace("waiting for employees to finish their work");
		}
		long totalTime = (System.currentTimeMillis() - startTime) / 1000;
		//store the result in expected format in a map
		result.put(ComputeConstants.TOTAL_PRODUCTS, count);
		result.put(ComputeConstants.TOTAL_TIME, totalTime);
		return result;
	}

	/**
	 * pick one good from conveyer belt based on the type (bolt or machine), if available
	 * if no such good available, return null
	 * 
	 * @param type
	 * @return GoodsModel
	 */
	public static GoodsModel pickupOneGood(String type) {
		GoodsModel data = null;
		if (ComputeConstants.BOLT.equals(type)) {
			Optional<GoodsModel> optional = goods.stream().filter(e -> ComputeConstants.BOLT.equals(e.getType()))
					.findAny();
			if (optional.isPresent()) {
				data = optional.get();
				goods.remove(data);
			}
		} else if (ComputeConstants.MACHINE.equals(type)) {
			Optional<GoodsModel> optional = goods.stream().filter(e -> ComputeConstants.MACHINE.equals(e.getType()))
					.findAny();
			if (optional.isPresent()) {
				data = optional.get();
				goods.remove(data);
			}
		}
		return data;
	}
	
	/**
	 * check if goods finished or not
	 * 
	 * @return boolean
	 */
	public static boolean isGoodsFinished() {
		return goods.isEmpty();
	}
	
	/**
	 * fill conveyer belt with unfinished goods, maintain bolt and machine alternate sequence till 
	 * machines are there
	 * 
	 * @param machines
	 * @param bolts
	 */
	public void fillConveyorBeltWithGoods(int machines, int bolts) {
		validateGoods(machines, bolts);
		for (int i = 0; i < bolts; i++) {
			// ensure alternate sequence of bolt and machine is maintained
			// till there is machines available
			GoodsModel bolt = new GoodsModel();
			bolt.setId(i + 1);
			bolt.setType(ComputeConstants.BOLT);
			goods.add(bolt);
			if (machines > 0) {
				GoodsModel machine = new GoodsModel();
				machine.setId(i + 1);
				machine.setType(ComputeConstants.MACHINE);
				goods.add(machine);
				machines--;
			}
		}
	}

	/**
	 * validate count of bolts and machine so that they can be assembled
	 * 
	 * @param machines
	 * @param bolts
	 */
	public void validateGoods(int machines, int bolts) {
		if (machines < 1) {
			throw new ValidationException("number of machines provided to assemble is less than 1");
		} else if (bolts < 1) {
			throw new ValidationException("number of bolts provided to assemble is less than 1");
		}
		else if (machines * 2 > bolts) {
			throw new ValidationException("number of bolts provided to assemble is not sufficient! bolts count:" + bolts);
		}
		else if (machines * 2 < bolts) {
			throw new ValidationException("there are excess bolts in conveyer belt! bolts count:" + bolts);
		}
	}
}
