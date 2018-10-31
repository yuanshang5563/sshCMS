package org.ys.exception;

/**
 * 业务异常类
 * @author Administrator
 *
 */
public class BusiException extends Exception {
	private static final long serialVersionUID = 6580868540942285055L;
	private String exceptionInfo;
	private String exceptionCode;
	public String getExceptionInfo() {
		return exceptionInfo;
	}
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public BusiException(String exceptionInfo, String exceptionCode) {
		super();
		this.exceptionInfo = exceptionInfo;
		this.exceptionCode = exceptionCode;
	}
}
