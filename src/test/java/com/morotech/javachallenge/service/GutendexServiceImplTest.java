package com.morotech.javachallenge.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class GutendexServiceImplTest {

    private final GutendexService gutendexService;

    @Autowired
    GutendexServiceImplTest(GutendexService gutendexService) {
        this.gutendexService = gutendexService;
    }

    @Test
    public void findBookBy() {
    }
}