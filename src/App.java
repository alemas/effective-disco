import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
	
	protected static LocalNetwork network = new LocalNetwork();

	public static void main(String[] args) {
		
		System.out.println("Selecione uma das opções:");
		System.out.println("0 - Criar um novo host");
		System.out.println("q - Sair");
		
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			String choice = scanner.nextLine();
					
			switch(choice) {
			case "0" :
				network.createHost();
			case "q" : break;
			}
		}
	}

}











