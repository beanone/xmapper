package org.beanone.xmapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps the related matched objects as a Map.
 *
 * @author Hongyan Li
 *
 */
public class ObjectMatchHolder {
	private final Map<String, ObjectMatch> objectMap = new HashMap<>();

	/**
	 * Constructs a new instance of this.
	 *
	 * @param matchTypeKey
	 *            the key that identifies the type of match.
	 * @param match
	 *            the match this holds on to.
	 */
	public ObjectMatchHolder(String matchTypeKey, ObjectMatch match) {
		put(matchTypeKey, match);
	}

	/**
	 * Fetches the match by match type key.
	 *
	 * @param matchTypeKey
	 *            the key that identifies the type of match.
	 * @return the match.
	 */
	public ObjectMatch get(String matchTypeKey) {
		return this.objectMap.get(matchTypeKey);
	}

	/**
	 * Stores the match and associate it with the passed in matchTypeKey.
	 *
	 * @param matchTypeKey
	 *            the key that identifies the type of match.
	 * @param match
	 *            the match to store.
	 */
	public void put(String matchTypeKey, ObjectMatch match) {
		this.objectMap.put(matchTypeKey, match);
	}
}
