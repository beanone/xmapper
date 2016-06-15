package org.beanone.xmapper;

import org.junit.Assert;
import org.junit.Test;

public class ObjectMatchTest {

	@Test
	public void test() {
		final ObjectMatch match = new ObjectMatch("k", "v", "o");
		Assert.assertEquals("k", match.getMappingKey());
		Assert.assertEquals("v", match.getMappingValue());
		Assert.assertEquals("o", match.getObject());
	}

}
