package com.example.demo.ReportController;

import com.example.demo.Entity.Room;
import com.example.demo.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping(value="/addRoom")
    public String addRoom(@RequestBody Room room)
    {
        return roomService.addRoom(room);
    }

    @RequestMapping(value="/getRooms")
    public List<Room> getRooms()
    {
        return roomService.getRooms();
    }

    @RequestMapping(value="/getRoomId/{id}")
    public Room getRoomById(@PathVariable(name="id") int id)
    {
        return roomService.getRoomById(id);
    }
}
