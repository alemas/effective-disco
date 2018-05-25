import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class App {
	
	protected static LocalNetwork network = new LocalNetwork();

	public static void main(String[] args) throws Exception {
		
		System.out.println("Selecione uma das opções:");
		System.out.println("0 - Criar um novo host");
		System.out.println("1 - Listar hosts existentes");
		System.out.println("q - Sair\n");
		
		Scanner scanner = new Scanner(System.in);
		boolean shouldRun = true;
		
		while (shouldRun) {
			String choice = scanner.nextLine();
					
			switch(choice) {
			case "0" :
				System.out.println("Informe o identificador do novo host:");
				String id = scanner.nextLine();
				
				while (network.hasHost(id)) {
					System.out.println("Informe um identificador que não esteja em uso:");
					id = scanner.nextLine();
				}
				network.createHost(id);
				System.out.println("Host criado com sucesso!");
				
				break;
				
			case "1" :
				ArrayList<String> hosts = network.getHostIds();
				Iterator i = hosts.iterator();
				if (!i.hasNext()) { System.out.println("Não existe nenhum host :,("); }
				while(i.hasNext()) { System.out.println(i.next()); }
				
				break;
				
			case "q" : shouldRun = false;
			
				break;
			
			default :
				System.out.println("Opção inválida");
			}
			
			System.out.println();
		}
	}

}











