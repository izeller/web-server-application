package izeller.server.web.http;

public enum HttpCode {

	OK(200, "Success"),
	BAD_REQUEST(400, "Bad Request"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not found"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	INTERNAL_ERROR(500, "Internal Error");
	
	public int statusCode;
	public String message;
	
	HttpCode(int statusCode, String message){
		this.statusCode = statusCode;
		this.message = message;
	}
}
