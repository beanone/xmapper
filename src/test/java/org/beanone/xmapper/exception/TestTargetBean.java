package org.beanone.xmapper.exception;

public class TestTargetBean {
	private String strVal;
	private int intValue;
	private TestTargetBean to;

	public int getIntValue() {
		return intValue;
	}

	public String getStrVal() {
		return strVal;
	}

	public TestTargetBean getTo() {
		return to;
	}

	public void setIntValue(int intVal) {
		intValue = intVal;
	}

	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}

	public void setTo(TestTargetBean to) {
		this.to = to;
	}
}
