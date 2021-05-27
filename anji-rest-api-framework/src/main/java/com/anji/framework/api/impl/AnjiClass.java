package com.anji.framework.api.impl;

import java.text.DecimalFormat;

import org.apache.commons.lang3.RandomUtils;

public class AnjiClass {
	
	public static void main(String args[]) {
		
		double avalue = RandomUtils.nextDouble();
		
		System.out.println(new DecimalFormat("#").format(avalue));
	}

}
