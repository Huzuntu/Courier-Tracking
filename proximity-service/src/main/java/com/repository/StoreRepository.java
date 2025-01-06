package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Stores;

public interface StoreRepository extends JpaRepository<Stores, Long>
{

}
