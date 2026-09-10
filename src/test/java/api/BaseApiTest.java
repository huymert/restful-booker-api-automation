package api;

import org.testng.annotations.BeforeClass;
import utils.ConfigReader;

public abstract class BaseApiTest {

    @BeforeClass
    public void setupApi() {
        // Cấu hình RestAssured RequestSpecification & Token Auth sẽ hoàn thiện ở Sprint 1
    }
}
