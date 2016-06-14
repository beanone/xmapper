package org.beanone.xmapper;

import java.util.Map;

/**
 * Callback interface that is used to find matching {@link ObjectMatchHolder} so
 * that multiple found object can be paired with each other.
 *
 * @author Hongyan Li
 *
 * @param <T>
 */
@FunctionalInterface
public interface ObjectMatcher<T> {
	/**
	 * Find from the passed in {@link ObjectMatchHolder} map one that matches
	 * the passed in t.
	 *
	 * @param objectHolderMap
	 *            a map of {@link ObjectMatchHolder}.
	 * @param t
	 *            the object to match against.
	 * @return the matched {@link ObjectMatchHolder}.
	 */
	ObjectMatchHolder match(Map<String, ObjectMatchHolder> objectHolderMap,
	        T t);
}
