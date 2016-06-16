package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.FlattenerTool;

/**
 * Builder for {@link XMapper}.
 *
 * @author Hongyan Li
 *
 * @param <F>
 *            the type of bean mapped from.
 * @param <T>
 *            the type of bean mapped to.
 */
public class XMapperBuilder<F, T> {
	private final AttributeHandlerRegistry handlerRegistry = new AttributeHandlerRegistry();
	private final FlattenerTool flattenerTool = new FlattenerTool();
	private T templateObject;
	private final Map<String, String> attributesMap = new HashMap<>();

	private XMapperConfiguration configuration;

	/**
	 * Constructs a new {@link XMapperBuilder}.
	 *
	 * @param initializer
	 *            the callback to initialize the {@link XMapper}.
	 */
	public XMapperBuilder(XMapperInitializer initializer) {
		initializer.init(this.flattenerTool, this.handlerRegistry);
	}

	/**
	 * Builds a new XMapper.
	 *
	 * @return a new XMapper.
	 */
	public XMapper<F, T> buildMapper() {
		if (this.configuration == null || this.templateObject == null) {
			throw new IllegalStateException(
			        "Either the configuration or the templateObject is not yet defined!");
		}

		return new XMapper<>(this.flattenerTool, this.configuration,
		        this.templateObject);
	}

	/**
	 * Builds a new template object.
	 *
	 * @param criteria
	 *            a criteria used to identify certain attributes of the source
	 *            bean.
	 * @param matchTypeKey
	 *            a unique key used to uniquely identify the type of match so
	 *            that different type of matches can be combined to help make
	 *            decision.
	 * @return an {@link ObjectTemplateBuilder} that can be used to build target
	 *         bean template. The template can be used by the XMapper to build
	 *         the final target bean object.
	 */
	public ObjectTemplateBuilder<F, T> buildTemplate(Criteria criteria,
	        String matchTypeKey) {
		return new ObjectTemplateBuilder<>(this, criteria, matchTypeKey);
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

	FlattenerTool getFlattenerTool() {
		return this.flattenerTool;
	}

	void setAttributesMap(Map<String, String> map) {
		this.attributesMap.clear();
		this.attributesMap.putAll(map);
	}

	void setTemplateObject(T templateObject) {
		this.templateObject = templateObject;
	}
}
