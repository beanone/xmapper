package org.beanone.xmapper;

/**
 * Encapsulated object mapping logic.
 *
 * @author Hongyan Li
 *
 * @param <F>
 *            the type of bean mapped from.
 * @param <T>
 *            the type of bean mapped to.
 */
@FunctionalInterface
public interface ObjectMapper<F, T> {
	/**
	 * Maps the passed in to the target bean.
	 *
	 * @param fromBean
	 *            the source bean instance.
	 * @param matchedObjectHolder
	 *            an {@link ObjectMatchHolder} that contains the child attribute
	 *            of the source bean instance that this method is focused on to
	 *            map.
	 * @param toBean
	 *            the target bean instance.
	 */
	void map(F fromBean, ObjectMatchHolder matchedObjectHolder, T toBean);
}
