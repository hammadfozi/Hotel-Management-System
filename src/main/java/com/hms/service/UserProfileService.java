package com.hms.service;

import com.hms.model.UserProfile;
import com.hms.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("userProfileService")
@Transactional
public class UserProfileService {

    @Autowired
    UserProfileRepository dao;

    public UserProfile findById(int id) {
        Optional<UserProfile> tmp = dao.findById(id);
        return tmp.orElse(null);
    }

    public UserProfile findByType(String type) {
        return dao.findByType(type);
    }

    public List<UserProfile> findAll() {
        return dao.findAll();
    }
}
