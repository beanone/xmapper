package org.beanone.xmapper;

import java.util.List;

import org.beanone.flattener.FlattenerTool;
import org.beanone.xmapper.exception.TestSourceBean;
import org.beanone.xmapper.exception.TestTargetBean;
import org.junit.Assert;
import org.junit.Test;

public class XObjectBuilderTest {

	@Test
	public void testCollect() {
		final XObjectBuilder<TestSourceBean, TestTargetBean> builder = new XObjectBuilder<>(
		        new FlattenerTool(), new TestTargetBean(),
		        createTestSourceBean());
		final ObjectMatchCollector<TestSourceBean, TestTargetBean> collector = builder
		        .collect("strVal", (key, value) -> {
			        if (key.endsWith(".strVal") && "test".equals(value)) {
				        return true;
			        } else {
				        return false;
			        }
		        }).andEach("strAnotherVal", (key, value) -> {
			        if (key.endsWith(".strAnotherVal")
		                    && "test".equals(value)) {
				        return true;
			        } else {
				        return false;
			        }
		        }, (objectHolderMap, match) -> {
			        final TestSourceBean matchedBean = (TestSourceBean) match
		                    .getObject();
			        return objectHolderMap.get("from.strVal");
		        });
		final TestTargetBean result = collector
		        .map((fromBean, matchedObjectHolder, toBean) -> {
			        final ObjectMatch m1 = matchedObjectHolder.get("strVal");
			        final ObjectMatch m2 = matchedObjectHolder
		                    .get("strAnotherVal");
			        toBean.setIntValue(getIntVal(m1) + getIntVal(m2));
			        toBean.setStrVal(getStrVal(m1) + getStrVal(m2));
		        }).done().finishAndGet();
		Assert.assertNotNull(result);
		Assert.assertEquals(25, result.getIntValue());
		Assert.assertEquals("teststring", result.getStrVal());
	}

	@Test
	public void testFindAllBy() {
		final XObjectBuilder<TestSourceBean, TestTargetBean> builder = new XObjectBuilder<>(
		        new FlattenerTool(), new TestTargetBean(),
		        createTestSourceBean());
		final List<ObjectMatch> matches = builder.findAllBy((key, value) -> {
			if (key.endsWith("Val") && "test".equals(value)) {
				return true;
			} else {
				return false;
			}
		});

		Assert.assertEquals(2, matches.size());
		Assert.assertEquals("another.strAnotherVal",
		        matches.get(0).getMappingKey());
		Assert.assertEquals("S,test", matches.get(0).getMappingValue());
		Assert.assertEquals("from.strVal", matches.get(1).getMappingKey());
		Assert.assertEquals("S,test", matches.get(1).getMappingValue());
	}

	@Test
	public void testMapBy() {
		final XObjectBuilder<TestSourceBean, TestTargetBean> builder = new XObjectBuilder<>(
		        new FlattenerTool(), new TestTargetBean(),
		        createTestSourceBean());
		builder.mapBy((key, value) -> {
			if (key.endsWith("Val") && "test".equals(value)) {
				return true;
			} else {
				return false;
			}
		}, (fromBean, fromChild, toBean) -> {
			final TestSourceBean child = (TestSourceBean) fromChild.get("1")
		            .getObject();
			toBean.setIntValue(toBean.getIntValue() + child.getIntVal());
			if ("test".equals(child.getStrVal())) {
				toBean.setStrVal(child.getStrVal());
			} else {
				toBean.setStrVal(toBean.getStrVal() + child.getStrAnotherVal());
			}
		});

		final TestTargetBean result = builder.finishAndGet();
		Assert.assertNotNull(result);
		Assert.assertEquals(25, result.getIntValue());
		Assert.assertEquals("testtest", result.getStrVal());
	}

	@Test
	public void testXObjectBuilder() {
		final Object templateObject = new Object();
		final XObjectBuilder<Object, Object> builder = new XObjectBuilder<>(
		        new FlattenerTool(), templateObject, new Object());
		Assert.assertSame(templateObject, builder.finishAndGet());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testXObjectBuilderFlattenerToolNull() {
		new XObjectBuilder<Object, Object>(null, new Object(), new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testXObjectBuilderSourceObjectNull() {
		new XObjectBuilder<Object, Object>(new FlattenerTool(), null,
		        new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testXObjectBuilderTemplateObjectNull() {
		new XObjectBuilder<Object, Object>(new FlattenerTool(), new Object(),
		        null);
	}

	private TestSourceBean createTestSourceBean() {
		final TestSourceBean sourceBean = new TestSourceBean();
		sourceBean.setIntVal(20);
		sourceBean.setStrVal("string1");

		final TestSourceBean from = new TestSourceBean();
		from.setStrVal("test");
		from.setIntVal(9);
		sourceBean.setFrom(from);

		final TestSourceBean another = new TestSourceBean();
		another.setStrAnotherVal("test");
		another.setIntVal(16);
		sourceBean.setAnother(another);
		return sourceBean;
	}

	private int getIntVal(ObjectMatch match) {
		final TestSourceBean bean = (TestSourceBean) match.getObject();
		return bean.getIntVal();
	}

	private String getStrVal(ObjectMatch match) {
		final TestSourceBean bean = (TestSourceBean) match.getObject();
		return bean.getStrVal();
	}
}
