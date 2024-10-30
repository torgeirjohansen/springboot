package com.example.withcustomauthdemo.generics;

import java.util.List;

import com.google.common.collect.ImmutableList;

import org.testng.annotations.Test;

public class GenericsTest {

    @Test
    public void testGenerics() {
        // Covariance:  you cannot add new elements to this list because the compiler
        // doesn’t know the specific subtype it should allow—it's read-only.
        // Covariant: Use ? extends T when you’re only going to read from a structure
        List<? extends Animal> animals = List.of(
            new Cat("Whiskers", "White"),
            new Dog("Buddy", "Golden Retriever"));
        Animal animal = animals.get(0);
        //animals.add(new Dog("Buddy", "Golden Retriever")); // Compile-time error

        // Contravariance: You can add Dog instances to this list,
        // but when you read from it, you'll get an Object, as the compiler only knows it’s a superclass of Dog
        // Use ? super T when you’re only going to write to a structure
        List<? super Dog> dogs = ImmutableList.of(new Animal("Rex"), new Object());
        dogs.add(new Dog("Buddy", "Golden Retriever"));
    }
}
