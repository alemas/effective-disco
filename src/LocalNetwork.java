import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class LocalNetwork {

	private Hashtable<String, Host> hosts;
//	private Router router;
	
	public LocalNetwork() {
		this.hosts = new Hashtable<String, Host>();
	}
	
	public void createHost(String id) throws Exception {
		Host h = new Host();
		h.startReceive();
	}
	
	public void send(String from, String to) {
		
	}
}
