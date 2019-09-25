package dataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class RatePlanDataReader {
	private Properties properties;
	private String propertyFilePath = System.getProperty("user.dir") + "/config/RatePlanData.properties";

	public RatePlanDataReader() throws IOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("RatePlanData.properties not found at " + propertyFilePath);
		}
	}

	public String getRatePlanID() {
		String ratePlanID = properties.getProperty("ratePlanID");
		if (ratePlanID != null)
			return ratePlanID;
		else
			throw new RuntimeException("ratePlanID is not specified in the RatePlanData.properties");
	}

	public String getDescription() {
		String description = properties.getProperty("description");
		if (description != null)
			return description;
		else
			throw new RuntimeException("description is not specified in the RatePlanData.properties");
	}

	public String getComments() {
		String comments = properties.getProperty("comments");
		if (comments != null)
			return comments;
		else
			throw new RuntimeException("comments is not specified in the RatePlanData.properties");
	}

	public String getSubmittedBy() {
		String submittedBy = properties.getProperty("submittedBy");
		if (submittedBy != null)
			return submittedBy;
		else
			throw new RuntimeException("submittedBy is not specified in the RatePlanData.properties");
	}

	public String getSubmittedDate() {
		String submittedDate = properties.getProperty("submittedDate");
		if (submittedDate != null)
			return submittedDate;
		else
			throw new RuntimeException("submittedDate is not specified in the RatePlanData.properties");
	}

	public String getIddReference() {
		String iddReference = properties.getProperty("iddReference");
		if (iddReference != null)
			return iddReference;
		else
			throw new RuntimeException("iddReference is not specified in the RatePlanData.properties");
	}

	public String getRatePlanType() {
		String ratePlanType = properties.getProperty("ratePlanType");
		if (ratePlanType != null)
			return ratePlanType;
		else
			throw new RuntimeException("ratePlanType is not specified in the RatePlanData.properties");
	}

	public String getCallRateCat() {
		String callRateCat = properties.getProperty("callRateCat");
		if (callRateCat != null)
			return callRateCat;
		else
			throw new RuntimeException("callRateCat is not specified in the RatePlanData.properties");
	}

	public String getInitialDuration() {
		String initialDuration = properties.getProperty("initialDuration");
		if (initialDuration != null)
			return initialDuration;
		else
			throw new RuntimeException("initialDuration is not specified in the RatePlanData.properties");
	}

	public String getRatePerCost() {
		String ratePerCost = properties.getProperty("ratePerCost");
		if (ratePerCost != null)
			return ratePerCost;
		else
			throw new RuntimeException("ratePerCost is not specified in the RatePlanData.properties");
	}

	public String getRateFixed() {
		String rateFixed = properties.getProperty("rateFixed");
		if (rateFixed != null)
			return rateFixed;
		else
			throw new RuntimeException("rateFixed is not specified in the RatePlanData.properties");
	}

	public String getRatePerKB() {
		String ratePerKB = properties.getProperty("ratePerKB");
		if (ratePerKB != null)
			return ratePerKB;
		else
			throw new RuntimeException("ratePerKB is not specified in the RatePlanData.properties");
	}

	public String getRatePerSecond() {
		String ratePerSecond = properties.getProperty("ratePerSecond");
		if (ratePerSecond != null)
			return ratePerSecond;
		else
			throw new RuntimeException("ratePerSecond is not specified in the RatePlanData.properties");
	}

	public String getRatePer30Sec() {
		String ratePer30Sec = properties.getProperty("ratePer30Sec");
		if (ratePer30Sec != null)
			return ratePer30Sec;
		else
			throw new RuntimeException("ratePer30Sec is not specified in the RatePlanData.properties");
	}

	public String getRatePer60Sec() {
		String ratePer60Sec = properties.getProperty("ratePer60Sec");
		if (ratePer60Sec != null)
			return ratePer60Sec;
		else
			throw new RuntimeException("ratePer60Sec is not specified in the RatePlanData.properties");
	}

	public String getCapFixed() {
		String capFixed = properties.getProperty("capFixed");
		if (capFixed != null)
			return capFixed;
		else
			throw new RuntimeException("capFixed is not specified in the RatePlanData.properties");
	}

	public String getCapMax() {
		String capMax = properties.getProperty("capMax");
		if (capMax != null)
			return capMax;
		else
			throw new RuntimeException("capMax is not specified in the RatePlanData.properties");
	}

	public String getCapRate() {
		String capRate = properties.getProperty("capRate");
		if (capRate != null)
			return capRate;
		else
			throw new RuntimeException("capRate is not specified in the RatePlanData.properties");
	}

	public String getFloatCredit() {
		String floatCredit = properties.getProperty("floatCredit");
		if (floatCredit != null)
			return floatCredit;
		else
			throw new RuntimeException("floatCredit is not specified in the RatePlanData.properties");
	}

	public String getCIDNSerNum() {
		String cidnSerNum = properties.getProperty("cidnSerNum");
		if (cidnSerNum != null)
			return cidnSerNum;
		else
			throw new RuntimeException("cidnSerNum is not specified in the RatePlanData.properties");
	}

	public String getSalutationSerNum() {
		String salutationSerNum = properties.getProperty("salutationSerNum");
		if (salutationSerNum != null)
			return salutationSerNum;
		else
			throw new RuntimeException("salutationSerNum is not specified in the RatePlanData.properties");
	}

	public String getFirstNameSerNum() {
		String firstNameSerNum = properties.getProperty("firstNameSerNum");
		if (firstNameSerNum != null)
			return firstNameSerNum;
		else
			throw new RuntimeException("firstNameSerNum is not specified in the RatePlanData.properties");
	}

	public String getLastNameSerNum() {
		String lastNameSerNum = properties.getProperty("lastNameSerNum");
		if (lastNameSerNum != null)
			return lastNameSerNum;
		else
			throw new RuntimeException("lastNameSerNum is not specified in the RatePlanData.properties");
	}

	public String getAddressSerNum() {
		String addressSerNum = properties.getProperty("addressSerNum");
		if (addressSerNum != null)
			return addressSerNum;
		else
			throw new RuntimeException("addressSerNum is not specified in the RatePlanData.properties");
	}

	public String getContactNumSerNum() {
		String contactNumSerNum = properties.getProperty("contactNumSerNum");
		if (contactNumSerNum != null)
			return contactNumSerNum;
		else
			throw new RuntimeException("contactNumSerNum is not specified in the RatePlanData.properties");
	}

	public String getEmailSerNum() {
		String emailSerNum = properties.getProperty("emailSerNum");
		if (emailSerNum != null)
			return emailSerNum;
		else
			throw new RuntimeException("emailSerNum is not specified in the RatePlanData.properties");
	}

	public String getServiceNumber() {
		String serviceNumber = properties.getProperty("serviceNumber");
		if (serviceNumber != null)
			return serviceNumber;
		else
			throw new RuntimeException("serviceNumber is not specified in the RatePlanData.properties");
	}

	public String getTargetBureauDBox() {
		String targetBureauDBox = properties.getProperty("targetBureauDBox");
		if (targetBureauDBox != null)
			return targetBureauDBox;
		else
			throw new RuntimeException("targetBureauDBox is not specified in the RatePlanData.properties");
	}

	public String getDesCreateCopy() {
		String desCreateCopy = properties.getProperty("desCreateCopy");
		if (desCreateCopy != null)
			return desCreateCopy;
		else
			throw new RuntimeException("desCreateCopy is not specified in the RatePlanData.properties");
	}

	public String getIDDCBox() {
		String iDDCBox = properties.getProperty("iDDCBox");
		if (iDDCBox != null)
			return iDDCBox;
		else
			throw new RuntimeException("iDDCBox is not specified in the RatePlanData.properties");
	}

	public String getCreate() {
		String create = properties.getProperty("create");
		if (create != null)
			return create;
		else
			throw new RuntimeException("create is not specified in the RatePlanData.properties");
	}

	public String getCopy() {
		String copy = properties.getProperty("copy");
		if (copy != null)
			return copy;
		else
			throw new RuntimeException("copy is not specified in the RatePlanData.properties");
	}

	public String getCancel() {
		String cancel = properties.getProperty("cancel");
		if (cancel != null)
			return cancel;
		else
			throw new RuntimeException("cancel is not specified in the RatePlanData.properties");
	}

	public String getWholesaleAmountCal() {
		String wholesaleAmountCal = properties.getProperty("wholesaleAmountCal");
		if (wholesaleAmountCal != null)
			return wholesaleAmountCal;
		else
			throw new RuntimeException("wholesaleAmountCal is not specified in the RatePlanData.properties");
	}

	public String getRetailAmountCal() {
		String retailAmountCal = properties.getProperty("retailAmountCal");
		if (retailAmountCal != null)
			return retailAmountCal;
		else
			throw new RuntimeException("retailAmountCal is not specified in the RatePlanData.properties");
	}

	public String getAmount() {
		String amount = properties.getProperty("amount");
		if (amount != null)
			return amount;
		else
			throw new RuntimeException("amount is not specified in the RatePlanData.properties");
	}

	public String getTitleCalRatePerCost() {
		String titleCalRatePerCost = properties.getProperty("titleCalRatePerCost");
		if (titleCalRatePerCost != null)
			return titleCalRatePerCost;
		else
			throw new RuntimeException("titleCalRatePerCost is not specified in the RatePlanData.properties");
	}

	public String getResultRatePerCost() {
		String resultRatePerCost = properties.getProperty("resultRatePerCost");
		if (resultRatePerCost != null)
			return resultRatePerCost;
		else
			throw new RuntimeException("resultRatePerCost is not specified in the RatePlanData.properties");
	}

	public String getTitleCalRatePerKB() {
		String titleCalRatePerKB = properties.getProperty("titleCalRatePerKB");
		if (titleCalRatePerKB != null)
			return titleCalRatePerKB;
		else
			throw new RuntimeException("titleCalRatePerKB is not specified in the RatePlanData.properties");
	}

	public String getDataTypeCal() {
		String dataTypeCal = properties.getProperty("dataTypeCal");
		if (dataTypeCal != null)
			return dataTypeCal;
		else
			throw new RuntimeException("dataTypeCal is not specified in the RatePlanData.properties");
	}

	public String getResultKB() {
		String resultKB = properties.getProperty("resultKB");
		if (resultKB != null)
			return resultKB;
		else
			throw new RuntimeException("resultKB is not specified in the RatePlanData.properties");
	}
}