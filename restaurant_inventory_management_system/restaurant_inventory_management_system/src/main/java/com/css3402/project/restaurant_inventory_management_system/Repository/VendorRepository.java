package com.css3402.project.restaurant_inventory_management_system.Repository;

import com.css3402.project.restaurant_inventory_management_system.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
}