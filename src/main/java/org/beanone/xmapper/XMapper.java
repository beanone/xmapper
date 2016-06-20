package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.FlattenerContants;
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

	/**
	 * Construct a new instance of this.
	 *
	 * @param flattenerTool
	 *            the FlattenerTool to flatten beans.
	 * @param configuration
	 *            the {@link XMapperConfiguration} for the XMapper.
	 * @param templateObject
	 *            the target bean type template object.
	 */
	public XMapper(FlattenerTool flattenerTool,
	        XMapperConfiguration configuration, T templateObject) {
		this.flattenerTool = flattenerTool;
		this.configuration = configuration;
		getTemplate().putAll(flattenerTool.flat(templateObject));
	}

	/**
	 * Maps the passed in source type bean to the target type bean this XMapper
	 * supports.
	 *
	 * @param bean
	 *            a source type bean instance.
	 * @return a target type bean instance that the passed in bean maps to.
	 */
	@SuppressWarnings("unchecked")
	public T map(F bean) {
		final Map<String, String> attributeMap = getFlattenerTool().flat(bean);
		final XMapperContext context = new XMapperContext(getConfiguration(),
		        attributeMap, getTemplate());

		attributeMap.forEach((k, v) -> {
			if (k.indexOf(FlattenerContants.SUFFIX_SEPARATE) < 0) {
				context.setKeyMapper(getConfiguration().getKeyMapper(), k);
				final AttributeHandler handler = getConfiguration()
		                .getAttributeHandler(context.getKeyConfig());
				handler.execute(context, k, v);
			}
		});

		final Map<String, String> resultAttributeMap = context
		        .getResultAttributeMap();
		return (T) getFlattenerTool().unflat(resultAttributeMap);
	}

	private XMapperConfiguration getConfiguration() {
		return this.configuration;
	}

	private FlattenerTool getFlattenerTool() {
		return this.flattenerTool;
	}

	private Map<String, String> getTemplate() {
		return this.template;
	}
}
