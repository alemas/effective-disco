import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.SocketException;

public class Host {

	private DatagramSocket receiveSocket;
	private Thread receiveThread;
	private String id = "";
	private boolean shouldReceive;
	
	public Host(String id) throws Exception {
		this.receiveSocket = new DatagramSocket();
		this.id = id;
	}
	
	public int getPort() {
		return this.receiveSocket.getPort();
	}
	
	public void startReceive() {
		shouldReceive = true;
		receiveThread = new Thread () {
			public void run() {
				while (true) {
					byte[] receiveData = new byte[1024];
					try {
						receiveSocket.receive(new DatagramPacket(receiveData, receiveData.length));
					} catch (IOException e) {
						printMessage(e.getMessage());
						break;
					}
					printMessage("[" + id +"]: Oi, recebi algo");
				}
			}
		};
		
		receiveThread.start();
	}
	
	public void stopReceive() {
		if (receiveThread != null) {
			receiveThread.interrupt();
			receiveSocket.close();
			shouldReceive = false;
		}
	
	}
	
	public void sendTo(String message, Inet6Address address, int port) {
		new Thread () {
			public void run() {
				byte[] sendData = new byte[1024];
				sendData = message.getBytes();
				DatagramSocket socket;
				
				try {
					socket = new DatagramSocket();
					socket.send(new DatagramPacket(sendData, sendData.length, address, port));
					socket.close();
				} catch (Exception e) {
					printMessage(e.getMessage());
				}
			}
		}.start();
	}

	private void printMessage(String message) {
		System.out.println("[" + id + "]: " + message);
	}
}














