package izeller.server.web.handler;

import izeller.server.security.UserRepository;
import izeller.server.security.model.User;
import izeller.server.security.model.ViewUser;
import izeller.server.web.ControllerHandler.ModelHandler;
import izeller.server.web.http.HttpCode;
import izeller.server.web.http.HttpException;
import izeller.server.web.http.HttpRequest;
import izeller.server.web.model.Model;
import izeller.server.web.model.ModelResponse;

public class UserPerIdHandler implements ModelHandler {

	private UserRepository userRepository;

	public UserPerIdHandler(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@Override
	public ModelResponse execute(HttpRequest request) {
		String userId = request.getFirstPathParam();
		if(!userRepository.contains(userId)){
			throw new HttpException(HttpCode.NOT_FOUND);
		}
		User user = userRepository.findBy(userId);
		return new ModelResponse(new Model(new ViewUser(user)));
	}

}
