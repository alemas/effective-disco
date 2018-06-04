import java.io.IOException;
import java.nio.file.*;

public class FileManager {
	
	private static String rootPath = "C:\\Users\\13104485\\Documents\\";

	public static void saveFile(String filename, byte[] data) {
		Path path = Paths.get(rootPath + "incoming\\" + filename);
		try {
			Files.write(path, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] loadFile(String filename) {
		Path path = Paths.get(rootPath + "outcoming\\" + filename);
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
