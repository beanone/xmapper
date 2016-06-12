package org.beanone.xmapper;

import org.beanone.flattener.FlattenerTool;

/**
 * Builder for {@link XMapper}.
 *
 * @author Hongyan Li
 *
 * @param <F>
 *            the source attribute type.
 * @param <T>
 *            the target attribute type.
 */
public class XMapperBuilder<F, T> {
	private final AttributeHandlerRegistry handlerRegistry = new AttributeHandlerRegistry();
	private final FlattenerTool flattenerTool = new FlattenerTool();
	private T templateObject;
	private XMapperConfiguration configuration;

	/**
	 * Constructs a new {@link XMapperBuilder}.
	 *
	 * @param initializer
	 *            the callback to initialize the {@link XMapper}.
	 */
	public XMapperBuilder(XMapperInitializer initializer) {
		initializer.init(flattenerTool, handlerRegistry);
	}

	/**
	 * Builds a new XMapper.
	 *
	 * @return a new XMapper.
	 */
	public XMapper<F, T> buildMapper() {
		if (configuration == null || templateObject == null) {
			throw new IllegalStateException(
			        "Either the configuration or the templateObject is not yet defined!");
		}

		return new XMapper<>(flattenerTool, configuration, templateObject);
	}

	/**
	 * Sets the {@link XMapperConfiguration}.
	 *
	 * @param config
	 *            a {@link XMapperConfiguration}.
	 * @return this builder so that we can chain other calls.
	 */
	public XMapperBuilder<F, T> withConfiguration(XMapperConfiguration config) {
		this.configuration = config;
		return this;
	}

	/**
	 * Sets the templateObject.
	 *
	 * @param templateObject
	 *            a template object for the target type bean.
	 * @return this builder so that we can chain other calls.
	 */
	public XMapperBuilder<F, T> withTemplateObject(T templateObject) {
		this.templateObject = templateObject;
		return this;
	}
}
