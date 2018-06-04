import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;

public class Host extends HostBase {
	
	public Host(String id, InetAddress localAddress) throws Exception {
		super(id, localAddress);
		this.receiveSocket = new DatagramSocket();
		printMessage("Listening on port " + getPort());
	}
	
	private String messageWithHeaders(String sourceAddress, int sourcePort, String destAddress, int destPort, String data) {
		return sourceAddress + "|" + sourcePort + "|" + destAddress + "|" + destPort + "|" + data; 
	}
	
	public final void sendTo(String data, InetAddress address, int port) {
		try {
			DatagramSocket socket = new DatagramSocket();
			String message = messageWithHeaders(localAddress.toString(), getPort(), address.toString(), port, data);
			byte[] sendData = new byte[1024];
			sendData = message.getBytes();
			DatagramPacket sendPacket;
			if (address.toString().hashCode() == localAddress.toString().hashCode()) {
				sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
			} else {
				sendPacket = new DatagramPacket(sendData, sendData.length, localAddress, LocalNetwork.RouterPort);
			}
			printMessage("Sending data to " + address.toString() + "|" + port);
			socket.send(sendPacket);
			socket.close();
		} catch (Exception e) {
			printMessage(e.getMessage());
		}
	}
	
	@Override
	protected void receivePacket(DatagramPacket packet) {
		String[] brokenMessage = breakPacket(packet);
		printMessage("Hey, I've received data!");
		printMessageHeader(brokenMessage);
	}
}
