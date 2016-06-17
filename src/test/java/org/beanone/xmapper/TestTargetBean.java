package org.beanone.xmapper;

public class TestTargetBean {
	private String strVal;
	private int intValue;
	private TestTargetBean to;
	private String anotherSerialized;

	public String getAnotherSerialized() {
		return anotherSerialized;
	}

	public int getIntValue() {
		return intValue;
	}

	public String getStrVal() {
		return strVal;
	}

	public TestTargetBean getTo() {
		return to;
	}

	public void setAnotherSerialized(String anotherSerialized) {
		this.anotherSerialized = anotherSerialized;
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
