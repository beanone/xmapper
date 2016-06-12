package org.beanone.xmapper;

import java.util.Properties;

/**
 * Holder class for all configuration for a {@link XMapper}.
 *
 * @author Hongyan Li
 */
public class XMapperConfiguration {
	private final AttributeHandlerRegistry handlerRegistry;
	private final Properties configuration;
	private final AttributeHandler doNothingAttributeHandler = new DoNothingAttributeHandler();
	private final AttributeHandler defaultAttributeHandler = new DefaultAttributeHandler();

	/**
	 * Constructs a new instance of this.
	 *
	 * @param configuration
	 *            the mapping properties.
	 * @param handlerRegistry
	 *            the registry for {@link AttributeHandler}s.
	 */
	public XMapperConfiguration(Properties configuration,
	        AttributeHandlerRegistry handlerRegistry) {
		this.configuration = configuration;
		this.handlerRegistry = handlerRegistry;
	}

	/**
	 * Retrieves the AttributeHandler for the passed in keyConfig.
	 *
	 * @param keyConfig
	 *            a String in the form <key>:<handlerKey> where <key> is the
	 *            target attribute key and <handlerKey> is the unique key that
	 *            identifies the {@link AttributeHandler}.
	 * @return an {@link AttributeHandler}. Never null.
	 */
	public AttributeHandler getAttributeHandler(String keyConfig) {
		if (keyConfig == null) {
			return doNothingAttributeHandler;
		}
		final int index = keyConfig.indexOf(':');
		if (index == -1) {
			return defaultAttributeHandler;
		} else {
			final String handlerKey = keyConfig.substring(index + 1).trim();
			final AttributeHandler returns = getHandlerRegistry()
			        .getAttributeHandler(handlerKey);
			if (returns == null) {
				throw new IllegalArgumentException(
				        "Invalid handler key: " + handlerKey);
			}
			return returns;
		}
	}

	public Properties getConfiguration() {
		return configuration;
	}

	public AttributeHandlerRegistry getHandlerRegistry() {
		return handlerRegistry;
	}
}
