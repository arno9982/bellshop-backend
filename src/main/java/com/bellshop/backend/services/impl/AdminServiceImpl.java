package com.bellshop.backend.services.impl;

import com.bellshop.backend.models.Admin;
import com.bellshop.backend.repositories.AdminRepository;
import com.bellshop.backend.services.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé : " + username));
    }

    @Override
    public Admin createAdmin(Admin admin) {
        // Note : Plus tard, on ajoutera le hachage du mot de passe ici avec BCrypt
        return adminRepository.save(admin);
    }
}