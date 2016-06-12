package org.beanone.xmapper;

/**
 * Does simple mapping from source to target without any calculation. At most it
 * does a data type conversion if needed.
 *
 * @author Hongyan Li
 *
 */
public class DefaultAttributeHandler implements AttributeHandler {
	@Override
	public void execute(XMapperContext context, String key, String value) {
		final KeyMapper keyMapper = context.getKeyMapper();
		final String toKey = keyMapper.calculateKeyConfig(key);
		final String templateValue = context.getTemplate().get(toKey);
		final String toValue = merge(templateValue, value);
		context.getResultAttributeMap().put(toKey, toValue);
	}

	private String merge(String templateValue, String value) {
		final int index = templateValue.indexOf(',');
		if (index == -1) {
			throw new IllegalArgumentException(
			        "Only valid values are primitive ones registered with PrimitiveValueRegistry");
		}

		final int index1 = value.indexOf(',');
		if (index1 == -1) {
			throw new IllegalArgumentException(
			        "Only valid values are primitive ones registered with PrimitiveValueRegistry."
			                + " Otherwise, you should implement a custom AttributeHandler.");
		}
		return templateValue.substring(0, index) + value.substring(index1 + 1);
	}
}
