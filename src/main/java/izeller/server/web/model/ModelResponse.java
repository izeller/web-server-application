package izeller.server.web.model;

public class ModelResponse {

	private Model model;
	
	private int statusCode = 200;
	
	public ModelResponse(Model model){
		this.model = model;
		this.statusCode = 200;
	}
	
	public ModelResponse(Model model, int statusCode){
		this.model = model;
		this.statusCode = statusCode;
	}
	
	public Model getModel() {
		return model;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
