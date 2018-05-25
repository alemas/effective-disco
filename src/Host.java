import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.SocketException;

public class Host {

	private DatagramSocket receiveSocket;
	private boolean shouldReceive = true;
	private String id = "";
	
	public Host() throws Exception {
		this.receiveSocket = new DatagramSocket();
	}
	
	public int getPort() {
		return this.receiveSocket.getPort();
	}
	
	public void startReceive() {
		new Thread () {
			
			public void run() {
				while (shouldReceive) {
					byte[] receiveData = new byte[1024];
					try {
						receiveSocket.receive(new DatagramPacket(receiveData, receiveData.length));
					} catch (IOException e) {
						
					}
					System.out.println("Oi, recebi algo (" + id +")");
				}
			}
		}.start();
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
					
				}
			}
		}.start();
	}

}














