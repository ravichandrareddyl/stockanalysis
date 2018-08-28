// package com.ravi.scheduling;

// import com.ravi.service.LocalCacheEvict;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Profile;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;


// @Component
// @Profile("simple-cache")
// public class EvictScheduler {

//     @Autowired
//     private LocalCacheEvict localCacheEvict;

//     private static final Logger LOGGER = LoggerFactory.getLogger(EvictScheduler.class);

//     @Scheduled(fixedDelay=300000)
//     public void clearCaches() {

//         LOGGER.info("Invalidating caches");

//         localCacheEvict.evictDbStatsCache();
//         localCacheEvict.evictFebaStatsCache();
//     }


// }