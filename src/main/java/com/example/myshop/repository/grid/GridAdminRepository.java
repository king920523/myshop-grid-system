package com.example.myshop.repository.grid;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myshop.entity.grid.GridAdmin;

public interface GridAdminRepository extends JpaRepository<GridAdmin,Long>{

    GridAdmin findByUsername(String username);
}
