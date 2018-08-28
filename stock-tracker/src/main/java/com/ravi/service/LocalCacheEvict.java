// package com.ravi.service;

// import org.springframework.cache.annotation.CacheEvict;
// import org.springframework.context.annotation.Profile;
// import org.springframework.stereotype.Component;

// @Component
// @Profile("simple-cache")
// public class LocalCacheEvict {

//     @CacheEvict(cacheNames = "dbStats",allEntries = true)
//     public void evictDbStatsCache() {}

//     @CacheEvict(cacheNames = "febaStats",allEntries = true)
//     public void evictFebaStatsCache() {}

// }