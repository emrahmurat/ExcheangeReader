package com.bilgeadam.boost.java.course01.exchangerate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExchangeRateReader {

	public static void main(String[] args) {
		ExchangeRateReader reader = new ExchangeRateReader();

		if (args.length > 0) {
			reader.setCurrencyPreferences(args);
		}
		else {
			reader.setNoCurrencyPreferences();
		}
		reader.retrieveCurrentRates();
		reader.showExchangeRates();
	}

	private void showExchangeRates() {
		System.out.println("Döviz Cinsi                      Döviz Kodu\tAlýþ Kuru\tSatýþ Kuru");
		Currency[] currencies = Currency.currencies();
		for (Currency currency : currencies) {
			System.out.println(currency.exchangeRateInfo());
		}
	}

	private void retrieveCurrentRates() {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder        builder;
			builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(new URL("https://www.tcmb.gov.tr/kurlar/today.xml").openStream());
			String   date     = document.getDocumentElement().getAttribute("Date");
			System.out.println(date);

			NodeList nodeList = document.getDocumentElement().getElementsByTagName("Currency");

			Currency[] currencies = Currency.currencies();
			for (Currency currency : currencies) {
				if (currency.isCurrencyToShow()) {
					Node node = nodeList.item(currency.ordinal());
					if (node.getNodeType() == Node.ELEMENT_NODE) {

						Element element = (Element) node;
						currency.setName(element.getElementsByTagName("Isim").item(0).getTextContent());
						currency.setUnit(
								Integer.parseInt(element.getElementsByTagName("Unit").item(0).getTextContent()));
						String banknoteBuying = element.getElementsByTagName("BanknoteBuying").item(0).getTextContent();
						if (banknoteBuying.isEmpty()) {
							currency.setBuyingPrice(Double
									.parseDouble(element.getElementsByTagName("ForexBuying").item(0).getTextContent()));
							currency.setSellingPrice(Double.parseDouble(
									element.getElementsByTagName("ForexSelling").item(0).getTextContent()));
							currency.setForex(true);
						}
						else {
							currency.setBuyingPrice(Double.parseDouble(banknoteBuying));
							currency.setSellingPrice(Double.parseDouble(
									element.getElementsByTagName("BanknoteSelling").item(0).getTextContent()));
							currency.setForex(false);
						}
					}
				}
			}
		}
		catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		}
		catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		catch (SAXException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private void setCurrencyPreferences(String... currencyCodes) {

		int order = 0;
		for (String currencyCode : currencyCodes) {
			Currency currency = Currency.getCurrencyByCode(currencyCode);
			if (currency != null) {
				currency.setPreferredOrder(order++);
				currency.showCurrency();
			}
		}
	}

	private void setNoCurrencyPreferences() {
		Currency[] currencies = Currency.values();
		for (Currency currency : currencies) {
			currency.showCurrency();
		}
	}
}
