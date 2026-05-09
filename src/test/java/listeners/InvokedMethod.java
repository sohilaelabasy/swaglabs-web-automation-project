package listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class InvokedMethod implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

        System.out.println("Starting Test: "
                + method.getTestMethod().getMethodName());
    }


    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Finished Test: "
                + method.getTestMethod().getMethodName());
    }
}
