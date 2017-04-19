package izeller.server.web.handler;

import izeller.server.web.ControllerHandler.ModelHandler;
import izeller.server.web.http.HttpRequest;
import izeller.server.web.model.Model;
import izeller.server.web.model.ModelResponse;

public class LoginRedirectHandler implements ModelHandler {

	@Override
	public ModelResponse execute(HttpRequest request) {
		return new ModelResponse(new Model("from", request.getRedirect()));
	}

}
