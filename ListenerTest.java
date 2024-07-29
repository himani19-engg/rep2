package solRetailIHM.Runner;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import solRetailIHM.ScenarioMainClass.ScenarioMain_UK;

public class ListenerTest implements ITestListener  {

	public void onTestStart(ITestResult result) {
		System.out.println("=====================Test case Execution Started============================");
		System.out.println("Name of the Started test case is "+result.getName());	
		
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("=====================Test case Passed============================");
		System.out.println("Name of the Passed test case is "+result.getName());	
		
		
	}

	public void onTestFailure(ITestResult result)  {
		System.out.println("=====================Test case Got Failed============================");
		System.out.println("Name of the Failed test case is "+result.getName());
		//Assert.assertFalse(false);
		//org.testng.Assert.fail();
		//ScenarioMain_UK.logger.log(Status.FAIL,MarkupHelper.createLabel("Failed Scenario", ExtentColor.RED));
		//ScenarioMain_UK.driver.close();
	}

	public void onTestSkipped(ITestResult result) {
		System.err.println("=====================Test case Started============================");
		System.out.println("Name of the Skipped test case is "+result.getName());	
		ScenarioMain_UK.logger.log(Status.WARNING,MarkupHelper.createLabel("Skipped scenario", ExtentColor.ORANGE));
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		ScenarioMain_UK.logger.log(Status.WARNING,MarkupHelper.createLabel("Failed within success percentage scenario", ExtentColor.ORANGE));
		
	}

	public void onStart(ITestContext context) {
		System.out.println("=====================Test case Started============================");
		
	}

	public void onFinish(ITestContext context) {
		System.out.println("=====================Test case Got Finished============================");
		
	}

	
	
}
