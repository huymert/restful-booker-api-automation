package api;

import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest extends BaseApi {

    @BeforeClass(alwaysRun = true)
    public void setupApi() {
        System.out.println("========================================");
        System.out.println("  API Base URL: " + BASE_URL);
        System.out.println("  Test Class : " + this.getClass().getSimpleName());
        System.out.println("========================================");
    }
}
