package org.beanone.xmapper;

/**
 * The {@link AttributeHandler} that does nothing when invoked.
 *
 * @author Hongyan Li
 *
 */
public class DoNothingAttributeHandler implements AttributeHandler {

	@Override
	public void execute(XMapperContext context, String key, String value) {
		// do nothing
	}

}
