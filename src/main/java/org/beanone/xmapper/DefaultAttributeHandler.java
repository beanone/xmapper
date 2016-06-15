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
		final String toValue = templateValue == null ? value
		        : XMapperUtils.merge(templateValue, value);
		context.getResultAttributeMap().put(toKey, toValue);
	}
}
