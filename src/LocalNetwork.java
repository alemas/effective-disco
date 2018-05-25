import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

public class LocalNetwork {

	private Hashtable<String, Host> hosts;
//	private Router router;
	
	public LocalNetwork() {
		this.hosts = new Hashtable<String, Host>();
	}
	
	public void createHost(String id) throws Exception {
		Host h = new Host(id);
		h.startReceive();
		hosts.put(id, h);
	}
	
	public boolean hasHost(String id) {
		return hosts.containsKey(id);
	}
	
	public ArrayList<String> getHostIds() {
		return Collections.list(hosts.keys());
	}
	
	public void close() {
		Enumeration<String> e = hosts.keys();
		while(e.hasMoreElements()) {
			String key = e.nextElement();
			hosts.get(key).stopReceive();
		}
	}
	
	public void send(String from, String to) {
		
	}
}
