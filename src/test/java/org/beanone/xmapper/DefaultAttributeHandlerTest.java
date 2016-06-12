package org.beanone.xmapper;

import org.junit.Assert;
import org.junit.Test;

public class DefaultAttributeHandlerTest {
	private final TestObjectFactory objectFactory = new TestObjectFactory();

	@Test
	public void testExecute() {
		final AttributeHandler handler = new DefaultAttributeHandler();
		final XMapperContext context = new XMapperContext(
		        this.objectFactory.getMappingConfig(),
		        this.objectFactory.getAttributeMap(),
		        this.objectFactory.getTemplate());
		context.setKeyMapper(
		        new KeyMapper(this.objectFactory.getConfiguration()), "intVal");
		handler.execute(context, "intVal", "I,30");
		final String value = context.getResultAttributeMap().get("intValue");
		Assert.assertEquals("I,30", value);
	}

	@Test
	public void testExecuteForAttributeWithoutTemplateAttribute() {
		final AttributeHandler handler = new DefaultAttributeHandler();
		final XMapperContext context = new XMapperContext(
		        this.objectFactory.getMappingConfig(),
		        this.objectFactory.getAttributeMap(),
		        this.objectFactory.getTemplate());
		context.setKeyMapper(
		        new KeyMapper(this.objectFactory.getConfiguration()), "intVal");
		final String fromKey = "from";
		final String toKey = this.objectFactory.getMappingConfig()
		        .getKeyMapper().calculateKeyConfig(fromKey);
		Assert.assertNull(this.objectFactory.getTemplate().get(toKey));
		handler.execute(context, fromKey, "from#ref");
	}

}
