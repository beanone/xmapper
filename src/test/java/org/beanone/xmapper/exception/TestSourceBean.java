package org.beanone.xmapper.exception;

public class TestSourceBean {
	private String strVal = "string";
	private int intVal = 10;
	private TestSourceBean from;
	private TestSourceBean another;

	public TestSourceBean getAnother() {
		return another;
	}

	public TestSourceBean getFrom() {
		return from;
	}

	public int getIntVal() {
		return intVal;
	}

	public String getStrVal() {
		return strVal;
	}

	public void setAnother(TestSourceBean another) {
		this.another = another;
	}

	public void setFrom(TestSourceBean from) {
		this.from = from;
	}

	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}
}
