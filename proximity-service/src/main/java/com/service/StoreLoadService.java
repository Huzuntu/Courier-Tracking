package com.service;
// package com.proximityservice.proximity_service.service;

// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.proximityservice.proximity_service.model.Stores;
// import com.proximityservice.proximity_service.repository.StoreRepository;

// import jakarta.annotation.PostConstruct;

// @Service
// public class StoreLoadService 
// {
//     private final StoreRepository _storeRepository;

//     public StoreLoadService(StoreRepository storeRepository)
//     {
//         this._storeRepository = storeRepository;
//     }

//     @PostConstruct
//     public void loadStoreData() throws IOException {
//         // Load JSON file from the resources folder
//         InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stores.json");
//         if (inputStream == null) 
//         {
//             throw new FileNotFoundException("stores.json file not found in resources");
//         }

//         // Convert JSON to List of Store objects using Jackson
//         ObjectMapper objectMapper = new ObjectMapper();
//         List<Stores> stores = objectMapper.readValue(inputStream, new TypeReference<List<Stores>>() {});

//         // Save stores to the database
//         _storeRepository.saveAll(stores);
//     }

// }
