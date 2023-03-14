package com.morotech.javachallenge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;

import static com.morotech.javachallenge.utils.MoroConstant.CACHE_BOOKS;

@Configuration
public class CacheConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

    private static final long ONE_DAY = Timer.ONE_DAY;

    private final CacheManager cacheManager;

    @Autowired
    public CacheConfig(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = ONE_DAY)
    @CacheEvict(value = CACHE_BOOKS, allEntries = true)
    public void clearSoftOneCacheValues() {
        LOGGER.info("Cleared softOne cached values");
    }

//    @Scheduled(fixedRate = ONE_DAY)
//    public void evictAllCachesPerDay() {
//        evictAllCaches();
//    }

    private void evictAllCaches() {
        cacheManager.getCacheNames().parallelStream()
                .forEach(cacheName -> {
                    Cache cache = cacheManager.getCache(cacheName);

                    if (cache != null) {
                        cache.clear();
                    }
                });
    }
}
