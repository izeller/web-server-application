package izeller.server.web.view;

import izeller.server.web.model.Model;

public interface View {

	public String render(Model model);
	
	public String getContentType();
	
	
}
