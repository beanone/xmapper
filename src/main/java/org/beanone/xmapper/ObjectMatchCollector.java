package org.beanone.xmapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * Collects ObjectMatches to support mapping of related objects.
 *
 * @author Hongyan Li
 *
 * @param <F>
 *            the type of bean mapped from.
 * @param <T>
 *            the type of bean mapped to.
 */
public class ObjectMatchCollector<F, T> {
	private final Map<String, ObjectMatchHolder> objectHolderMap = new HashMap<>();
	private final XObjectBuilder<F, T> builder;

	/**
	 * Constructs a new instance of this.
	 *
	 * @param builder
	 *            the {@link XObjectBuilder} this is created for.
	 *
	 * @param matchTypeKey
	 *            the key that identifies the type of match.
	 * @param matches
	 *            the matches found.
	 */
	public ObjectMatchCollector(XObjectBuilder<F, T> builder,
	        String matchTypeKey, List<ObjectMatch> matches) {
		this.builder = builder;
		matches.forEach(match -> this.objectHolderMap.put(match.getMappingKey(),
		        new ObjectMatchHolder(matchTypeKey, match)));
	}

	/**
	 * Matches the object matched by the passed in criteria with the already
	 * collected. Use the passed in matcher to pair the found with existing ones
	 * in this.
	 *
	 * @param matchTypeKey
	 *            the key that identifies the type of match.
	 * @param criteria
	 *            criteria to use to find matches.
	 * @param matcher
	 *            the {@link ObjectMatcher} used to pair the found matches.
	 * @return this so that other calls to this can be chained.
	 */
	public ObjectMatchCollector<F, T> andEach(String matchTypeKey,
	        BiPredicate<String, Object> criteria,
	        ObjectMatcher<ObjectMatch> matcher) {
		this.builder.findAllBy(criteria).forEach(match -> {
			final ObjectMatchHolder h = matcher.match(this.objectHolderMap,
		            match);
			if (h != null) {
				h.put(matchTypeKey, match);
			}
		});
		return this;
	}

	public XObjectBuilder<F, T> done() {
		endSession();
		return this.builder;
	}

	/**
	 * Ends the current session for object matching.
	 *
	 * @return this so that other calls to this can be chained.
	 */
	public ObjectMatchCollector<F, T> endSession() {
		this.objectHolderMap.clear();
		return this;
	}

	/**
	 * Finds {@link ObjectMatch}s using the passed criteria and then walks
	 * through each match so that it can be mapped.
	 *
	 * @param criteria
	 *            criteria to use to find matches.
	 * @param mapper
	 *            the callback of this as {@link ObjectMapper}.
	 * @return this so that other calls to this can be chained.
	 */
	public ObjectMatchCollector<F, T> map(BiPredicate<String, Object> criteria,
	        ObjectMapper<F, T> mapper) {
		newSession("1", criteria).map(mapper);
		return this;
	}

	/**
	 * Iterate through each {@link ObjectMatchHolder} in this and perform
	 * mapping with them.
	 *
	 * @param mapper
	 *            the callback of this as {@link ObjectMapper}.
	 * @return this so that other calls to this can be chained.
	 */
	public ObjectMatchCollector<F, T> map(ObjectMapper<F, T> mapper) {
		this.objectHolderMap.forEach((k, h) -> mapper
		        .map(this.builder.getFromBean(), h, this.builder.getToBean()));
		return this;
	}

	/**
	 * Starts a new session for object matching.
	 *
	 * @param matchTypeKey
	 *            the key that identifies the type of match.
	 * @param criteria
	 *            criteria to use to find matches.
	 * @return this so that other calls to this can be chained.
	 */
	public ObjectMatchCollector<F, T> newSession(final String matchTypeKey,
	        BiPredicate<String, Object> criteria) {
		endSession();
		this.builder.findAllBy(criteria).forEach(match -> this.objectHolderMap
		        .put(matchTypeKey, new ObjectMatchHolder(matchTypeKey, match)));
		return this;
	}
}
