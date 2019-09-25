package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jayway.jsonpath.JsonPath;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dataProvider.ConfigFileReader;
import managers.FileReaderManager;

public class Helper {
	public static File jsonfile;

	public static String splitDoubleQuote(String value) {
		String[] arr = value.split("\"");
		String result = arr[1].trim().toString();
		return result;
	}

	public static String nameDateTime() {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_hh.mm.ss");
		String folderName = formatter.format(today);
		return folderName;
	}

	public static String nameDate() {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String folderName = formatter.format(today);
		return folderName;
	}

	public static String[] splitSpecificSymbold(String value, String symbol) {
		String[] arr = value.split(symbol);
		return arr;
	}

	public static String[] splitNumCharacter(String value) {
		String[] arr = value.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
		return arr;
	}

	public static String getClassPath() throws IOException {
		ConfigFileReader configReader = FileReaderManager.getInstance().getConfigReader();
		return System.getProperty("user.dir") + configReader.getClassPath();
	}

	public static String getJsonValue(String value, String fieldJson) throws IOException {
		jsonfile = new File(getClassPath() + "/testDataResources/Json " + value + ".js");
		String valueJson = JsonPath.read(jsonfile, "$." + fieldJson).toString();
		return valueJson;
	}

	public static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

	public static List<List<String>> getListCSV(String fileName) throws IOException {
		String csvFile = getClassPath() + "/testDataResources/" + fileName + ".csv";
		String line = "";
		String cvsSplitBy = ",";
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> rowArray;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] wo = line.split(cvsSplitBy);
				rowArray = Arrays.asList(wo);
				list.add(rowArray);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return list;
		}
	}

	public static MongoCollection<Document> connectRatePlanTableInMongoDB() {
		int port_no = 49418;
		String auth_user = "admin", auth_pwd = "123456", host_name = "ds249418.mlab.com", db_name = "aws",
				db_col_name = "local_rateplan", encoded_pwd = "";
		try {
			encoded_pwd = URLEncoder.encode(auth_pwd, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			Log.error(ex.toString());
		}

		String client_url = "mongodb://" + auth_user + ":" + encoded_pwd + "@" + host_name + ":" + port_no + "/"
				+ db_name;
		MongoClientURI uri = new MongoClientURI(client_url);

		@SuppressWarnings("resource")
		MongoClient mongo_client = new MongoClient(uri);

		MongoDatabase db = mongo_client.getDatabase(db_name);

		MongoCollection<Document> coll = db.getCollection(db_col_name);
		Log.info("Fetching all documents from the collection");
		return coll;
	}

	public static void deleteDocumentMongoDB(MongoCollection<Document> coll, String description) {
		try {
			coll.deleteOne(new Document("ratePlan.description", description));
		} finally {

		}
	}

	public static List<List<String>> ratePlanList() {
		JSONParser parser = new JSONParser();
		List<List<String>> ratePlanList = new ArrayList<List<String>>();
		List<String> ratePlan;
		String comments;
		String iddRatePlanId;
		String ratePlanId;
		String ratePlanType;
		String description;
		String modifiedBy;
		String modifiedDate;
		try {
			Object object = parser.parse(new FileReader(getClassPath() + "/testDataResources/Json RatePlanList.js"));
			JSONObject jsonObject = (JSONObject) object;
			JSONArray dataArray = (JSONArray) jsonObject.get("data");
			int length = dataArray.size();
			for (int i = 0; i < length; i++) {
				ratePlan = new ArrayList<String>();
				JSONObject ob = (JSONObject) dataArray.get(i);
				JSONObject planType = (JSONObject) ob.get("ratePlanType");
				try {
					comments = ob.get("comments").toString();
				} catch (Exception ex) {
					comments = "";
				}
				try {
					iddRatePlanId = ob.get("iddRatePlanId").toString();
				} catch (Exception ex) {
					iddRatePlanId = "";
				}
				ratePlanId = ob.get("ratePlanId").toString();
				ratePlanType = planType.get("description").toString();
				description = ob.get("description").toString();
				try {
					modifiedBy = ob.get("modifiedBy").toString();
				} catch (Exception ex) {
					modifiedBy = "";
				}
				try {
					modifiedDate = ob.get("modifiedDate").toString();
				} catch (Exception ex) {
					modifiedDate = "";
				}
				ratePlan.add(comments);
				ratePlan.add(ratePlanId);
				ratePlan.add(ratePlanType);
				ratePlan.add(description);
				ratePlan.add(modifiedBy);
				ratePlan.add(modifiedDate);
				ratePlan.add(iddRatePlanId);
				ratePlanList.add(ratePlan);
			}
			return ratePlanList;
		} catch (Exception ex) {
			throw new RuntimeException("Something wrong with the json file: Json RatePlanList.js");
		}
	}

	public static List<List<String>> ratePlanAdminList() {
		JSONParser parser = new JSONParser();
		List<List<String>> ratePlanAdminList = new ArrayList<List<String>>();
		List<String> ratePlanArray;
		String comments;
		String iddRatePlanId;
		String ratePlanId;
		String ratePlanType;
		String description;
		String modifiedBy;
		String modifiedDate;
		try {
			Object object = parser
					.parse(new FileReader(getClassPath() + "/testDataResources/Json RatePlanAdminList.js"));
			JSONObject jsonObject = (JSONObject) object;
			JSONArray dataArray = (JSONArray) jsonObject.get("data");
			int length = dataArray.size();
			for (int i = 0; i < length; i++) {
				ratePlanArray = new ArrayList<String>();
				try {
					JSONObject ob = (JSONObject) dataArray.get(i);
					if (ob.get("ratePlanState").toString().equalsIgnoreCase("PENDING")) {
						JSONObject ratePlan = (JSONObject) ob.get("ratePlan");
						JSONObject planType = (JSONObject) ratePlan.get("ratePlanType");
						try {
							comments = ratePlan.get("comments").toString();
						} catch (Exception ex) {
							comments = "";
						}
						try {
							iddRatePlanId = ratePlan.get("iddRatePlanId").toString();
						} catch (Exception ex) {
							iddRatePlanId = "";
						}
						try {
							ratePlanId = ratePlan.get("ratePlanId").toString();
						} catch (Exception ex) {
							ratePlanId = "New";
						}
						ratePlanType = planType.get("description").toString();
						description = ratePlan.get("description").toString();
						try {
							modifiedBy = ratePlan.get("modifiedBy").toString();
						} catch (Exception ex) {
							modifiedBy = "";
						}
						try {
							modifiedDate = ratePlan.get("modifiedDate").toString();
						} catch (Exception ex) {
							modifiedDate = "";
						}
						ratePlanArray.add(comments);
						ratePlanArray.add(ratePlanId);
						ratePlanArray.add(ratePlanType);
						ratePlanArray.add(description);
						ratePlanArray.add(iddRatePlanId);
						ratePlanArray.add(modifiedBy);
						ratePlanArray.add(modifiedDate);
						ratePlanAdminList.add(ratePlanArray);
					}
				} catch (Exception ex) {

				}
			}

			return ratePlanAdminList;
		} catch (Exception ex) {
			throw new RuntimeException("Something wrong with the json file: Json RatePlanAdminList.js");
		}
	}

	public static List<List<String>> getAccount() {
		JSONParser parser = new JSONParser();
		List<List<String>> accountList = new ArrayList<List<String>>();
		List<String> account;
		String userName;
		String password;
		String role;
		try {
			Object object = parser.parse(new FileReader(getClassPath() + "/testDataResources/AccountProvPortal.js"));
			JSONObject jsonObject = (JSONObject) object;
			JSONArray dataArray = (JSONArray) jsonObject.get("data");
			int length = dataArray.size();
			for (int i = 0; i < length; i++) {
				account = new ArrayList<String>();
				JSONObject ob = (JSONObject) dataArray.get(i);
				userName = ob.get("userName").toString();
				password = ob.get("password").toString();
				role = ob.get("role").toString();
				account.add(userName);
				account.add(password);
				account.add(role);
				accountList.add(account);
			}
			return accountList;
		} catch (Exception ex) {
			throw new RuntimeException("Something wrong with the json file: AccountProvPortal.js");
		}
	}

	public static String trimRight(String input) {
		return input.replaceAll("\\s+$", "");
	}

	public static String getCurrentDate() {
		Date date = new Date();
		String DATE_FORMAT = "d/M/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(date);
	}

	public static void DeleteAFile(String filePath, String filename) {
		File file = new File(filePath + "/" + filename);
		if (file.exists()) {
			file.delete();
		}
	}

	public static String parseScientificNotationToDecimal(String scientificNotation) {
		BigDecimal d = new BigDecimal(scientificNotation);
		return d.toPlainString();
	}

}
