import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App {
	
	protected static LocalNetwork network = new LocalNetwork();
	protected static Scanner scanner = new Scanner(System.in);;

	public static void main(String[] args) throws Exception {
		
		System.out.println();
		
		showHelp();
		boolean shouldRun = true;
		
		while (shouldRun) {
			
			System.out.println("\nType a valid option (h for help):\n");
			String choice = scanner.nextLine();
					
			switch(choice) {
			case "0" :
				System.out.println("Inform the host id to be created:");
				String id = scanner.nextLine();
				while (network.hasHost(id)) {
					System.out.println("Please inform an unused id:");
					id = scanner.nextLine();
				}
				network.createHost(id);
				System.out.println("Host created succesfully!");
				
				break;
				
			case "1" :
				Iterator<Host> hosts = network.getHostIds();
				if (!hosts.hasNext()) { System.out.println("There are no hosts :,("); }
				while(hosts.hasNext()) { 
					Host h = hosts.next();
					System.out.println(h.id + " [" + h.getPort() + "]"); 
				}
				
				break;
				
			case "2" :
				if (!network.getHostIds().hasNext()) {
					System.out.println("There are no hosts :,(");
					break;
				}
				System.out.println("Inform the sending host id:");
				String fromId = getExistingId();
				System.out.println("Inform the receiving host id:");
				String toId = getExistingId();
				System.out.println("Inform the name of the file to be sent:");
				String message = scanner.nextLine();
				System.out.println();
				network.sendLocalMessage(fromId, toId, message);
				
				// pro menu não atropelar mensagens no menu
				TimeUnit.SECONDS.sleep(1);
				
				break;
				
			case "3" :
				if (!network.getHostIds().hasNext()) {
					System.out.println("There are no hosts :,(");
					break;
				}
				System.out.println("Inform the sending host id:");
				String fromId1 = getExistingId();
				System.out.println("Inform the destination IP:");
				String destAddress = scanner.nextLine();
				System.out.println("Inform the destination port:");
				String destPort = scanner.nextLine();
				System.out.println("Inform the name of the file to be sent:");
				String message1 = scanner.nextLine();
				network.sendExternalMessage(fromId1, destAddress, destPort, message1);
				
				// pro menu não atropelar mensagens no menu
				TimeUnit.SECONDS.sleep(1);
				
				break;
				
			case "h" :
				showHelp();
				
				break;
				
			case "q" :
				shouldRun = false;
				network.close();
				
				break;
			
			default :
				System.out.println("Invalid option");
			}
		}
	}
	
	private static void showHelp() {
		System.out.println("Available options:");
		System.out.println("0 - Create new Host");
		System.out.println("1 - List all Hosts");
		System.out.println("2 - Send message locally");
		System.out.println("3 - Send message externally");
		System.out.println("h - Show Help");
		System.out.println("q - Quit");
	}

	private static String getExistingId() {
		String id = scanner.nextLine();
		while (!network.hasHost(id)) {
			System.out.println("Inform a valid id:");
			id = scanner.nextLine();
		}
		return id;
	}

}











