package izeller.server.web.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import izeller.server.security.model.Cookie;
import izeller.server.security.model.Session;
import izeller.server.web.model.Model;
import izeller.server.web.model.ModelResponse;
import izeller.server.web.view.HtmlView;
import izeller.server.web.view.View;

public class HttpResponse {

	private HttpExchange httpExchange;

	public HttpResponse(HttpExchange httpExchange) {
		this.httpExchange = httpExchange;
	}

	public void addCookie(Cookie cookie){
		if(cookie!=null){
			httpExchange.getResponseHeaders().add("Set-Cookie", Session.SESSION_ID+"="+cookie.getValue());
		}
	}

	public void sendRedirect(Redirect redirect) {

		try{
			httpExchange.setAttribute("from", redirect.getFrom());
			httpExchange.getResponseHeaders().add("Location", redirect.getUrl());
			httpExchange.sendResponseHeaders(301, -1);
			httpExchange.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}

	}

	public void writeResponse(String content){
		writeResponse(content, 200);
	}

	public void writeResponse(String content, int statusCode) {
		try{
			byte[] response = content.getBytes();

			httpExchange.sendResponseHeaders(statusCode,
					response.length);
			httpExchange.getResponseBody().write(response);
			httpExchange.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public void sendReponse(ModelResponse response, View view){

		addResponseHeader("Content-Type", view.getContentType());
		writeResponse(view.render(response.getModel()), response.getStatusCode());

	}

	public void retryBasicAuth() {
		String realm = "test_server";
		addResponseHeader("WWW-Authenticate", "Basic realm=" + "\""+realm+"\"");
		writeResponse("", 401);
	}

	private void addResponseHeader(String key, String value){
		Headers map = httpExchange.getResponseHeaders();
		map.set (key, value);
	}

	public void writeStatusCodeResponse(HttpCode httpCode) {
		writeResponse(new HtmlView("statusCode")
				.render(new Model("message", httpCode.message)), 
				httpCode.statusCode);

	}

	public void retrySessionAuth(String from) {
		sendRedirect(new Redirect("/login").setFrom(from));
		
	}

}
