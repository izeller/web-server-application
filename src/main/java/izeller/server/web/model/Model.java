package izeller.server.web.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Model {
	
	private Map<String,String> values = new HashMap<>();
	private Object data;
	
	public Model(Object data) {
		this.data = data;
	}
	
	public Model(String key, String value){
		values.put(key, value);
	}

	public Set<String> keySet() {
		
		return values.keySet();
	}

	public String get(String key) {
		return values.get(key);
	}

	public void addData(Object data){
		this.data = data;
	}
	public Object getData(){
		return data;
	}
	
	public void add(String key, String value) {
		values.put(key, value);
		
	}
	
	

}
