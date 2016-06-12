package org.beanone.xmapper;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holds context data for a {@link XMapper}.
 *
 * @author Hongyan Li
 *
 */
public class XMapperContext {
	private final Map<String, String> attributeMap = new TreeMap<>();
	private final Map<String, String> resultAttributeMap = new TreeMap<>();
	private final Map<String, String> template = new TreeMap<>();
	private final XMapperConfiguration configuration;
	private KeyMapper keyMapper;
	private String keyConfig;

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
		this.resultAttributeMap.putAll(template);
	}

	public Map<String, String> getAttributeMap() {
		return this.attributeMap;
	}

	public XMapperConfiguration getConfiguration() {
		return this.configuration;
	}

	public String getKeyConfig() {
		return this.keyConfig;
	}

	public KeyMapper getKeyMapper() {
		return this.keyMapper;
	}

	public Map<String, String> getResultAttributeMap() {
		return this.resultAttributeMap;
	}

	public Map<String, String> getTemplate() {
		return this.template;
	}

	public void setKeyMapper(KeyMapper keyMapper, String key) {
		this.keyMapper = keyMapper;
		this.keyConfig = keyMapper.calculateKeyConfig(key);
	}
}
