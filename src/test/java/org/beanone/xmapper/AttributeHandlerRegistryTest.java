package org.beanone.xmapper;

import org.junit.Assert;
import org.junit.Test;

public class AttributeHandlerRegistryTest {

	@Test
	public void testRegister() {
		final String handlerKey = "someHandlerKey";
		final AttributeHandlerRegistry registry = new AttributeHandlerRegistry();
		Assert.assertNull(registry.getAttributeHandler(handlerKey));
		registry.register(handlerKey, (context, key, value) -> {
			// do nothing
		});
		Assert.assertNotNull(registry.getAttributeHandler(handlerKey));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterHandlerKeyNull() {
		final AttributeHandlerRegistry registry = new AttributeHandlerRegistry();
		registry.getAttributeHandler(null);
	}

}
