package com.example.withcustomauthdemo.controller;

import com.example.withcustomauthdemo.model.StoredString;
import com.example.withcustomauthdemo.service.StringService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SampleControllerTest {
    private final StoredString storedString = new StoredString("id", "content");
    private StringService stringService;
    private SampleController sampleController;

    @BeforeMethod
    public void setUp() {
        stringService = mock(StringService.class);
        when(stringService.getStringById(any())).thenReturn(storedString);
        sampleController = new SampleController(stringService);
    }

    @Test
    public void testGetStringReturnsCorrectValue() {
        assertThat(sampleController.getStringById("id")).isEqualTo(storedString);
    }
}
