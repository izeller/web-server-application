package izeller.server.web.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import izeller.server.web.model.Model;

public class JsonView implements View{
	
	private Gson gson = new GsonBuilder()
            .create();
	
	@Override
	public String render(Model model) {
		return gson.toJson(model.getData());
	}

	@Override
	public String getContentType() {
		return "application/json";
	}

	
}
