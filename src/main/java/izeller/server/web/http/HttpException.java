package izeller.server.web.http;

public class HttpException extends RuntimeException {

	private static final long serialVersionUID = -5190416722020310354L;
	private HttpCode httCode;

	public HttpException(HttpCode httCode){
		this.httCode = httCode;
	}
	
	public HttpCode getHttpCode(){
		return httCode;
	}
}
