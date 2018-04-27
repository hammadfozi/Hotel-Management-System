package com.hms.service;

import com.hms.model.RoomType;
import com.hms.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("roomTypeService")
@Transactional
public class RoomTypeService {

    @Autowired
    RoomTypeRepository dao;

    public RoomType findById(int id) {
        Optional<RoomType> tmp = dao.findById(id);
        return tmp.orElse(null);
    }

    public RoomType findByType(String type) {
        return dao.findByType(type);
    }

    public List<RoomType> findAll() {
        return dao.findAll();
    }
}
