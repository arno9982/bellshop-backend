package com.bellshop.backend.services;

import com.bellshop.backend.models.Admin;

public interface AdminService {
    Admin getAdminByUsername(String username);
    Admin createAdmin(Admin admin); // Pratique pour créer ton premier compte admin
}