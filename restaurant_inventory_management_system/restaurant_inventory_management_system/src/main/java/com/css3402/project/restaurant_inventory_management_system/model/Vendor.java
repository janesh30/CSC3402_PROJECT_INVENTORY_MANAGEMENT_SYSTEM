package com.css3402.project.restaurant_inventory_management_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "VENDORS")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VENDOR_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    private String email;

    // ----- GETTERS AND SETTERS -----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}