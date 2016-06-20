package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.FlattenerContants;
import org.beanone.flattener.FlattenerTool;

/**
 * Utility methods used by the XMapper.
 *
 * @author Hongyan Li
 *
 */
public class XMapperUtils {
	private final FlattenerTool flattenerTool;

	/**
	 * Construct a new instance of this.
	 *
	 * @param flattenerTool
	 *            the {@link FlattenerTool} this utility is associated with
	 */
	public XMapperUtils(FlattenerTool flattenerTool) {
		this.flattenerTool = flattenerTool;
	}

	public static String merge(String templateValue, String value) {
		final int index = templateValue.indexOf(',');
		final int index1 = value.indexOf(',');
		if (index == -1 && index1 == -1) {
			return templateValue;
		} else if (index >= 0 && index1 >= 0) {
			return templateValue.substring(0, index) + value.substring(index1);
		} else {
			throw new IllegalArgumentException(
			        "Only valid values are primitive ones registered with PrimitiveValueRegistry."
			                + " Otherwise, you should implement a custom AttributeHandler.");
		}
	}

	/**
	 * Retrieves / constructs the object attribute from the attributeMap.
	 *
	 * @param classKey
	 *            the class key.
	 * @param attributesMap
	 *            the attribute map of the parent object of the object attribute
	 *            to be retrieved.
	 * @return the object attribute.
	 */
	public Object getObject(String classKey,
	        Map<String, String> attributesMap) {
		final int start = classKey.length()
		        - FlattenerContants.CTYPE_SUFFIX.length() + 1;
		final Map<String, String> objectAttributesMap = new HashMap<>();
		objectAttributesMap.put(FlattenerContants.CTYPE_SUFFIX,
		        attributesMap.get(classKey));
		final String prefix = classKey.substring(0, start - 1)
		        + FlattenerContants.ATTRIBUTE_SEPARATE;
		attributesMap.forEach((k, v) -> {
			if (k.startsWith(prefix)) {
				objectAttributesMap.put(k.substring(start),
		                attributesMap.get(k));
			}
		});

		return this.flattenerTool.unflat(objectAttributesMap);
	}
}
