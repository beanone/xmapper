package org.beanone.xmapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestUtil {
	public static Properties loadMappingProperties(String mappingFileName)
	        throws IOException {
		final InputStream is = TestUtil.class.getClassLoader()
		        .getResourceAsStream(mappingFileName);
		final Properties props = new Properties();
		props.load(is);
		return props;
	}

}
