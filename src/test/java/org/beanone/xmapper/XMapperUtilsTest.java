package org.beanone.xmapper;

import java.util.Map;

import org.beanone.flattener.FlattenerContants;
import org.beanone.flattener.FlattenerTool;
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
		final Object result = utils.getObject(
		        "from" + FlattenerContants.CTYPE_SUFFIX, attributesMap);
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof TestSourceBean);
		final TestSourceBean tsb = (TestSourceBean) result;
		Assert.assertEquals(30, tsb.getIntVal());
		Assert.assertEquals("string", tsb.getStrVal());
		Assert.assertNull(tsb.getFrom());
	}

	@Test
	public void testMerge() {
		Assert.assertEquals("I,2", XMapperUtils.merge("I,1", "S,2"));
		Assert.assertEquals("AAA", XMapperUtils.merge("AAA", "BBB"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMergeTemplateValueNotPrimitive() {
		XMapperUtils.merge("AAA", "S,2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMergeValueNotPrimitive() {
		XMapperUtils.merge("I,1", "AAA");
	}
}
