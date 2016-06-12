package org.beanone.xmapper;

import org.beanone.xmapper.exception.TestSourceBean;
import org.beanone.xmapper.exception.TestTargetBean;
import org.junit.Assert;
import org.junit.Test;

public class XMapperTest {
	private final TestObjectFactory objectFactory = new TestObjectFactory();
	private final XMapper<TestSourceBean, TestTargetBean> mapper = new XMapper<>(
	        this.objectFactory.getFlattenerTool(),
	        this.objectFactory.getMappingConfig(),
	        this.objectFactory.getTemplateObject());

	@Test
	public void testMap() {
		final TestTargetBean result = this.mapper
		        .map(this.objectFactory.getSourceBean());
		Assert.assertNotNull(result);
		Assert.assertEquals(20, result.getIntValue());
		Assert.assertEquals("string1", result.getStrVal());
		Assert.assertEquals(10, result.getTo().getIntValue());
		Assert.assertEquals("string", result.getTo().getStrVal());
	}
}
