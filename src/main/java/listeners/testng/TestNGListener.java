package listeners.testng;

import driverFactory.Driver;
import org.testng.*;
import utilities.ScreenShotManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static utilities.properties.PropertiesManager.initializeProperties;

public class TestNGListener implements ITestListener, IExecutionListener, IAlterSuiteListener {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Map<String, Integer> testResults = new HashMap<>();
    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;
    private int skippedTests = 0;

    @Override
    public void onExecutionStart() {
        System.out.println("**************** Welcome to Selenium Framework *****************");
        System.out.println("Execution started at: " + dateFormat.format(new Date()));
        initializeProperties();
    }

    @Override
    public void onExecutionFinish() {
        System.out.println("\n=============== EXECUTION SUMMARY ===============");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("Skipped: " + skippedTests);
        System.out.println("Execution finished at: " + dateFormat.format(new Date()));
        System.out.println("********************* End of Execution *********************");

        // Generate simple HTML report
        generateHTMLReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        totalTests++;
        System.out.println("\n********* Starting Test: " + result.getName() + " *************");
        System.out.println("Test Description: " + result.getMethod().getDescription());
        System.out.println("Test Parameters: " + getParametersAsString(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests++;
        testResults.put(result.getName(), ITestResult.SUCCESS);
        System.out.println("\n********* Test Passed: " + result.getName() + " *************");
        System.out.println("Execution Time: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedTests++;
        testResults.put(result.getName(), ITestResult.FAILURE);

        System.out.println("\n********* Test Failed: " + result.getName() + " *************");
        System.out.println("Failure Reason: " + result.getThrowable().getMessage());

        // Capture screenshot
        System.out.println("Taking screenshot...");
        captureScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests++;
        testResults.put(result.getName(), ITestResult.SKIP);
        System.out.println("\n********* Test Skipped: " + result.getName() + " *************");
        System.out.println("Skip Reason: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("\n********* Test Failed Within Success Percentage: " + result.getName() + " *************");
    }

    private void captureScreenshot(ITestResult result) {
        Driver driver = null;
        ThreadLocal<Driver> driverThreadLocal;
        Object currentClass = result.getInstance();
        Field[] fields = result.getTestClass().getRealClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                if (field.getType() == Driver.class) {
                    field.setAccessible(true);
                    driver = (Driver) field.get(currentClass);
                }
                if (field.getType() == ThreadLocal.class) {
                    field.setAccessible(true);
                    driverThreadLocal = (ThreadLocal<Driver>) field.get(currentClass);
                    if (driverThreadLocal != null) {
                        driver = driverThreadLocal.get();
                    }
                }
            }

            if (driver != null && driver.get() != null) {
                ScreenShotManager.CaptureScreenShot(driver.get(), result.getName());
            } else {
                System.out.println("Driver instance not available for screenshot");
            }
        } catch (IllegalAccessException exception) {
            System.out.println("Unable to get field: " + exception.getMessage());
        }
    }

    private String getParametersAsString(ITestResult result) {
        Object[] parameters = result.getParameters();
        if (parameters == null || parameters.length == 0) {
            return "None";
        }

        StringBuilder sb = new StringBuilder();
        for (Object param : parameters) {
            sb.append(param != null ? param.toString() : "null").append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    private void generateHTMLReport() {
        // Simple HTML report generation
        String reportContent = "<html><head><title>Test Execution Report</title></head><body>" +
                "<h1>Test Execution Report</h1>" +
                "<p>Generated at: " + dateFormat.format(new Date()) + "</p>" +
                "<table border='1'><tr><th>Test Name</th><th>Status</th></tr>";

        for (Map.Entry<String, Integer> entry : testResults.entrySet()) {
            String status;
            switch (entry.getValue()) {
                case ITestResult.SUCCESS:
                    status = "<span style='color:green'>PASSED</span>";
                    break;
                case ITestResult.FAILURE:
                    status = "<span style='color:red'>FAILED</span>";
                    break;
                case ITestResult.SKIP:
                    status = "<span style='color:orange'>SKIPPED</span>";
                    break;
                default:
                    status = "UNKNOWN";
            }
            reportContent += "<tr><td>" + entry.getKey() + "</td><td>" + status + "</td></tr>";
        }

    }
}