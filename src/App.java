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
				ArrayList<String> hosts = network.getHostIds();
				Iterator<String> i = hosts.iterator();
				if (!i.hasNext()) { System.out.println("There are no hosts :,("); }
				while(i.hasNext()) { System.out.println(i.next()); }
				
				break;
				
			case "2" :
				if (network.getHostIds().isEmpty()) {
					System.out.println("There are no hosts :,(");
					break;
				}
				System.out.println("Inform the sending host id:");
				String fromId = getExistingId();
				System.out.println("Inform the receiving host id:");
				String toId = getExistingId();
				System.out.println("Inform the message to be sent:");
				String message = scanner.nextLine();
				System.out.println();
				network.sendLocalMessage(fromId, toId, message);
				
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











