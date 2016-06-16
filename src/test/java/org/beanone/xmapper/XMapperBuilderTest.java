package org.beanone.xmapper;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class XMapperBuilderTest {
	private final XMapperBuilder<TestSourceBean, TestTargetBean> builder = new XMapperBuilder<>(
	        (flattenerTool, handlerRegister) -> {
		        // do nothing
	        });

	@Test
	public void testBuildMapper() {
		Assert.assertNotNull(this.builder
		        .withConfiguration(new XMapperConfiguration(new Properties(),
		                new AttributeHandlerRegistry()))
		        .withTemplateObject(new TestTargetBean()).buildMapper());
	}

	@Test(expected = IllegalStateException.class)
	public void testBuildMapperWithoutConfiguration() {
		this.builder.withTemplateObject(new TestTargetBean()).buildMapper();
	}

	@Test(expected = IllegalStateException.class)
	public void testBuildMapperWithoutConfigurationAndTemplateObject() {
		this.builder.buildMapper();
	}

	@Test(expected = IllegalStateException.class)
	public void testBuildMapperWithoutTemplateObject() {
		this.builder
		        .withConfiguration(new XMapperConfiguration(new Properties(),
		                new AttributeHandlerRegistry()))
		        .buildMapper();
	}
}
