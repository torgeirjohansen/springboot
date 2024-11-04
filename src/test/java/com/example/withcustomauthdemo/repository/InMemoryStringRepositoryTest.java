package com.example.withcustomauthdemo.repository;

public class InMemoryStringRepositoryTest extends StringRepositoryTest {

    @Override
    protected StringRepository setUp() {
        return new InMemoryStringRepository();
    }
}
