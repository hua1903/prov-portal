package utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJson {
	public final static String pathResource = System.getProperty("user.dir") + "/src/test/resources/testDataResources";

	public static List<String> getAllBureaus() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		List<String> account = new ArrayList<String>();
		Object object = parser.parse(new FileReader(pathResource + "/Json BureauList.js"));
		JSONObject jsonObject = (JSONObject) object;
		JSONArray dataArray = (JSONArray) jsonObject.get("data");
		int length = dataArray.size();
		for (int i = 0; i < length; i++) {
			account.add(dataArray.get(i).toString());
		}
		Collections.sort(account);
		return account;
	}

	public static List<List<String>> getAllRatePlanSummary() {
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
			Object object = parser.parse(new FileReader(pathResource + "/Json RatePlanList.js"));
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

	public static List<List<String>> getSepecificCallRate(int id)
			throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		List<List<String>> allCallRate = new ArrayList<List<String>>();
		String[] jsonLabel = { "initialDurationSec", "ratePerCost", "rateFixed", "ratePerKB", "ratePerSecond",
				"rate30Sec", "rate60Sec", "cappedTimeSec", "cappedRateInitial", "cappedRateAfterTime" };
		Object object = parser.parse(new FileReader(pathResource + "/Json CallRateWithID" + id + ".js"));
		JSONObject jsonObject = (JSONObject) object;
		JSONArray dataArray = (JSONArray) jsonObject.get("data");
		int length = dataArray.size();
		for (int i = 0; i < length; i++) {
			List<String> callRate = new ArrayList<String>();
			JSONObject ob = (JSONObject) dataArray.get(i);
			JSONObject obCallRateCat = (JSONObject) ob;
			callRate.add(obCallRateCat.get("description").toString());
			for (int j = 0; j < jsonLabel.length; j++) {
				callRate.add(ob.get(jsonLabel[j]).toString());
			}
			callRate.add(ob.get("floatingCredit").toString());
			allCallRate.add(callRate);
		}
		return allCallRate;
	}

	public static List<List<String>> getSerNumList(int id) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		List<List<String>> allSerNum = new ArrayList<List<String>>();
		String[] jsonLabel = { "cidn", "salutation", "firstName", "lastName", "longAddress", "contactNumber", "email",
				"serviceNumber" };
		Object object = parser.parse(new FileReader(pathResource + "/Json SerNumWithID" + id + ".js"));
		JSONObject jsonObject = (JSONObject) object;
		JSONArray dataArray = (JSONArray) jsonObject.get("data");
		int length = dataArray.size();
		for (int i = 0; i < length; i++) {
			List<String> serNum = new ArrayList<String>();
			JSONObject ob = (JSONObject) dataArray.get(i);
			for (int j = 0; j < jsonLabel.length; j++) {
				serNum.add(ob.get(jsonLabel[j]).toString());
			}
			allSerNum.add(serNum);
		}
		return allSerNum;
	}

}
