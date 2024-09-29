package com.example.demo.Service;

import com.example.demo.Entity.Room;
import com.example.demo.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> getRooms()
    {
        return roomRepository.findAll();
    }

    public Room getRoomById(int id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent())
            return room.get();
        else
            return null;
    }

    public String addRoom(Room room)
    {
        room.setLeftCapacity(room.getCapacity());
        Room savedRoom=roomRepository.save(room);
        if(savedRoom.getId()!=null)
            return "Saved Successfully!";
        else
            return "Failed";
    }

    public boolean checkAvailibilityRoom(Integer room_Id)
    {
        Room room= getRoomById(room_Id);
        return room.getLeftCapacity()>0;
    }
}
