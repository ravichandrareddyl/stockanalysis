// package com.ravi.service;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// import com.ravi.constants.AppQueries;
// import com.ravi.mappers.ANCLSMapper;
// import com.ravi.model.ANCLSModel;
// import com.ravi.model.ResponseModel;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.cache.annotation.Cacheable;
// import org.springframework.context.annotation.Profile;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.stereotype.Repository;

// @Repository
// @Profile("simple-cache")
// public class DBRepo {
//     private final Logger log = LoggerFactory.getLogger(getClass());

//     @Autowired
//     private ANCLSMapper anclsMapper;

//     @Autowired 
//     @Qualifier("jdbcMap")
//     private Map<String, JdbcTemplate> jdbcMap; 

//     @Cacheable(cacheNames = "dbStats", key = "{#country}")
//     public ResponseModel getdetails(String country) {
//         List<ANCLSModel> results = new ArrayList<ANCLSModel>();
//         ResponseModel response = new ResponseModel();
//         response.setCountry(country);
//         try {
//             JdbcTemplate template = jdbcMap.get(country);
//             results = template.query(AppQueries.anclsQry, anclsMapper);
//             response.setStatus("success");
//         } catch (Exception e) {
//             e.printStackTrace();
//             log.error("Error while executing the query" + e.getMessage());
//             response.setStatus("error");
//         }
//         response.setList(results);
        
//         return response; 
//     }

// }