package com.example.withcustomauthdemo.repository;

import com.example.withcustomauthdemo.model.StoredString;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class StringRepositoryTest {
    private StringRepository repository;

    @BeforeMethod
    protected final void testSetUp() {
        repository = setUp();
    }

    protected abstract StringRepository setUp();

    @Test
    public void testSave() {
        StoredString storedString = new StoredString("1", "test content");

        StoredString result = repository.save(storedString);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("1");
        assertThat(result.content()).isEqualTo("test content");
    }
}
