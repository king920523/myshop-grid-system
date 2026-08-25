package com.example.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myshop.GridAdmin;

public interface GridAdminRepository extends JpaRepository<GridAdmin,Long>{

    GridAdmin findByUsername(String username);
}
