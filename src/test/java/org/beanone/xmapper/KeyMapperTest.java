package org.beanone.xmapper;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KeyMapperTest {
	private KeyMapper keyMapper;

	@Before
	public void setup() throws Exception {
		final Properties configuration = TestUtil
		        .loadMappingProperties("test_mapping.properties");
		keyMapper = new KeyMapper(configuration);
	}

	@Test
	public void testCalculateKeyConfig() throws Exception {
		String result = keyMapper.calculateKeyConfig("from.strVal");
		Assert.assertEquals("to.strVal", result);
		result = keyMapper.calculateKeyConfig("strVal");
		Assert.assertEquals("strVal", result);
	}

	@Test
	public void testCalculateKeyConfigNoCalculation() throws Exception {
		String result = keyMapper.calculateKeyConfig("from.intVal");
		Assert.assertEquals("to.intValue", result);
		result = keyMapper.calculateKeyConfig("intVal");
		Assert.assertEquals("intValue", result);
		result = keyMapper.calculateKeyConfig("from");
		Assert.assertEquals("to", result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCalculateKeyConfigWithEmptyMappingEntryForFromKey()
	        throws Exception {
		keyMapper.calculateKeyConfig(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCalculateKeyConfigWithNullFromKey() throws Exception {
		keyMapper.calculateKeyConfig("  ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKeyMapperPropertiesNull() {
		new KeyMapper(null);
	}
}
