package com.morotech.javachallenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.morotech.javachallenge.dto.BookDetailsDTO;
import com.morotech.javachallenge.dto.RatingDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RatingServiceImplTest {

    @MockBean
    private RatingService ratingService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void rateBook() {
        RatingDTO dto = new RatingDTO();
        dto.setBookId(1513L);
        dto.setRating(5.0);
        dto.setReview("random review");

        BookDetailsDTO bookDetailsDTO = new BookDetailsDTO();
        bookDetailsDTO.setId(1513L);

        when(ratingService.rateBook(dto)).thenReturn(bookDetailsDTO);

        assertEquals(bookDetailsDTO.getId(), dto.getBookId());
    }

    @Test
    public void findSavedBook() throws Exception {
        RatingDTO dto = new RatingDTO();
        dto.setBookId(6593L);
        dto.setRating(2.0);
        dto.setReview("test review");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(dto);

        mvc.perform(MockMvcRequestBuilders.post("/rate/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}