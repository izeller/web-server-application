package izeller.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import izeller.server.web.InvokerHandler;
import izeller.server.web.http.HttpCode;
import izeller.server.web.http.HttpRequest;
import izeller.server.web.http.HttpResponse;

public class DispatcherHandler implements HttpHandler {

	final static Logger logger = Logger.getLogger(DispatcherHandler.class);
	
	private InvokerHandler invokerHandler;

	public DispatcherHandler(InvokerHandler invokerHandler) {
		this.invokerHandler = invokerHandler;
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		HttpRequest httpRequest = null;
		HttpResponse httpResponse = null;

		try{

			httpRequest = new HttpRequest(httpExchange);
			httpResponse = new HttpResponse(httpExchange);
			invokerHandler.invoke(httpRequest, httpResponse);

		}catch(Exception exception){
			logger.error(exception.getMessage(), exception);
			if(httpResponse!=null){
				httpResponse.writeStatusCodeResponse(HttpCode.INTERNAL_ERROR);
			}

		}

	}




}
