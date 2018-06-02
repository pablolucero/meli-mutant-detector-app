package com.meli.mutantdetector;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.validateMockitoUsage;

@RunWith(MockitoJUnitRunner.class)
public class MockRunnerBaseTest {

    @After
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void stubMethod() {

    }
}
