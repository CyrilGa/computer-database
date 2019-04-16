package fr.cgaiton611.cdb.error;

public class ErrorModel {

	private String httpCode;
	
	private String message;

	/**
	 * @return the httpCode
	 */
	public String getHttpCode() {
		return httpCode;
	}

	/**
	 * @param httpCode the httpCode to set
	 */
	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static class ErrorModelBuilder {
		
		private String httpCode;
		private String message;
		
		public ErrorModelBuilder setHttpCode(String httpCode) {
			this.httpCode = httpCode;
			return this;
		}
		
		public ErrorModelBuilder setMessage(String message) {
			this.message = message;
			return this;
		}
		
		public ErrorModel build() {
			ErrorModel errorModel = new ErrorModel();
			errorModel.setHttpCode(this.httpCode);
			errorModel.setMessage(this.message);
			return errorModel;
		}
	}
}
