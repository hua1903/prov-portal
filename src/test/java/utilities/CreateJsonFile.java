package utilities;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dataProvider.ConfigFileReader;
import managers.FileReaderManager;
import stepDefinitions.LoginSteps;

public class CreateJsonFile {
	public static String urlGeneral;
	public static String url;
	public static String jsessID;
	public static String globalUserName =LoginSteps.globalUserName;
	public static String globalPassword = LoginSteps.globalPassword;

	public static void createJsonFileBureauList() throws Exception {
		haveToLoginForGettingSessionID();
		url = urlGeneral + "/allBureaus";
		String result = connectToWebServiceAfterLogin(url, "GET", null);
		writeJson(result, "BureauList");
	}

	public static void createJsonFileRatePlanList(String bureauName)
			throws Exception {
		haveToLoginForGettingSessionID();
		url = urlGeneral + "/cindyRatePlan/" + bureauName;
		String result = connectToWebServiceAfterLogin(url, "GET", null);
		writeJson(result, "RatePlanList");
	}

	public static void createJsonFileRatePlanAdminList() throws Exception {
		haveToLoginForGettingSessionID();
		url = urlGeneral + "/ratePlan/admin?status=PENDING";
		String result = connectToWebServiceAfterLogin(url, "GET", null);
		writeJson(result, "RatePlanAdminList");
	}

	public static void createJsonFileSpecificRatePlan( String bureauName, int id)
			throws Exception {
		haveToLoginForGettingSessionID();
		url = urlGeneral + "/cindyCallRate/" + bureauName + "/" + id;
		String result = connectToWebServiceAfterLogin(url, "GET", null);
		writeJson(result, "RatePlanWith" + id);
	}

	public static void createJsonFileCallRateWithID(String bureauName, int ratePlanId)
			throws Exception {
		haveToLoginForGettingSessionID();
		url = urlGeneral + "/cindyCallRate/" + bureauName + "/" + ratePlanId;
		String result = connectToWebServiceAfterLogin(url, "GET", null);
		writeJson(result, "CallRateWithID" + ratePlanId);
	}

	public static void createJsonFileSerNumWithID(String bureauName, int ratePlanId)
			throws Exception {
		haveToLoginForGettingSessionID();
		url = urlGeneral + "/cindyRatePlan/" + bureauName + "/serviceNumbers/" + ratePlanId;
		String result = connectToWebServiceAfterLogin(url, "GET", null);
		writeJson(result, "SerNumWithID" + ratePlanId);
	}

	public static void writeJson(String result, String value) throws IOException {
		ConfigFileReader configReader = FileReaderManager.getInstance().getConfigReader();
		String classPath = System.getProperty("user.dir") + configReader.getClassPath();
		PrintWriter writer = new PrintWriter(classPath + "/testDataResources/Json " + value + ".js", "UTF-8");
		writer.println(result);
		writer.close();
	}

	public static void writeJsonAccSer(String tabName, String result, String value) throws IOException {
		ConfigFileReader configReader = FileReaderManager.getInstance().getConfigReader();
		String classPath = System.getProperty("user.dir") + configReader.getClassPath();
		PrintWriter writer = new PrintWriter(classPath + "/testDataResources/" + tabName + " " + value + ".js",
				"UTF-8");
		writer.println(result);
		writer.close();
	}

	public static void haveToLoginForGettingSessionID() throws IOException {
		urlGeneral = FileReaderManager.getInstance().getConfigReader().getURLPostman();
		String urlLogin = urlGeneral + "/login";
		String urlParameters = "username=" +globalUserName  + "&password=" + globalPassword;
		String result = connectToWebServiceWithLoginNotJson(urlLogin, "POST", urlParameters);
		Log.info(result);
	}

	public static String connectToWebServiceWithLogin(String urlString, String method, String postBody) {
		String result = null;
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(urlString).openConnection();
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod(method.toUpperCase());
			if (method.equalsIgnoreCase("POST")) {
				con.getOutputStream().write(postBody.getBytes());
				con.getOutputStream().flush();
			}
			con.connect();
			jsessID = getCookieValue(con);
			if (con.getResponseCode() == 200 || con.getResponseCode() == 202 || con.getResponseCode() == 201) {
				result = readContentOfStream(con.getInputStream());
			} else {
				result = readContentOfStream(con.getErrorStream());
			}
		} catch (IOException e) {
		}
		return result;
	}

	public static String connectToWebServiceWithLoginNotJson(String urlString, String method, String urlParameters) {
		String result = null;
		try {
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			HttpURLConnection con = (HttpURLConnection) new URL(urlString).openConnection();
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod(method.toUpperCase());
			if (method.equalsIgnoreCase("POST")) {
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				con.setRequestProperty("charset", "utf-8");
				con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
				con.setUseCaches(false);
				try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
					wr.write(postData);
				}
			}
			con.connect();
			jsessID = getCookieValue(con);
			if (con.getResponseCode() == 200 || con.getResponseCode() == 202 || con.getResponseCode() == 201) {
				result = readContentOfStream(con.getInputStream());
			} else {
				result = readContentOfStream(con.getErrorStream());
			}
		} catch (IOException e) {
		}
		return result;
	}

	public static String getCookieValue(HttpURLConnection con) {
		Map<String, List<String>> headerFields = con.getHeaderFields();
		Set<String> headerFieldsSet = headerFields.keySet();
		Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
		String cookieValue = "";
		while (hearerFieldsIter.hasNext()) {
			String headerFieldKey = hearerFieldsIter.next();
			if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
				List<String> headerFieldValue = headerFields.get(headerFieldKey);
				for (String headerValue : headerFieldValue) {
					System.out.println("Cookie Found...");
					String[] fields = headerValue.split(";\\s*");
					cookieValue = fields[0];
					break;
				}
			}

		}
		return cookieValue;
	}

	public static String connectToWebServiceAfterLogin(String urlString, String method, String postBody)
			throws InterruptedException {
		String result = null;
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(urlString).openConnection();
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Cookie", jsessID);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod(method.toUpperCase());
			if (method.equalsIgnoreCase("POST")) {
				con.getOutputStream().write(postBody.getBytes());
				con.getOutputStream().flush();
			}
			con.connect();
			Thread.sleep(5000);
			if (con.getResponseCode() == 200 || con.getResponseCode() == 202 || con.getResponseCode() == 201) {
				result = readContentOfStream(con.getInputStream());
			} else {
				result = readContentOfStream(con.getErrorStream());
			}
		} catch (IOException e) {
		}
		return result;
	}

	private static String readContentOfStream(InputStream inputStream) {
		StringBuilder stringBuilder = new StringBuilder();
		byte[] readBuffer = new byte[4096];
		int read;
		try {
			while ((read = inputStream.read(readBuffer)) != -1) {
				stringBuilder.append(new String(readBuffer, 0, read));
			}
		} catch (IOException e) {
		}
		return stringBuilder.toString();
	}

}
