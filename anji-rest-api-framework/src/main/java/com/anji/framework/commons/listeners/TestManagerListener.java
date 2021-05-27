package com.anji.framework.commons.listeners;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.anji.framework.commons.config.ConfigLoader;


/**
 * @author boddupally.anji
 *
 * Implementation of TestNG's ITestListner to perform activities based on test run status
 * and pre and post setup before suite starts
 */
public class TestManagerListener implements ITestListener {

	private Logger getLogger(String className) {
		Logger logger = Logger.getLogger(className);
		return logger;

	}

	@Override
	public void onTestStart(ITestResult result) {
		Logger logger = getLogger(result.getTestClass().getName());
		logger.info(result.getMethod().getMethodName() + " started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Logger logger = getLogger(result.getTestClass().getName());
		logger.info(result.getMethod().getMethodName() + " success");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Logger logger = getLogger(result.getTestClass().getName());
		logger.info(result.getMethod().getMethodName() + " failed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		Logger logger = getLogger(result.getTestClass().getName());
		logger.info(result.getMethod().getMethodName() + " skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {

		Logger logger = getLogger(context.getSuite().getName());
		logger.info("Starting Test Suite Execution");
		ConfigLoader.loadCongifuration();

	}

	@Override
	public void onFinish(ITestContext context) {
		Logger logger = getLogger(context.getSuite().getName());
		logger.info("Finishing Test Suite Execution");

	}
}
