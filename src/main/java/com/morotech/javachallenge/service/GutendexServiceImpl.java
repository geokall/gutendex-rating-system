package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.GutendexDTO;
import com.morotech.javachallenge.dto.ResultDTO;
import com.morotech.javachallenge.exception.MoroNotFoundException;
import com.morotech.javachallenge.feign.GutendexFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.morotech.javachallenge.utils.MoroConstant.GUTENDEX_BOOK_NOT_FOUND;
import static com.morotech.javachallenge.utils.MoroConstant.BOOK_RESULTS_NOT_FOUND;

@Service
public class GutendexServiceImpl implements GutendexService {

    private final GutendexFeignClient gutendexFeignClient;

    @Autowired
    public GutendexServiceImpl(GutendexFeignClient gutendexFeignClient) {
        this.gutendexFeignClient = gutendexFeignClient;
    }

    @Override
    public GutendexDTO searchBooks(String bookTitle, Long page) {
        return gutendexFeignClient.searchBooksBy(page, bookTitle);
    }

    @Override
    public ResultDTO findBookBy(Long id) {
        GutendexDTO book = gutendexFeignClient.findBookBy(id);

        if (book.getResults().isEmpty()) {
            throw new MoroNotFoundException(GUTENDEX_BOOK_NOT_FOUND);
        }

        return book.getResults().stream()
                .findFirst()
                .orElseThrow(() -> {
                    throw new MoroNotFoundException(BOOK_RESULTS_NOT_FOUND);
                });
    }
}
