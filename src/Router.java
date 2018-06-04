import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Router extends HostBase {

	public Router(InetAddress localAddress) throws Exception {
		super("Router", localAddress);
		this.receiveSocket = new DatagramSocket(LocalNetwork.RouterPort);
		printMessage("Listening on port " + getPort());
	}
	
	@Override
	protected void receivePacket(DatagramPacket packet) {
		String[] message = breakPacket(packet);
//		for (String s : message) { printMessage(s); }
		String destination = message[2];
		
		printMessage(destination);
		printMessage(localAddress.toString());
		if (destination.hashCode() == localAddress.toString().hashCode()) {
			printMessage("Received data from inside the network: ");
		} else {
			printMessage("Received data from outside the network: ");
		}
	}
	
}
