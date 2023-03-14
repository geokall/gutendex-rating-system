package com.morotech.javachallenge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;

import java.time.LocalDateTime;

import static com.morotech.javachallenge.utils.MoroConstant.CACHE_BOOKS;
import static com.morotech.javachallenge.utils.MoroConstant.CACHE_BOOK_DETAILS;

@Configuration
public class CacheConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

    private static final long TWELVE_HOURS_IN_MILLISECONDS = Timer.ONE_HOUR * 12;

    @Autowired
    public CacheConfig() {
    }

    @Scheduled(fixedRate = TWELVE_HOURS_IN_MILLISECONDS)
    @CacheEvict(cacheNames = {CACHE_BOOKS, CACHE_BOOK_DETAILS}, allEntries = true)
    public void clearSoftOneCacheValues() {
        LOGGER.info("Cleared cached books and book details at: " + LocalDateTime.now());
        LOGGER.info("Next clear cache will happen at: " + LocalDateTime.now().plusHours(12));
    }
}
