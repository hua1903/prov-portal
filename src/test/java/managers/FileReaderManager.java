package managers;

import java.io.IOException;

import dataProvider.*;

public class FileReaderManager {
	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static ConfigFileReader configFileReader;
	private static RatePlanDataReader ratePlanReader;

	private FileReaderManager() {

	}

	public static FileReaderManager getInstance() throws IOException {
		return fileReaderManager;
	}

	public ConfigFileReader getConfigReader() throws IOException {
		return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
	}

	public RatePlanDataReader getRatePlanDataReader() throws IOException {
		return (ratePlanReader == null) ? new RatePlanDataReader() : ratePlanReader;
	}

}
