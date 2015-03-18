package zadania;

import it.zielke.moji.SocketClient;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class Z1 {
	public static void main(String[] args) throws Exception {
		// a list of students' source code files located in the prepared
		// directory.
		Collection<File> files = FileUtils.listFiles(new File(
				"F:\\z1"), new String[] { "html" }, true);

		// a list of base files that was given to the students for this
		// assignment.
		Collection<File> baseFiles = FileUtils.listFiles(new File(
				"F:\\Plagiator\\moss-base-dir"), new String[] { "html" }, true);

		// get a new socket client to communicate with the MOSS server
		// and set its parameters.
		SocketClient socketClient = new SocketClient();

		// set your MOSS user ID
		socketClient.setUserID("615550782");
		// socketClient.setOpt...

		// set the programming language of all student source codes
		socketClient.setLanguage("ascii");
		socketClient.setOptN(1000);

		// initialize connection and send parameters
		socketClient.run();

		// upload all base files
		for (File f : baseFiles) {
			socketClient.uploadBaseFile(f);
		}

		// upload all source files of students
		for (File f : files) {
			socketClient.uploadFile(f);
		}

		// finished uploading, tell server to check files
		socketClient.sendQuery();

		// get URL with MOSS results and do something with it
		URL results = socketClient.getResultURL();
		System.out.println("Results available at " + results.toString());
	}
}
