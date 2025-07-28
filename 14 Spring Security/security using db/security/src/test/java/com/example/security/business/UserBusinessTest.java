package com.example.security.business;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UserBusinessTest {
    @Test
    void test() {
        Set<String> stringSet = new HashSet<>();
        stringSet.add("test");
        stringSet.add("test");
        System.out.println(stringSet);
    }
}