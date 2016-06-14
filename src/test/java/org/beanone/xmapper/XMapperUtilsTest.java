package org.beanone.xmapper;

import java.util.Map;

import org.beanone.flattener.FlattenerTool;
import org.beanone.xmapper.exception.TestSourceBean;
import org.junit.Assert;
import org.junit.Test;

public class XMapperUtilsTest {

	@Test
	public void testGetObject() {
		final TestObjectFactory factory = new TestObjectFactory();
		final TestSourceBean bean = factory.getSourceBean();
		final FlattenerTool tool = new FlattenerTool();
		final XMapperUtils utils = new XMapperUtils(tool);
		final Map<String, String> attributesMap = tool.flat(bean);
		attributesMap.put("from.intVal", "I,30");
		final Object result = utils.getObject("from#1ctype", attributesMap);
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof TestSourceBean);
		final TestSourceBean tsb = (TestSourceBean) result;
		Assert.assertEquals(30, tsb.getIntVal());
		Assert.assertEquals("string", tsb.getStrVal());
		Assert.assertNull(tsb.getFrom());
	}

}
