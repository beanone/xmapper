package org.beanone.xmapper;

/**
 * Abstraction of a mapping handler for attributes.
 *
 * @author Hongyan Li
 */
@FunctionalInterface
public interface AttributeHandler {
	/**
	 * Executes the handler logic.
	 *
	 * @param context
	 *            stores the context data of the current execution.
	 * @param key
	 *            the source attribute key
	 * @param value
	 *            the source attribute value - in Flattener serialized form.
	 */
	void execute(XMapperContext context, String key, String value);
}
