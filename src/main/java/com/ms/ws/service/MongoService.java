package com.ms.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ms.ws.model.entity.GroceryItem;
import com.ms.ws.repo.ItemRepository;

@Service
public class MongoService {

	@Autowired
	ItemRepository groceryItemRepo;

	public ResponseEntity<?> insertData(GroceryItem item) {

		List<GroceryItem> items = new ArrayList<>();

//		System.out.println("Data creation started...");
//		items.add(groceryItemRepo.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks")));
//		items.add(groceryItemRepo.save(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets")));
//		items.add(groceryItemRepo.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices")));
//		items.add(groceryItemRepo.save(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 1, "millets")));
//		items.add(groceryItemRepo.save(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks")));

		groceryItemRepo.save(item);

		items = groceryItemRepo.findAll();
		System.out.println("Data creation complete...");

		return new ResponseEntity<List<GroceryItem>>(items, HttpStatus.ACCEPTED);

	}

	public ResponseEntity<?> readData() {

		List<GroceryItem> items = groceryItemRepo.findAll();

		return new ResponseEntity<>(items, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<?> getCounts() {
		
		long count = groceryItemRepo.count();
		return new ResponseEntity<>(count, HttpStatus.ACCEPTED);
	}

}
