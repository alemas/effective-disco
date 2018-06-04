import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

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
		String origin = message[0];
		String destination = message[2];
		int destPort = Integer.parseInt(message[3]);
		
//		printMessage(destination);
//		printMessage(localAddress.toString());
		if (origin.hashCode() == localAddress.toString().hashCode()) {
			printMessage("Received data from inside the network");
			destPort = LocalNetwork.RouterPort;
		} else {
			printMessage("Received data from outside the network");
		}
		printMessage("Redirecting received packet...");
		printMessageHeader(message);
		try {
			sendTo(new String(packet.getData()), InetAddress.getByName(destination.replace("/", "")), destPort);
		} catch (Exception e) {
			printMessage(e.toString());
		}
	}
	
	private final void sendTo(String data, InetAddress address, int port) {
		try {
			DatagramSocket socket = new DatagramSocket();
			byte[] sendData = new byte[1024];
			sendData = data.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
			printMessage("Sending data to " + sendPacket.getAddress().toString() + "|" + port);
			socket.send(sendPacket);
			socket.close();
		} catch (Exception e) {
			printMessage(e.getMessage());
		}
	}
	
}
