package com.hms.service;

import com.hms.helpers.Constant;
import com.hms.model.Room;
import com.hms.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.hms.helpers.Constant.ROOM_STATUS.VERIFIED;

/**
 * @Transactional changes to model is reflected automatically in database
 */

@Service("roomService")
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository dao;

    public Room findById(int id) {
        Optional<Room> tmp = dao.findById(id);
        return tmp.orElse(null);
    }

    public Room findByName(String name) {
        return dao.findByName(name).orElse(null);
    }

    public List<Room> findByTypeId(int id) {
        return dao.findByTypeIdAndStatus(id, VERIFIED);
    }

    public List<Room> findByStatus(String status) {
        return dao.findByStatus(status);
    }

    public void saveRoom(@NonNull Room room) {
        dao.save(room);
    }

    public void updateRoom(@NonNull Room room) {
        Optional<Room> entity = dao.findById(room.getId());
        if (entity.isPresent()) {
            room.setId(entity.get().getId());
            dao.save(room);
        }
    }

    public void deleteRoomById(int id) {
        dao.deleteById(id);
    }

    public void deleteRoomByName(String name) {
        dao.deleteByName(name);
    }

    public List<Room> findAllRooms() {
        return dao.findAll();
    }

    public boolean isRoomNameUnique(Integer id, String name) {
        Room room = findByName(name);
        return (room == null || ((id != null) && (room.getId().equals(id))));
    }

    public List<Room> findFreeRooms() {
        return dao.findFreeRooms(Constant.ROOM_STATUS.VERIFIED);
    }

    public List<Room> findFreeRooms(Integer min, Integer max, String type) {
        if (type != null && type.toLowerCase().equals(Constant.ROOM_TYPE.FAMILY.toLowerCase())) {
            return dao.findFreeRooms(min, max, Constant.ROOM_TYPE_VALUE.FAMILY, Constant.ROOM_STATUS.VERIFIED);
        } else if (type != null && type.toLowerCase().equals(Constant.ROOM_TYPE.DELUXE.toLowerCase())) {
            return dao.findFreeRooms(min, max, Constant.ROOM_TYPE_VALUE.DELUXE, Constant.ROOM_STATUS.VERIFIED);
        } else if (type != null && type.toLowerCase().equals(Constant.ROOM_TYPE.EXECUTIVE.toLowerCase())) {
            return dao.findFreeRooms(min, max, Constant.ROOM_TYPE_VALUE.EXECUTIVE, Constant.ROOM_STATUS.VERIFIED);
        }
        return dao.findFreeRooms(min, max, Constant.ROOM_STATUS.VERIFIED);
    }

    public List<Room> searchByName(String text) {
        return dao.findByName(text, Constant.ROOM_STATUS.VERIFIED);
    }

}
