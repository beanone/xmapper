package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.api.FlattenerCallback;

public class MatchingStrategy<F, T> {
	public static <F, T> void execute(XMapperBuilder<F, T> builder,
	        Criteria criteria, F fromBean, ObjectMapper<F, T> mapper,
	        T toBean) {
		final MatchingStrategy<F, T> strategy = new MatchingStrategy<>(builder,
		        "1", criteria);
		strategy.execute(fromBean, mapper, toBean);
	}

	private final boolean executed = false;
	private final Map<String, ObjectMatchHolder> objectHolderMap = new HashMap<>();
	private final XMapperBuilder<F, T> builder;
	private FlattenerCallback callback;

	public MatchingStrategy(XMapperBuilder<F, T> builder, String matchTypeKey,
	        Criteria criteria) {
		this.builder = builder;
		this.callback = (k, v, vo, o) -> {
			if (criteria.test(k, vo, o)) {
				this.objectHolderMap.put(k, new ObjectMatchHolder(matchTypeKey,
		                new ObjectMatch(k, v, o)));
			}
		};
	}

	public MatchingStrategy<F, T> and(String matchTypeKey, Criteria criteria,
	        ObjectMatcher<ObjectMatch> matcher) {
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

	public XMapperBuilder<F, T> execute(F fromBean, ObjectMapper<F, T> mapper,
	        T toBean) {
		if (!this.executed) {
			this.builder.setAttributesMap(this.builder.getFlattenerTool()
			        .flat(fromBean, this.callback));
		}
		this.objectHolderMap.forEach((k, h) -> mapper.map(fromBean, h, toBean));
		this.builder.setTemplateObject(toBean);
		return this.builder;
	}
}
