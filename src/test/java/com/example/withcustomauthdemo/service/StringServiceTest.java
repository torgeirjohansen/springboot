package com.example.withcustomauthdemo.service;

import java.util.Optional;

import com.example.withcustomauthdemo.model.StoredString;
import com.example.withcustomauthdemo.model.StringRequest;
import com.example.withcustomauthdemo.repository.StringRepository;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StringServiceTest {
    private StringRepository stringRepository;

    private StringService stringService;

    @BeforeMethod
    public void setUp() {
        stringRepository = mock(StringRepository.class);
        stringService = new StringServiceImpl(stringRepository);
    }

    @Test
    public void testStoreString_success() {
        StringRequest request = new StringRequest("test content");
        StoredString storedString = new StoredString("1", "test content");

        when(stringRepository.save(any(StoredString.class))).thenReturn(storedString);

        StoredString result = stringService.storeString(request);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("1");
        assertThat(result.content()).isEqualTo("test content");
    }

    @Test
    public void testGetStringById_success() {
        StoredString storedString = new StoredString("1", "test content");

        when(stringRepository.findById("1")).thenReturn(Optional.of(storedString));

        StoredString result = stringService.getStringById("1");

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("1");
        assertThat(result.content()).isEqualTo("test content");
    }

    @Test
    public void testGetStringById_notFound() {
        when(stringRepository.findById("1")).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> stringService.getStringById("1"));

        assertThat(throwable).isNotNull();
    }

    @Test
    public void testDeleteStringById_success() {
        doNothing().when(stringRepository).deleteById("1");

        stringService.deleteStringById("1");

        verify(stringRepository, times(1)).deleteById("1");
    }
}