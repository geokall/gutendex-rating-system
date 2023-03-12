package com.morotech.javachallenge.feign;

import com.morotech.javachallenge.configuration.FeignClientConfiguration;
import com.morotech.javachallenge.dto.GutendexDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gutendex",
        url = "${app.gutendex.api.url}",
        configuration = FeignClientConfiguration.class)
public interface GutendexFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/books")
    GutendexDTO searchBooksBy(@RequestParam(required = false) Long page, @RequestParam String search);

    @RequestMapping(method = RequestMethod.GET, value = "/books")
    GutendexDTO findBookBy(@RequestParam Long ids);

}
