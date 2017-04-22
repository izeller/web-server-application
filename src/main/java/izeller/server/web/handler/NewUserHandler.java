package izeller.server.web.handler;

import java.util.Optional;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import izeller.server.security.UserRepository;
import izeller.server.security.model.NewUser;
import izeller.server.security.model.User;
import izeller.server.security.model.ViewUser;
import izeller.server.web.ControllerHandler.ModelHandler;
import izeller.server.web.http.HttpCode;
import izeller.server.web.http.HttpException;
import izeller.server.web.http.HttpRequest;
import izeller.server.web.model.Model;
import izeller.server.web.model.ModelResponse;

public class NewUserHandler implements ModelHandler {

	final static Logger logger = Logger.getLogger(NewUserHandler.class);
	
	private UserRepository userRepository;

	private Gson gson = new GsonBuilder()
			.create();

	public NewUserHandler(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	@Override
	public ModelResponse execute(HttpRequest request) {
	
		Optional<NewUser> newUserOptional = getNewUser(request.getBody());
		NewUser newUser = newUserOptional.orElseThrow(() -> new HttpException(HttpCode.BAD_REQUEST));
		if(!newUser.isValid()){
			throw new HttpException(HttpCode.BAD_REQUEST);
		}
		User user = new User(newUser);
		int statusCode = 201;
		if(userRepository.contains(user)){
			statusCode = 200;
		}
		userRepository.save(user);
		return new ModelResponse(new Model(new ViewUser(user)), statusCode);
	}
	
	
	private Optional<NewUser> getNewUser(String body){
		if(body == null){
			return Optional.empty();
		}
		try{
			return Optional.of(gson.fromJson(body, NewUser.class));
		}catch(JsonSyntaxException e){
			logger.info(e.getMessage(), e);
			return Optional.empty();
		}
	}
}
