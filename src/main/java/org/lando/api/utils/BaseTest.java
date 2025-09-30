package org.lando.api.utils;

import org.lando.api.config.RestAssuredConfig;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setup() {
        RestAssuredConfig.init();
    }
}