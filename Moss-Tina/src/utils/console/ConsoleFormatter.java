package utils.console;

import java.io.File;
import java.time.LocalDateTime;

public class ConsoleFormatter {
	public static String formatFile(File file){
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.toString() + ":" + file.getPath();
	}
}
