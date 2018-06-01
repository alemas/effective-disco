import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;

public class Host {

	protected DatagramSocket receiveSocket;
	protected Thread receiveThread;
	protected String id;
	protected final InetAddress localAddress;
	
	public Host(String id, InetAddress localAddress) throws Exception {
		this.localAddress = localAddress;
		this.receiveSocket = new DatagramSocket();
		this.id = id;
		printMessage("Listening on port " + getPort());
	}
	
	public int getPort() {
		return this.receiveSocket.getLocalPort();
	}
	
	public void startReceive() {
		receiveThread = new Thread () {
			public void run() {
				while (true) {
					byte[] receiveData = new byte[1024];
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					try {
						receiveSocket.receive(receivePacket);
						receivePacket(receivePacket);
					} catch (IOException e) {
						printMessage(e.getMessage());
						break;
					}
				}
			}
		};
		
		receiveThread.start();
	}
	
	protected String[] breakPacket(DatagramPacket packet) {
		String receivedMessage = new String(packet.getData());
		return receivedMessage.split("|", 6);
	}
	
	protected void receivePacket(DatagramPacket packet) {
		printMessage("Hey, I've received a message!");
		String receivedMessage = new String(packet.getData());
		printMessage(receivedMessage);
	}

	
	public void stopReceive() {
		if (receiveThread != null) {
			receiveThread.interrupt();
			receiveSocket.close();
		}
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
			if (address == localAddress) {
				sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
			} else {
				sendPacket = new DatagramPacket(sendData, sendData.length, localAddress, LocalNetwork.RouterPort);
			}
			printMessage("Sending message to " + sendPacket.getAddress().toString() + " on port " + port);
			socket.send(sendPacket);
			socket.close();
		} catch (Exception e) {
			printMessage(e.getMessage());
		}
	}

	protected void printMessage(String message) {
		System.out.println("[" + id + "]: " + message);
	}
}
