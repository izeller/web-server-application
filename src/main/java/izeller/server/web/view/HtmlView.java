package izeller.server.web.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import izeller.server.App;
import izeller.server.web.model.Model;

public class HtmlView implements View{

	private  final String resource;

	public HtmlView(String resources){
		this.resource = resources+".html"; 
	}

	public String render(Model model){
		final InputStreamReader inputStreamReader = new InputStreamReader(
				App.class.getClassLoader().getResourceAsStream(resource));

		final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		final StringBuilder html = new StringBuilder();
		try {
			while (bufferedReader.ready()) {
				html.append(bufferedReader.readLine());
			}
			bufferedReader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		String htmlString = html.toString();
		if(model!=null && model.keySet()!=null)
		for (String key : model.keySet()) { 
			String temp = ""; 
			if (null != model.get(key)) { 
				temp = model.get(key); 
			} 
			htmlString = htmlString.replaceAll("\\$\\{" + key + "\\}", temp); 
		} 
		
		return htmlString;
	}

	@Override
	public String getContentType() {
		return "text/html";
	}

}
