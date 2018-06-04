import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HostBase {
	
	protected DatagramSocket receiveSocket = null;
	protected Thread receiveThread = null;
	protected String id = null;
	protected final InetAddress localAddress;
	
	protected HostBase(String id, InetAddress localAddress) {
		this.id = id;
		this.localAddress = localAddress;
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
	
	public void stopReceive() {
		if (receiveThread != null) {
			receiveThread.interrupt();
			receiveSocket.close();
		}
	}
	
	protected void receivePacket(DatagramPacket packet) {
		printMessage("Hey, I've received a message!");
		String receivedMessage = new String(packet.getData());
		printMessage(receivedMessage);
	}
	
	protected String[] breakPacket(DatagramPacket packet) {
		String receivedMessage = new String(packet.getData());
		return receivedMessage.split("[|]", 6);
	}
	
	protected final void printMessageHeader(String[] message) {
		printMessage("Header:"
				+ "\nFrom: " + message[0] + "|" + message[1]
				+ "\nTo: " + message[2] + "|" + message[3]
				+ "\nFile name: " + message[4]);
	}
	
	protected void printMessage(String message) {
		System.out.println("[" + id + "]: " + message);
	}
}
