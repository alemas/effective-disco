import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

public class LocalNetwork {

	private Hashtable<String, Host> hosts;
	private InetAddress localAddress;
//	private Router router;
	
	public LocalNetwork() {
		this.hosts = new Hashtable<String, Host>();
		
		//TODO: esse InetAddress.getLocalHost retorna ipv4, precisamos do ipv6
		try {
			this.localAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}
//		try {
//			InetAddress[] addr = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
//			for (InetAddress a : addr) {
//				System.out.println(a.toString());
//				if (a instanceof Inet6Address) {
//					localIpv6Address = (Inet6Address) a;
//					break;
//				}
//			}
//			
//		} catch (UnknownHostException e) {
//			System.out.println(e.getMessage());
//		}
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
	
	public void sendLocalMessage(String fromId, String toId, String message) {
		Host fromHost = hosts.get(fromId);
		Host toHost = hosts.get(toId);
		fromHost.sendTo(message, localAddress, toHost.getPort());
	}
}
