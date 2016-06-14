package org.beanone.xmapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import org.beanone.flattener.FlattenerContants;
import org.beanone.flattener.FlattenerTool;

/**
 * A highly extensible and sophisticated builder for objects to support very
 * flexible object mapping. This can be used together with XMapper to build
 * sophisticated object mapping, where the complex mapping logic is handled by
 * this in building the bean template, and then using XMapper to handle the
 * simpler mapping with configuration, passing in the template built.
 *
 * @author Hongyan Li
 *
 * @param <F>
 *            the type of source bean this support.
 * @param <T>
 *            the type of target bean this support.
 */
public class XObjectBuilder<F, T> {
	private final Map<String, Object> objectCache = new HashMap<>();
	private final FlattenerTool flattenerTool;
	private final F fromBean;
	private final T toBean;
	private final Map<String, String> attributesMap;
	private final XMapperUtils utils;

	/**
	 * Construct a new instance of this.
	 *
	 * @param flattenerTool
	 *            the FlattenerTool to flatten beans.
	 * @param templateObject
	 *            the target bean type template object.
	 * @param bean
	 *            the bean this helps to map from.
	 */
	public XObjectBuilder(FlattenerTool flattenerTool, T templateObject,
	        F bean) {
		if (flattenerTool == null || templateObject == null || bean == null) {
			throw new IllegalArgumentException(
			        "One of the parameters passed into the constructor is null!");
		}
		this.fromBean = bean;
		this.flattenerTool = flattenerTool;
		this.attributesMap = flattenerTool.flat(this.fromBean);
		this.toBean = templateObject;
		this.objectCache.put(FlattenerContants.CTYPE_SUFFIX, bean);
		this.utils = new XMapperUtils(flattenerTool);
	}

	/**
	 * Finds {@link ObjectMatch}s using the passed criteria.
	 *
	 * @param matchTypeKey
	 *            the attribute mapping key per to the match.
	 * @param criteria
	 *            criteria to use to find matches.
	 * @return a new {@link ObjectMatchCollector}.
	 */
	public ObjectMatchCollector<F, T> collect(String matchTypeKey,
	        BiPredicate<String, Object> criteria) {
		final List<ObjectMatch> matches = findAllBy(criteria);
		return new ObjectMatchCollector<>(this, matchTypeKey, matches);
	}

	/**
	 * Finish the object building operations and return the result. Clean up the
	 * cache before return.
	 *
	 * @return the target bean built.
	 */
	public T finishAndGet() {
		this.objectCache.clear();
		return this.toBean;
	}

	/**
	 * Maps the target bean by finding {@link ObjectMatch}s using the passed
	 * criteria and then walks through each match so that it can be mapped.
	 *
	 * @param criteria
	 *            criteria to use to find matches.
	 * @param mapper
	 *            the callback of this as {@link ObjectMapper}.
	 */
	public XObjectBuilder<F, T> mapBy(BiPredicate<String, Object> criteria,
	        ObjectMapper<F, T> mapper) {
		collect("1", criteria).map(mapper);
		return this;
	}

	private Object getObject(String key) {
		final StringBuilder keyBuilder = new StringBuilder();
		if (key.endsWith(FlattenerContants.CTYPE_SUFFIX)) {
			keyBuilder.append(key);
		} else {
			final int indexOfLastDot = key
			        .lastIndexOf(FlattenerContants.ATTRIBUTE_SEPARATE);
			if (indexOfLastDot > 0) {
				keyBuilder.append(key.substring(0, indexOfLastDot));
			}
			keyBuilder.append(FlattenerContants.CTYPE_SUFFIX);
		}

		final String classKey = keyBuilder.toString();
		Object returns = this.objectCache.get(classKey);
		if (returns == null) {
			returns = this.utils.getObject(classKey, this.attributesMap);
			this.objectCache.put(classKey, returns);
		}
		return returns;
	}

	private Object getValue(String v) {
		return v.indexOf(',') < 0 ? v : this.flattenerTool.parsePrimitive(v);
	}

	/**
	 * Finds all matching objects by the passed in criteria.
	 *
	 * @param criteria
	 *            a criteria used to find matches.
	 * @return a list of {@link ObjectMatch} that contains the matched objects.
	 */
	List<ObjectMatch> findAllBy(BiPredicate<String, Object> criteria) {
		final List<ObjectMatch> returns = new ArrayList<>();
		this.attributesMap.forEach((k, v) -> {
			if (criteria.test(k, getValue(v))) {
				returns.add(new ObjectMatch(k, v, getObject(k)));
			}
		});
		return returns;
	}

	F getFromBean() {
		return this.fromBean;
	}

	T getToBean() {
		return this.toBean;
	}
}
