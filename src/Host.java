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
	
	private String messageWithHeaders(String sourceAddress, int sourcePort, String destAddress, int destPort, String filename) {
		String file = new String(FileManager.loadFile(filename));
		System.out.println(file);
		return sourceAddress + "|" + sourcePort + "|" + destAddress + "|" + destPort + "|" + filename + "|" + file; 
	}
	
	public final void sendTo(String filename, InetAddress address, int port) {
		try {
			DatagramSocket socket = new DatagramSocket();
			String message = messageWithHeaders(localAddress.toString(), getPort(), address.toString(), port, filename);
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
		printMessage("File contents:\n" + brokenMessage[5]);
		FileManager.saveFile(brokenMessage[4], brokenMessage[5].getBytes());
	}
}
