package org.beanone.xmapper.exception;

public class TestSourceBean {
	private String strVal = "string";
	private String strAnotherVal;
	private int intVal = 10;

	private TestSourceBean from;

	private TestSourceBean another;

	public TestSourceBean getAnother() {
		return this.another;
	}

	public TestSourceBean getFrom() {
		return this.from;
	}

	public int getIntVal() {
		return this.intVal;
	}

	public String getStrAnotherVal() {
		return this.strAnotherVal;
	}

	public String getStrVal() {
		return this.strVal;
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

	public void setStrAnotherVal(String strAnotherVal) {
		this.strAnotherVal = strAnotherVal;
	}

	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}
}
