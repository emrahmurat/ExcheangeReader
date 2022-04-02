package com.bilgeadam.boost.java.course01.exchangerate;

import java.time.LocalDate;
import java.util.Locale;

public enum Currency {
	
	/*
	 * 	
	US_DOLLAR, AUSTRALIAN_DOLLAR, DANISH_KRONE, EURO, POUND_STERLING,
	SWISS_FRANK, SWEDISH_KRONA, CANADIAN_DOLLAR, KUWAITI_DINAR, NORWEGIAN_KRONE,
	SAUDI_RIYAL, JAPANESE_YEN, BULGARIAN_LEV, NEW_LEU, RUSSIAN_ROUBLE,
	IRANIAN_RIAL, CHINESE_RENMINBI, PAKISTANI_RUPEE, QATARI_RIAL, SOUTH_KOREAN_WON,
	AZERBAIJAN_NEW_MANAT, UAE_DIRHAM;
	 *
	 */

	US_DOLLAR("USD"), AUSTRALIAN_DOLLAR("AUD"), DANISH_KRONE("DKK"), EURO("EUR"), POUND_STERLING("GBP"),
	SWISS_FRANK("CHF"), SWEDISH_KRONA("SEK"), CANADIAN_DOLLAR("CAD"), KUWAITI_DINAR("KWD"), NORWEGIAN_KRONE("NOK"),
	SAUDI_RIYAL("SAR"), JAPANESE_YEN("JPY"), BULGARIAN_LEV("BGN"), NEW_LEU("RON"), RUSSIAN_ROUBLE("RUB"),
	IRANIAN_RIAL("IRR"), CHINESE_RENMINBI("CNY"), PAKISTANI_RUPEE("PKR"), QATARI_RIAL("QAR"), SOUTH_KOREAN_WON("KRW"),
	AZERBAIJAN_NEW_MANAT("AZN"), UAE_DIRHAM("AED");

	private static int numOfCurrenciesToShow = 0;
	private int        prefferedOrder;
	private boolean    list;
	private String     code;
	private String     name;
	private double     buyingPrice;
	private double     sellingPrice;
	private boolean    forex;
	private int        unit;
	public record	   rates(LocalDate day, double date) {};
	
	private Currency(String code) {
		this.prefferedOrder = this.ordinal();
		this.code           = code;
		this.list           = false;
	}

	public void setPreferredOrder(int preferredOrder) {
		int      actualOrder       = this.ordinal();
		Currency currencyToReplace = null;

		Currency[] currencies = Currency.values();
		for (Currency currency : currencies) {

			
			if (currency.prefferedOrder == preferredOrder) {
				currencyToReplace = currency;
				break;
			}
		}

		if (currencyToReplace != null && (this != currencyToReplace)) {
			this.prefferedOrder              = preferredOrder;
			currencyToReplace.prefferedOrder = actualOrder;
		}
	}

	public int getPrefferedOrder() {
		return this.prefferedOrder;
	}

	public void showCurrency() {
		this.list = true;
		Currency.numOfCurrenciesToShow++;
	}

	public void dontShowCurrency() {
		this.list = false;
	}

	public boolean isCurrencyToShow() {
		return this.list;
	}

	public String getCode() {
		return this.code;
	}

	public static Currency[] currencies() {

		Currency[] retVal = new Currency[Currency.numOfCurrenciesToShow];

		Currency[] currencies = Currency.values();
		for (Currency currency : currencies) {
			if (currency.isCurrencyToShow()) {
				retVal[currency.getPrefferedOrder()] = currency;
			}
		}

		return retVal;
	}

	public void setName(String name) {
		this.name = name.toLowerCase(new Locale("tr"));
	}

	public String getName() {
		return this.name;
	}

	public double getBuyingPrice() {
		return this.buyingPrice;
	}

	public void setBuyingPrice(double buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public double getSellingPrice() {
		return this.sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String exchangeRateInfo() {
		StringBuilder info = new StringBuilder();
		
		String forexInfo = this.forex ? " (forex)" : "";
		info.append(String.format("%-33s", this.getName()));
		info.append("   (").append(this.code).append(")\t ");
		info.append(String.format("% .4f", this.getBuyingPrice()/this.unit));
		info.append("\t  ");
		info.append(String.format("% .4f", this.getSellingPrice()/this.unit));
		info.append(forexInfo);

		return info.toString();
	}

	public static Currency getCurrencyByCode(String code) {
		Currency retVal = null;

		Currency[] currencies = Currency.values();
		for (Currency currency : currencies) {
			if (currency.getCode().equalsIgnoreCase(code)) {
				retVal = currency;
				break;
			}
		}

		return retVal;
	}

	public void setForex(boolean forex) {
		this.forex = forex;
	}

	void setUnit(int unit) {
		this.unit = unit;
	}
}
