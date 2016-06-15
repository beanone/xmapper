package org.beanone.xmapper;

/**
 * Criteria used to help find attributes of matching criteria.
 *
 * <p>
 * This is a functional interface whose functional method is
 * {@link #test(String, Object, Object)}.
 */
@FunctionalInterface
public interface Criteria {

	/**
	 * Evaluates this on the given arguments.
	 *
	 * @param k
	 *            the attribute key String.
	 * @param v
	 *            the attribute value Object.
	 * @param o
	 *            the owning object of the attributes.
	 * @return {@code true} if the input arguments match the predicate,
	 *         otherwise {@code false}
	 */
	boolean test(String k, Object v, Object o);
}
