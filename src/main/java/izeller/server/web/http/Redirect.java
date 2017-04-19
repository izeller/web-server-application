package izeller.server.web.http;

public class Redirect {


	private String url;
	private String from;

	public Redirect(String url){
		this.url = url;
	}

	public Redirect setFrom(String from){
		this.from = from;
		return this;
	}
	public String getUrl(){
		return url;
	}
	public String getFrom(){
		return from;
	}


}
