import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class LocalNetwork {

	public static final int RouterPort = 5000;
	
	private InetAddress localAddress;
	private Hashtable<String, Host> hosts;
	private Router router;
	
	public LocalNetwork() {
		this.hosts = new Hashtable<String, Host>();
		
		System.setProperty("java.net.preferIPv6Addresses","true");
	
		try {
			
//			Enumeration<NetworkInterface> i = NetworkInterface.getNetworkInterfaces();
//			while (i.hasMoreElements()) {
//				NetworkInterface n = i.nextElement();
//				System.out.println(n.getDisplayName());
//				Enumeration<InetAddress> a = n.getInetAddresses();
//				while (a.hasMoreElements()) {
//					System.out.println(a.nextElement().toString());
//				}
//			}
//			this.localAddress = InetAddress.getByName("eth4");
//			this.localAddress = InetAddress.getByName(localAddress.toString().split("[/]", 2)[1]);
			
			InetAddress[] addr = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
			localAddress = (Inet6Address) addr[1];
//			for (InetAddress a : addr) {
//				System.out.println(a.toString());
//				if (a instanceof Inet6Address) {
//					localAddress = (Inet6Address) a;
//					break;
//				}
//			}
			this.localAddress = InetAddress.getByName(localAddress.toString().split("[/]", 2)[1]);
			System.out.println("\nLocal Address = " + localAddress);
			
			this.router = new Router(localAddress);
			this.router.startReceive();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void createHost(String id) throws Exception {
		Host h = new Host(id, localAddress);
		h.startReceive();
		hosts.put(id, h);
	}
	
	public boolean hasHost(String id) {
		return hosts.containsKey(id);
	}
	
	public Iterator<Host> getHostIds() {
		return hosts.values().iterator();
	}
	
	public void close() {
		Enumeration<String> e = hosts.keys();
		while(e.hasMoreElements()) {
			String key = e.nextElement();
			hosts.get(key).stopReceive();
		}
		router.stopReceive();
	}
	
	public void sendLocalMessage(String fromId, String toId, String filename) {
		Host fromHost = hosts.get(fromId);
		Host toHost = hosts.get(toId);
		fromHost.sendTo(filename, localAddress, toHost.getPort());
	}

	public void sendExternalMessage(String fromId, String destAddress, String destPort, String message) {
		Host fromHost = hosts.get(fromId);
		try {
			fromHost.sendTo(message, InetAddress.getByName(destAddress), Integer.parseInt(destPort));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
