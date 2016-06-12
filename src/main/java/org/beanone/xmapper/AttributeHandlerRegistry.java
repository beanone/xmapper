package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Map;

/**
 * The registry for all {@link AttributeHandler}s for the current instance of
 * {@link XMapper}.
 *
 * @author Hongyan Li
 */
public class AttributeHandlerRegistry {
	private final Map<Object, AttributeHandler> handlerMap = new HashMap<>();
	private final AttributeHandler defaultAttributeHandler = new DefaultAttributeHandler();

	/**
	 * Retrieves the {@link AttributeHandler} for the passed in class.
	 *
	 * @param handlerKey
	 *            the key that is used to register the handler. Null allowed
	 * @return an {@link AttributeHandler}.
	 */
	public AttributeHandler getAttributeHandler(Object handlerKey) {
		if (handlerKey == null) {
			return defaultAttributeHandler;
		}
		return handlerMap.get(handlerKey);
	}

	/**
	 * Registers the passed in handler with the handlerKey.
	 *
	 * @param handlerKey
	 *            a unique key that can be used to identify the passed in
	 *            {@link AttributeHandler} later.
	 * @param handler
	 *            an {@link AttributeHandler}.
	 */
	public void register(Object handlerKey, AttributeHandler handler) {
		handlerMap.put(handlerKey, handler);
	}
}
