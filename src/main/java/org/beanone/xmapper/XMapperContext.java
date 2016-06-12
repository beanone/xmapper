package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds context data for a {@link XMapper}.
 *
 * @author Hongyan Li
 *
 */
public class XMapperContext {
	private final Map<String, String> attributeMap = new HashMap<>();
	private final Map<String, String> resultAttributeMap = new HashMap<>();
	private final Map<String, String> template = new HashMap<>();
	private final XMapperConfiguration configuration;
	private KeyMapper keyMapper;

	/**
	 * Constructs a new instance of this.
	 *
	 * @param configuration
	 *            a {@link XMapperConfiguration}.
	 * @param attributeMap
	 *            the attributeMap of the source bean.
	 * @param template
	 *            the template attributeMap of the target bean.
	 */
	public XMapperContext(XMapperConfiguration configuration,
	        Map<String, String> attributeMap, Map<String, String> template) {
		this.configuration = configuration;
		this.attributeMap.putAll(attributeMap);
		this.template.putAll(template);
	}

	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	public XMapperConfiguration getConfiguration() {
		return configuration;
	}

	public KeyMapper getKeyMapper() {
		return keyMapper;
	}

	public Map<String, String> getResultAttributeMap() {
		return resultAttributeMap;
	}

	public Map<String, String> getTemplate() {
		return template;
	}

	public void setKeyMapper(KeyMapper keyMapper) {
		this.keyMapper = keyMapper;
	}
}
