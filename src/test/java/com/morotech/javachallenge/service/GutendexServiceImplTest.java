package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.GutendexDTO;
import com.morotech.javachallenge.dto.ResultDTO;
import com.morotech.javachallenge.exception.MoroNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class GutendexServiceImplTest {

    private final GutendexService gutendexService;

    @Autowired
    public GutendexServiceImplTest(GutendexService gutendexService) {
        this.gutendexService = gutendexService;
    }

    @Test
    public void findBookBy() {
        ResultDTO book = gutendexService.findBookBy(1513L);

        assertEquals(1513L, book.getId().longValue());
    }

    @Test
    public void throwNotFoundOnNotExistBook() {
        assertThrows(MoroNotFoundException.class, () -> gutendexService.findBookBy(666999L));
    }

    @Test
    public void searchBooks() {
        GutendexDTO book = gutendexService.searchBooks("Romeo and Juliet", 1L);
        ResultDTO bookResult = book.getResults().stream().findFirst().orElse(null);

        assertNotNull(bookResult);
        assertEquals("Romeo and Juliet", bookResult.getTitle());
    }
}