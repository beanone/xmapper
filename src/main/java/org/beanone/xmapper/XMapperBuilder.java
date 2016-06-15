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
 *            the source attribute type.
 * @param <T>
 *            the target attribute type.
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

	public MatchingStrategy<F, T> buildTemplate(String matchTypeKey,
	        Criteria criteria) {
		return new MatchingStrategy<>(this, matchTypeKey, criteria);
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
