package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Items;
import com.example.demo.Repository.ItemsRepository;

@Service
public class ItemService {
	
	
	@Autowired
	ItemsRepository itemsRepository;
	
	
	public List<Items> getAllItems(){
		
			
		return itemsRepository.findAll(Sort.by(Direction.ASC, "code"));
	}
	

	

	
}


