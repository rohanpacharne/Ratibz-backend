package com.lonar.master.a2zmaster.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberToWord {

	public String numberToWord(String currency, Double amount) {
		String numberToWordsStrInMillion = "";
		DecimalFormat df = new DecimalFormat("0.00");
		if (currency.equals("INR")) {
			double netAmountValueInDouble = Double.parseDouble(df.format(amount));

			String bob = BigDecimal.valueOf(netAmountValueInDouble).toPlainString();
			// String bob = Double.toString(netAmountValueInDouble);

			String[] convert = bob.split("\\.");

			long beforeDecimal = Long.parseLong(convert[0]);
			long afterDecimal = 0;
			String afterDecimalConversion = "";
			// afterDecimal = Integer.parseInt(convert[1]);
			if (convert.length >= 2) {
				afterDecimal = Long.parseLong(convert[1]);
				String afterDecimalStr = convert[1];
				boolean b = afterDecimalStr.startsWith("0"); // true
				if (b) {
					if (afterDecimal > 0) {
						afterDecimalConversion = "And " + NumberToWords.convert(afterDecimal);
					}
				} else {
					if (afterDecimal <= 9) {
						afterDecimal = afterDecimal * 10;
					}
					afterDecimalConversion = " And " + NumberToWords.convert(afterDecimal) + " Paise";
				}
			}

			String beforeDecimalConversion = NumberToWords.convert(beforeDecimal);
			String numberToWordsStr = "";
			if (netAmountValueInDouble > 0) {
				numberToWordsStr = "Rupees " + beforeDecimalConversion + "" + afterDecimalConversion + " Only.";
			}

			numberToWordsStrInMillion = numberToWordsStr;
		} else {
			double netAmountValueInDouble = Double.parseDouble(df.format(amount));
			String bob = BigDecimal.valueOf(netAmountValueInDouble).toPlainString();
			String[] convert = bob.split("\\.");
			long beforeDecimal = Long.parseLong(convert[0]);
			long afterDecimal = 0;
			String afterDecimalConversion = "";
			if (convert.length >= 2) {
				String afterDecimalStr = convert[1];
				boolean b = afterDecimalStr.startsWith("0"); // true
				afterDecimal = Integer.parseInt(convert[1]);
				if (b) {
					if (afterDecimal > 0) {
						// afterDecimalConversion = "And "+Number2WordInMillion.convert(afterDecimal);
						if (currency.equals("USD")) {
							afterDecimalConversion = "Dollars And " + Number2WordInMillion.convert(afterDecimal)
									+ " Cent";
						} else if (currency.equals("EUR")) {
							afterDecimalConversion = "Euro And " + Number2WordInMillion.convert(afterDecimal) + " Cent";
						} else if (currency.equals("GBP")) {
							afterDecimalConversion = "Pounds And " + Number2WordInMillion.convert(afterDecimal)
									+ " Penny";
						} else if (currency.equals("AUD")) {
							afterDecimalConversion = "Dollars And " + Number2WordInMillion.convert(afterDecimal)
									+ " Cent";
						} else {
							afterDecimalConversion = "And " + Number2WordInMillion.convert(afterDecimal);
						}
					}
				} else {
					if (afterDecimal <= 9) {
						afterDecimal = afterDecimal * 10;
					}
					if (currency.equals("USD")) {
						afterDecimalConversion = "Dollars And " + Number2WordInMillion.convert(afterDecimal) + " Cents";
					} else if (currency.equals("EUR")) {
						afterDecimalConversion = "Euro And " + Number2WordInMillion.convert(afterDecimal) + " Cents";
					} else if (currency.equals("GBP")) {
						afterDecimalConversion = "Pounds And " + Number2WordInMillion.convert(afterDecimal) + " Pence";
					} else if (currency.equals("AUD")) {
						afterDecimalConversion = "Dollars And " + Number2WordInMillion.convert(afterDecimal) + " Cents";
					} else {
						afterDecimalConversion = "And " + Number2WordInMillion.convert(afterDecimal);
					}

					// afterDecimalConversion = "And "+Number2WordInMillion.convert(afterDecimal);
				}
			}

			String beforeDecimalConversion = Number2WordInMillion.convert(beforeDecimal);
			numberToWordsStrInMillion = currency + " " + beforeDecimalConversion + " " + afterDecimalConversion;

		}

		return numberToWordsStrInMillion;
	}

}
