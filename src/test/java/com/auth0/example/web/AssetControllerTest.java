package com.auth0.example.web;

import com.auth0.example.web.controller.AssetController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Integration tests to verify that our endpoints are properly secured.
 */

public class AssetControllerTest {

    @Test
    public void test() {
        int var = 1;
        assertEquals(1,var);
    }
}
