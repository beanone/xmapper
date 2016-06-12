package org.beanone.xmapper;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.beanone.flattener.api.KeyStack;

/**
 * Encapsulates the logic to calculate the target attribute key from a source
 * attribute key. This maps the target attribute based on the mapping defined in
 * the mapping configuration. The the corresponding mapping configuration cannot
 * be found, it recursively walks up the object graph and assumes that attribute
 * names in the target attribute are the same as that of the source.
 *
 * @author Hongyan Li
 */
public class KeyMapper {
	private final Properties config;

	/**
	 * Constructs this from the source attribute key.
	 *
	 * @param config
	 *            a Properties that has the mapping configuration. Cannot be
	 *            null.
	 * @throws IllegalArgumentException
	 *             if the passed in is null.
	 */
	public KeyMapper(Properties config) {
		if (config == null) {
			throw new IllegalArgumentException("Mapping properties is null!");
		}
		this.config = config;
	}

	/**
	 *
	 * @param fromKey
	 *            the source attribute key.
	 * @return the target attribute key.
	 */
	public String calculateKeyConfig(String fromKey) {
		if (StringUtils.isBlank(fromKey)) {
			throw new IllegalArgumentException("The fromkey is null or empty!");
		}

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
			if (!StringUtils.isBlank(to)) {
				return buildKey(to, right);
			}
		}

		return fromKey;
	}

	private String buildKey(String to, KeyStack tail) {
		final StringBuilder sb = new StringBuilder(to);
		while (!tail.isEmpty()) {
			sb.append('.').append(tail.pop());
		}
		return sb.toString();
	}
}
