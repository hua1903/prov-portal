package utilities;

import java.math.BigDecimal;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(isNumeric("1.259765625E-5"));
	}
	
	public static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

}
