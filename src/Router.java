import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Router extends Host {

	public Router(InetAddress localAddress) throws Exception {
		super("Router", localAddress);
		super.receiveSocket = new DatagramSocket(LocalNetwork.RouterPort);
	}
	
	@Override
	protected void receivePacket(DatagramPacket packet) {
		String[] message = breakPacket(packet);
		String destination = message[2];
		if (destination == localAddress.toString()) {
			printMessage("Redirecting data to host on port " + message[1]);
		} else {
			printMessage("Sending data to " + destination + " on port " + message[3]);
		}
	}
	
}
