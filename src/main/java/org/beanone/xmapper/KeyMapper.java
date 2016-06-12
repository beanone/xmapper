package org.beanone.xmapper;

import java.util.Properties;

import org.beanone.flattener.api.KeyStack;

/**
 * Encapsulates the logic to calculate the target attribute key from a source
 * attribute key.
 *
 * @author Hongyan Li
 */
public class KeyMapper {
	private final Properties config;

	/**
	 * Constructs this from the source attribute key.
	 *
	 * @param config
	 *            a Properties that has the mapping configuration.
	 */
	public KeyMapper(Properties config) {
		this.config = config;
	}

	/**
	 *
	 * @param fromKey
	 *            the source attribute key.
	 * @return the target attribute key.
	 */
	public String calculateKeyConfig(String fromKey) {
		String to = config.getProperty(fromKey);
		if (to != null) {
			return to;
		}

		final String[] keys = fromKey.split("\\.");
		final KeyStack left = KeyStack.create(keys);
		final KeyStack right = new KeyStack();
		String head = fromKey;
		while (!left.isEmpty()) {
			final String key = left.pop();
			right.push(key);
			int endIndex = head.lastIndexOf("." + key);
			if (endIndex < 0) {
				endIndex = head.length();
			}
			head = head.substring(0, endIndex);
			to = config.getProperty(head);
			if (to != null) {
				return buildKey(to, right);
			}
		}

		return fromKey;
	}

	private String buildKey(String to, KeyStack tail) {
		final StringBuilder sb = new StringBuilder(to);
		while (!tail.isEmpty()) {
			if (sb.length() > 0) {
				sb.append('.');
			}
			sb.append(tail.pop());
		}
		return sb.toString();
	}
}
