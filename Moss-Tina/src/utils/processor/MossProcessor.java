package utils.processor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import utils.console.ConsoleFormatter;
import utils.logger.FileLogger;
import utils.moji.MossException;
import utils.moji.SocketClient;

public class MossProcessor {
	/**
	 * Runs moss processor and writes console log out
	 * @param baseDir			Directory of base files, can be null if there is no base files.
	 * @param baseFileTypes		List of base file types
	 * @param projectDir		Directory of project files
	 * @param projectFileTypes	List of project file types 
	 * @param comparisonType	Comparison language -- see moss documentation if null ascii comparison (full text mode) is used.
	 * @return String with result link
	 * @throws MossException 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static String process(String baseDir, String [] baseFileTypes, String projectDir, String [] projectFileTypes, String comparisonType) throws MossException, UnknownHostException, IOException{
		// a list of students' source code files located in the prepared
		// directory.
		Collection<File> files = FileUtils.listFiles(new File(projectDir), projectFileTypes, true);

		// a list of base files that was given to the students for this
		// assignment.
		Collection<File> baseFiles = null;
		if (baseDir == null){
			baseFiles = new ArrayList<File>();
		}
		else{
			baseFiles = FileUtils.listFiles(new File(baseDir), baseFileTypes, true);
		}

		// get a new socket client to communicate with the MOSS server
		// and set its parameters.
		SocketClient socketClient = new SocketClient();

		// set your MOSS user ID
		socketClient.setUserID("615550782");
		// socketClient.setOpt...

		// set the programming language of all student source codes
		if (comparisonType == null){
			socketClient.setLanguage("ascii");
		}
		else{
			socketClient.setLanguage(comparisonType);
		}
		
		socketClient.setOptN(1000);

		// initialize connection and send parameters
		socketClient.run();

		// upload all base files
		FileLogger.logConsole("## Base files upload");
		for (File f : baseFiles) {
			FileLogger.logConsole(ConsoleFormatter.formatFile(f));
			socketClient.uploadBaseFile(f);
		}

		// upload all source files of students
		FileLogger.logConsole("## Project files upload");
		for (File f : files) {
			FileLogger.logConsole(ConsoleFormatter.formatFile(f));
			socketClient.uploadFile(f);
		}

		// finished uploading, tell server to check files
		socketClient.sendQuery();

		// get URL with MOSS results and do something with it
		URL results = socketClient.getResultURL();
		FileLogger.logConsole("Results available at " + results.toString());
		return results.toString();
	}
}
