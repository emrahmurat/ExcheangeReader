package com.bilgeadam.boost.java.course01.exchangerate;

public interface Exchangeable {
	String getDate();

	String getName();

	float getBuyingPrice();

	float getSellingPrice();

	boolean isForex();
}
