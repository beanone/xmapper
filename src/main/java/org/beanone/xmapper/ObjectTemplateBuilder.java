package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.api.FlattenerCallback;

/**
 * A builder for object template.
 *
 * @author Hongyan Li
 *
 * @param <F>
 *            the type of bean mapped from.
 * @param <T>
 *            the type of bean mapped to.
 */
public class ObjectTemplateBuilder<F, T> {
	private boolean executed = false;

	private final Map<String, ObjectMatchHolder> objectHolderMap = new HashMap<>();
	private final XMapperBuilder<F, T> builder;
	private FlattenerCallback callback;

	/**
	 * Constructs a new instance of this.
	 *
	 * @param builder
	 *            the owning XMapperBuilder of this.
	 * @param criteria
	 *            a criteria used to identify certain attributes of the source
	 *            bean.
	 * @param matchTypeKey
	 *            a unique key used to uniquely identify the type of match so
	 *            that different type of matches can be combined to help make
	 *            decision.
	 */
	public ObjectTemplateBuilder(XMapperBuilder<F, T> builder,
	        Criteria criteria, String matchTypeKey) {
		this.builder = builder;
		this.callback = (k, v, vo, o) -> {
			if (criteria.test(k, vo, o)) {
				this.objectHolderMap.put(k, new ObjectMatchHolder(matchTypeKey,
		                new ObjectMatch(k, v, o)));
			}
		};
	}

	public static <F, T> void execute(XMapperBuilder<F, T> builder,
	        Criteria criteria, F fromBean, ObjectMapper<F, T> mapper,
	        T toBean) {
		final ObjectTemplateBuilder<F, T> strategy = new ObjectTemplateBuilder<>(
		        builder, criteria, "1");
		strategy.finish(fromBean, mapper, toBean);
	}

	/**
	 * Add a criteria for another type of matches.
	 *
	 * @param criteria
	 *            a criteria used to identify certain attributes of the source
	 *            bean.
	 * @param matcher
	 *            a matching logic that helps to group the different types of
	 *            matches found by the criterias.
	 * @param matchTypeKey
	 *            a unique key used to uniquely identify the type of match so
	 *            that different type of matches can be combined to help make
	 *            decision.
	 * @return
	 */
	public ObjectTemplateBuilder<F, T> addCriteria(Criteria criteria,
	        ObjectMatcher<ObjectMatch> matcher, String matchTypeKey) {
		this.callback = this.callback.and((k, v, vo, o) -> {
			if (criteria.test(k, vo, o)) {
				final ObjectMatch match = new ObjectMatch(k, v, o);
				final ObjectMatchHolder h = matcher.match(this.objectHolderMap,
		                match);
				if (h != null) {
					h.put(matchTypeKey, match);
				}
			}
		});
		return this;
	}

	/**
	 * Finish building of the template.
	 *
	 * @param fromBean
	 *            the source bean object.
	 * @param mapper
	 *            mapping that determines how the target bean is created from
	 *            the matches found.
	 * @param toBean
	 *            the target bean object, which is the template we are building.
	 * @return
	 */
	public XMapperBuilder<F, T> finish(F fromBean, ObjectMapper<F, T> mapper,
	        T toBean) {
		if (!this.executed) {
			this.builder.setAttributesMap(this.builder.getFlattenerTool()
			        .flat(fromBean, this.callback));
			this.executed = true;
		}
		this.objectHolderMap.forEach((k, h) -> mapper.map(fromBean, h, toBean));
		this.builder.setTemplateObject(toBean);
		return this.builder;
	}
}
