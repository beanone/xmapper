package org.beanone.xmapper;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XMapperConfigurationTest {
	private XMapperConfiguration mappingConfig;

	@Before
	public void setup() throws Exception {
		final Properties configuration = TestUtil
		        .loadMappingProperties("test_mapping.properties");
		this.mappingConfig = new XMapperConfiguration(configuration,
		        new AttributeHandlerRegistry());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeHandlerResolveNull() {
		this.mappingConfig
		        .getAttributeHandler("target.attribute.key:handler.key");
	}

	@Test
	public void testGetAttributeHandlerResolveToCustomAttributeHandler() {
		final String handlerKey = "serializeTestSourceBean";
		this.mappingConfig.getHandlerRegistry()
		        .register("serializeTestSourceBean", (context, key, value) -> {
			        // do nothing
		        });
		Assert.assertNotNull(this.mappingConfig
		        .getAttributeHandler("target.attribute.key:" + handlerKey));
	}

	@Test
	public void testGetAttributeHandlerResolveToDefaultAttributeHandler() {
		final AttributeHandler handler = this.mappingConfig
		        .getAttributeHandler("intVal");
		Assert.assertNotNull(handler);
		Assert.assertTrue(handler instanceof DefaultAttributeHandler);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeHandlerResolveToDefaultHandler() {
		this.mappingConfig.getAttributeHandler(null);
	}

	@Test
	public void testXMapper() {
		final XMapperConfiguration mapperConfig = new XMapperConfiguration(
		        new Properties(), new AttributeHandlerRegistry());
		Assert.assertNotNull(mapperConfig.getConfiguration());
		Assert.assertNotNull(mapperConfig.getHandlerRegistry());
		Assert.assertNotNull(mapperConfig.getKeyMapper());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testXMapperAttributeHandlerRegistryNull() {
		new XMapperConfiguration(new Properties(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testXMapperConfigurationConfigurationNull() {
		new XMapperConfiguration(null, new AttributeHandlerRegistry());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testXMapperParametersNull() {
		new XMapperConfiguration(null, null);
	}
}
