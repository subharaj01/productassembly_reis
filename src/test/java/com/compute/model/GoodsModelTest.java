package com.compute.model;

import org.junit.Assert;
import org.junit.Test;

import com.compute.constants.ComputeConstants;

public class GoodsModelTest {

	@Test 
	public void goodsModelTest() throws Exception {
		GoodsModel data=new GoodsModel();
		data.setId(1);
		data.setType(ComputeConstants.BOLT);
		Assert.assertTrue("Id should be 1", data.getId()==1);
		Assert.assertTrue("Type should be bolt", data.getType().equals(ComputeConstants.BOLT));
	}
}
