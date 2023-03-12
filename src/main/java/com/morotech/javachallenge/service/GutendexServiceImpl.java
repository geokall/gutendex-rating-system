package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.GutendexDTO;
import com.morotech.javachallenge.feign.GutendexFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GutendexServiceImpl implements GutendexService {

    private final GutendexFeignClient gutendexFeignClient;

    @Autowired
    public GutendexServiceImpl(GutendexFeignClient gutendexFeignClient) {
        this.gutendexFeignClient = gutendexFeignClient;
    }

    @Override
    public GutendexDTO searchBook(String bookTitle, Long page) {
        return gutendexFeignClient.searchBookBy(page, bookTitle);
    }
}
