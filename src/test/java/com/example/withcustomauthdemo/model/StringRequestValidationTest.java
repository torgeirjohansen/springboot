package com.example.withcustomauthdemo.model;

import jakarta.validation.Validator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

public class StringRequestValidationTest {
    private Validator validator;

    @BeforeMethod
    public void setUp() {
        validator = buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testInputValidation_maxLength() {
        StringRequest stringRequest = new StringRequest("a".repeat(256));

        var violations = validator.validate(stringRequest);

        assertThat(violations).hasSize(1);
        assertThat(violations.toArray()[0]).toString()
                                           .contains("Content must not exceed 255 characters");
    }
}
