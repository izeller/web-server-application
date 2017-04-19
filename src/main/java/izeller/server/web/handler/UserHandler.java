package izeller.server.web.handler;

import izeller.server.security.UserRepository;
import izeller.server.web.ControllerHandler.ModelHandler;
import izeller.server.web.http.HttpRequest;
import izeller.server.web.model.Model;
import izeller.server.web.model.ModelResponse;

public class UserHandler implements ModelHandler {

	private UserRepository userRepository;

	public UserHandler(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@Override
	public ModelResponse execute(HttpRequest request) {
		return new ModelResponse(new Model(userRepository.getUsers()));
	}

}
