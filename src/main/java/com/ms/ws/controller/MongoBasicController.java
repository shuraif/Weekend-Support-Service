package com.ms.ws.controller;

import com.ms.ws.model.entity.GroceryItem;
import com.ms.ws.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mongobasic")
public class MongoBasicController {

  @Autowired
  MongoService mongoService;

  @GetMapping("/insertobjects")
  public ResponseEntity<?> createObjects(@RequestBody GroceryItem item) {

    return mongoService.insertData(item);

  }

  @GetMapping("/readobjects")
  public ResponseEntity<?> showAllGroceryItems() {

    return mongoService.readData();

  }

  @GetMapping("/count")
  public ResponseEntity<?> getItemCount() {

    return mongoService.getCounts();

  }


}
