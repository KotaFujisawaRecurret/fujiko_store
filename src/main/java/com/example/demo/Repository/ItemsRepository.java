package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Items;

public interface ItemsRepository extends JpaRepository<Items, Integer>{
	
	

}


