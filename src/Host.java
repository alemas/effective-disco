import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;

public class Host {

	private DatagramSocket receiveSocket;
	private Thread receiveThread;
	private String id;
	
	public Host(String id) throws Exception {
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
						printMessage("Hey, I've received a message!");
						String receivedMessage = new String(receivePacket.getData());
						String[] msgArray = receivedMessage.split(" ", 2);
//						printMessage(receivedMessage);
						printMessage("\nFrom: " + msgArray[0] + "\nMessage: " + msgArray[1]);
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
	
	public void sendTo(String message, InetAddress address, int port) {
//		new Thread () {
//			public void run() {
				message = id + " " + message;
				byte[] sendData = new byte[1024];
				sendData = message.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
				DatagramSocket socket;
				
				try {
					socket = new DatagramSocket();
					printMessage("Sending message to " + sendPacket.getAddress().toString() + " on port " + port);
					socket.send(sendPacket);
					socket.close();
				} catch (Exception e) {
//					printMessage(e.getMessage());
				}
//			}
//		}.start();
	}

	private void printMessage(String message) {
		System.out.println("[" + id + "]: " + message);
	}
}














