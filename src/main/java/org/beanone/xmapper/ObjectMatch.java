package org.beanone.xmapper;

/**
 * Stores the matching object the related info.
 *
 * @author Hongyan Li
 */
public class ObjectMatch {
	private final String mappingKey;
	private final String mappingValue;
	private final Object object;

	/**
	 * Constructs a new instance of this.
	 *
	 * @param mappingKey
	 *            the attribute mapping key per to the match.
	 * @param mappingValue
	 *            the attribute mapping value matched.
	 * @param object
	 *            the object that contains the match.
	 */
	public ObjectMatch(String mappingKey, String mappingValue, Object object) {
		this.mappingKey = mappingKey;
		this.mappingValue = mappingValue;
		this.object = object;
	}

	public String getMappingKey() {
		return this.mappingKey;
	}

	public String getMappingValue() {
		return this.mappingValue;
	}

	public Object getObject() {
		return this.object;
	}
}
