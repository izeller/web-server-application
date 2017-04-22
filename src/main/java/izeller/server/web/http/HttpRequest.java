package izeller.server.web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpExchange;

import izeller.server.security.model.Credentials;
import izeller.server.security.model.Session;
import izeller.server.security.model.User;
import izeller.server.web.route.Route.RequestMethod;

public class HttpRequest {

	private HttpExchange exchange;
	private Map<String,List<String>> parametersGet = new HashMap<>();
	private Map<String,List<String>> parametersPost = new HashMap<>();
	private User user;
	private List<String> pathParams;
	private String body;

	public HttpRequest(){

	}

	public HttpRequest(HttpExchange exchange){

		this.exchange = exchange;
		try{
			parametersGet = parseGetParameters();
			parametersPost = parsePostParameters();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public String getBody(){
		return body;
	}

	public String getPath(){
		return exchange.getRequestURI().getPath().toString();
	}

	public String getQueryId(String key){
		return getFirstValue(parametersGet, key);
	}

	public String getPostId(String key){
		return getFirstValue(parametersPost, key);
	}

	private String getFirstValue(Map<String,List<String>> parameters, String key){
		if(parameters.containsKey(key)){
			return parameters.get(key).get(0);
		}
		return null;
	}

	public String getRedirect(){
		return (String) exchange.getAttribute("from");
	}

	public String getSessionId(){

		String cookies = exchange.getRequestHeaders().getFirst("Cookie"); 

		if (isEmpty(cookies)) { 
			return null; 
		} 

		String sessionId = ""; 
	
		String[] cookiearry = cookies.split(";"); 
		for(String cookie : cookiearry){ 
			cookie = cookie.replaceAll(" ", ""); 
			if (cookie.startsWith(Session.SESSION_ID+"=")) { 
				sessionId = cookie.replace(Session.SESSION_ID+"=", "").replace(";", ""); 
			} 
		} 
		return sessionId; 
	}

	private Map<String, List<String>> parseGetParameters() throws UnsupportedEncodingException{

		return parseQuery(exchange.getRequestURI().getRawQuery());

	}

	public RequestMethod getRequestMethod(){
		return RequestMethod.valueOf(exchange.getRequestMethod().toUpperCase());
	}

	private Map<String, List<String>> parsePostParameters() throws IOException {

		if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
			String query = null;
			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
				query = buffer.lines().collect(Collectors.joining(""));
			}

			return parseQuery(query);

		}else{
			return new HashMap<>();
		}
	}

	private Map<String,List<String>> parseQuery(String query) throws UnsupportedEncodingException{

		Map<String,List<String>> parameters = new HashMap<>();
		if (query != null) {
			body = query;
			String pairs[] = query.split("[&]");

			for (String pair : pairs) {
				String param[] = pair.split("[=]");

				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0],
							"UTF-8");
				}

				if (param.length > 1) {
					value = URLDecoder.decode(param[1],
							"UTF-8");
				}

				if (parameters.containsKey(key)) {
					List<String> params = parameters.get(key);
					params.add(value);

				} else {
					List<String> params = new ArrayList<>();
					params.add(value);

					parameters.put(key, params);
				}
			}
		}

		return parameters;
	}

	public static boolean isEmpty(String str) { 
		return str == null || str.length() == 0; 
	}

	public void setPrincipalUser(User user) {
		this.user = user;
	} 
	public User getUser(){
		return user;
	}

	public void addPathParams(List<String> pathParams) {
		this.pathParams = pathParams;
	}

	public List<String> getPathParams(){
		return pathParams;
	}

	public String getFirstPathParam() {
		return pathParams.get(0);
	}

	public Optional<Credentials> getBasicAuthCredentials() {

		String auth = exchange.getRequestHeaders().getFirst("Authorization");
		if(auth==null){
			return Optional.empty();
		}
		int sp = auth.indexOf (' ');
		if (sp == -1 || !auth.substring(0, sp).equals ("Basic")) {
			return Optional.empty();
		}

		byte[] b = Base64.getDecoder().decode(auth.substring(sp+1));
		String userpass = new String (b);
		int colon = userpass.indexOf (':');
		String name = userpass.substring (0, colon);
		String pass = userpass.substring (colon+1);
		return Optional.of(new Credentials(name, pass));
	}

}
