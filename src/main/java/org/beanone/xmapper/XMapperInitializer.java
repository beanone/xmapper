package org.beanone.xmapper;

import org.beanone.flattener.FlattenerTool;

/**
 * Callback interface for the {@link XMapperBuilder} constructor to give client
 * an opportunity to extend the {@link XMapper} behavior.
 *
 * @author Hongyan Li
 */
@FunctionalInterface
public interface XMapperInitializer {
	/**
	 * The callback method.
	 *
	 * @param flattenerTool
	 *            the {@link FlattenerTool} to customize.
	 * @param handlerRegister
	 *            the {@link AttributeHandlerRegistry} to customize.
	 */
	void init(FlattenerTool flattenerTool,
	        AttributeHandlerRegistry handlerRegister);
}
