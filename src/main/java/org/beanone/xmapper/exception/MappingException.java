package org.beanone.xmapper.exception;

public class MappingException extends RuntimeException {
	private static final long serialVersionUID = -8714382742411293689L;

	public MappingException(ReflectiveOperationException e) {
		super(e);
	}

}
