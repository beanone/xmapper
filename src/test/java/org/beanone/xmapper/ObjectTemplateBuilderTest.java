package org.beanone.xmapper;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ObjectTemplateBuilderTest {
	private final XMapperBuilder<TestSourceBean, TestTargetBean> builder = new XMapperBuilder<>(
	        (flattenerTool, handlerRegister) -> {
		        // do nothing
	        });
	private final TestObjectFactory factory = new TestObjectFactory();

	@Test
	public void testAndThatDoesNotFindMatch() {
		final TestTargetBean result = new TestTargetBean();
		final ObjectTemplateBuilder<TestSourceBean, TestTargetBean> strategy = new ObjectTemplateBuilder<>(
		        this.builder, this::criteria0, "strVal");
		strategy.and(this::criteria00, this::matcher1, "strAnotherVal").finish(
		        this.factory.createTestSourceBean(), this::map0, result);
		Assert.assertEquals(9, result.getIntValue());
		Assert.assertEquals("test", result.getStrVal());
	}

	@Test
	public void testAndThatFindsMatch() {
		final TestTargetBean result = new TestTargetBean();
		final ObjectTemplateBuilder<TestSourceBean, TestTargetBean> strategy = new ObjectTemplateBuilder<>(
		        this.builder, this::criteria0, "strVal");
		strategy.and(this::criteria00, this::matcher0, "strAnotherVal").finish(
		        this.factory.createTestSourceBean(), this::map0, result);
		Assert.assertEquals(25, result.getIntValue());
		Assert.assertEquals("teststring", result.getStrVal());
	}

	@Test
	public void testStaticExecute() {
		final TestTargetBean result = new TestTargetBean();
		ObjectTemplateBuilder.execute(this.builder, this::criteria1,
		        this.factory.createTestSourceBean(), this::map1, result);

		Assert.assertEquals(25, result.getIntValue());
		Assert.assertEquals("testtest", result.getStrVal());
	}

	@Test
	public void testXMapperBuilderBuildTemplate() {
		final TestTargetBean result = new TestTargetBean();
		this.builder.buildTemplate(this::criteria1, "1").finish(
		        this.factory.createTestSourceBean(), this::map1, result);

		Assert.assertEquals(25, result.getIntValue());
		Assert.assertEquals("testtest", result.getStrVal());
	}

	private boolean criteria0(String key, Object value, Object object) {
		return key.endsWith(".strVal") && "test".equals(value);
	}

	private boolean criteria00(String key, Object value, Object object) {
		return key.endsWith(".strAnotherVal") && "test".equals(value);
	}

	private boolean criteria1(String key, Object value, Object object) {
		return key.endsWith("Val") && "test".equals(value);
	}

	private int getIntVal(ObjectMatch match) {
		if (match == null) {
			return 0;
		}
		final TestSourceBean bean = (TestSourceBean) match.getObject();
		return bean.getIntVal();
	}

	private String getStrVal(ObjectMatch match) {
		if (match == null) {
			return "";
		}
		final TestSourceBean bean = (TestSourceBean) match.getObject();
		return bean.getStrVal();
	}

	private void map0(TestSourceBean fromBean,
	        ObjectMatchHolder matchedObjectHolder, TestTargetBean toBean) {
		final ObjectMatch m1 = matchedObjectHolder.get("strVal");
		final ObjectMatch m2 = matchedObjectHolder.get("strAnotherVal");
		toBean.setIntValue(getIntVal(m1) + getIntVal(m2));
		toBean.setStrVal(getStrVal(m1) + getStrVal(m2));
	}

	private void map1(TestSourceBean frombean, ObjectMatchHolder fromChild,
	        TestTargetBean toBean) {
		final TestSourceBean child = (TestSourceBean) fromChild.get("1")
		        .getObject();
		toBean.setIntValue(toBean.getIntValue() + child.getIntVal());
		if ("test".equals(child.getStrVal())) {
			toBean.setStrVal(child.getStrVal());
		} else {
			toBean.setStrVal(toBean.getStrVal() + child.getStrAnotherVal());
		}
	}

	private ObjectMatchHolder matcher0(
	        Map<String, ObjectMatchHolder> objectHolderMap, ObjectMatch match) {
		return objectHolderMap.get("from.strVal");
	}

	private ObjectMatchHolder matcher1(
	        Map<String, ObjectMatchHolder> objectHolderMap, ObjectMatch match) {
		return objectHolderMap.get("somethingBogus");
	}
}
