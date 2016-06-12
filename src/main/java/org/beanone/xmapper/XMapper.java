package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.FlattenerTool;

/**
 * A generic utility that maps in between two types of beans.
 *
 * @author Hongyan Li
 *
 * @param <F>
 *            the type of bean mapped from.
 * @param <T>
 *            the type of bean mapped to.
 */
public class XMapper<F, T> {
	private final XMapperConfiguration configuration;
	private final FlattenerTool flattenerTool;
	private final Map<String, String> template = new HashMap<>();

	public XMapper(FlattenerTool flattenerTool,
	        XMapperConfiguration configuration, T templateObject) {
		this.flattenerTool = flattenerTool;
		this.configuration = configuration;
		getTemplate().putAll(flattenerTool.flat(templateObject));
	}

	@SuppressWarnings("unchecked")
	public T map(F bean) {
		final Map<String, String> attributeMap = getFlattenerTool().flat(bean);
		final XMapperContext context = new XMapperContext(getConfiguration(),
		        attributeMap, getTemplate());

		attributeMap.forEach((k, v) -> {
			final KeyMapper keyMapper = new KeyMapper(
		            getConfiguration().getConfiguration());
			context.setKeyMapper(keyMapper);
			final String keyConfig = keyMapper.calculateKeyConfig(k);
			final AttributeHandler handler = getConfiguration()
		            .getAttributeHandler(keyConfig);
			handler.execute(context, k, v);
		});

		final Map<String, String> resultAttributeMap = context
		        .getResultAttributeMap();
		return (T) getFlattenerTool().unflat(resultAttributeMap);
	}

	private XMapperConfiguration getConfiguration() {
		return configuration;
	}

	private FlattenerTool getFlattenerTool() {
		return flattenerTool;
	}

	private Map<String, String> getTemplate() {
		return template;
	}
}
