package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class XMapperContextTest {
	private final XMapperContext context = new XMapperContext(
	        new XMapperConfiguration(new Properties(),
	                new AttributeHandlerRegistry()),
	        new HashMap<>(), new HashMap<>());

	@Test
	public void testSetKeyMapper() {
		final KeyMapper km = Mockito.mock(KeyMapper.class);
		final String keyConfig = "someConfig";
		Mockito.when(km.calculateKeyConfig(Mockito.anyString()))
		        .thenReturn(keyConfig);
		this.context.setKeyMapper(km, "someKey");
		Assert.assertEquals(keyConfig, this.context.getKeyConfig());
		Assert.assertNotNull(this.context.getKeyMapper());
	}

	@Test
	public void testXMapperContext() {
		Assert.assertNotNull(this.context.getAttributeMap());
		Assert.assertNotNull(this.context.getConfiguration());
		Assert.assertNotNull(this.context.getTemplate());
		Assert.assertNotNull(this.context.getResultAttributeMap());
	}

}
