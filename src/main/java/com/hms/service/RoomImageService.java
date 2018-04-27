package com.hms.service;

import com.hms.model.RoomImages;
import com.hms.repository.RoomImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("roomImagesService")
@Transactional
public class RoomImageService {

    @Autowired
    private RoomImageRepository dao;

    public RoomImages findById(int id) {
        Optional<RoomImages> tmp = dao.findById(id);
        return tmp.orElse(null);
    }

    public List<RoomImages> findByRoomId(int id) {
        return dao.findByRoomId(id);
    }

    public List<RoomImages> findAll() {
        return dao.findAll();
    }

    public void save(@NonNull RoomImages image) {
        dao.save(image);
    }

    public void deleteById(int id) {
        dao.deleteById(id);
    }

    public void deleteByRoomId(int id) {
        List<RoomImages> roomImages = findByRoomId(id);

        for (RoomImages roomImage : roomImages) deleteById(roomImage.getId());
    }

    public void update(@NonNull RoomImages image) {
        Optional<RoomImages> entity = dao.findById(image.getId());
        if (entity.isPresent()) {
            image.setId(entity.get().getId());
            dao.save(image);
        }
    }
}
