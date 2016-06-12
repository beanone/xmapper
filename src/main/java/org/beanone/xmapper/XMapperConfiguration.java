package org.beanone.xmapper;

import java.util.Properties;

/**
 * Holder configuration data for a {@link XMapper}.
 *
 * @author Hongyan Li
 */
public class XMapperConfiguration {
	private final AttributeHandlerRegistry handlerRegistry;
	private final Properties configuration;
	private final AttributeHandler defaultAttributeHandler = new DefaultAttributeHandler();
	private final KeyMapper keyMapper;

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
		if (configuration == null || handlerRegistry == null) {
			throw new IllegalArgumentException(
			        "The configuration or handlerRegistry is null!");
		}
		this.configuration = configuration;
		this.keyMapper = new KeyMapper(configuration);
		this.handlerRegistry = handlerRegistry;
	}

	/**
	 * Retrieves the AttributeHandler for the passed in keyConfig.
	 *
	 * @param keyConfig
	 *            the attribute key configuration.
	 * @return an {@link AttributeHandler}. Never null.
	 */
	public AttributeHandler getAttributeHandler(String keyConfig) {
		if (keyConfig == null) {
			throw new IllegalArgumentException("The keyConfig is null!");
		}
		final int index = keyConfig.indexOf(':');
		if (index == -1) {
			return this.defaultAttributeHandler;
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
		return this.configuration;
	}

	public AttributeHandlerRegistry getHandlerRegistry() {
		return this.handlerRegistry;
	}

	public KeyMapper getKeyMapper() {
		return this.keyMapper;
	}
}
