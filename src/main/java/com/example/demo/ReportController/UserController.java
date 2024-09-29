package com.example.demo.ReportController;

import com.example.demo.Entity.Room;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value="/getUsers")
    public List<User> getUsers()
    {
        return userService.getUsers();
    }
        @GetMapping(value="/getUserById/{id}")
    public User getUserById(@PathVariable (name="id") int id)
    {
        return userService.getUserById(id);
    }
    @GetMapping(value="/getUserName/{name}")
    public List<User> getInfoById(@PathVariable (name="name") String name)
    {
        return userService.getUserByName(name);
    }
    @GetMapping(value="/getUserName2/")
    public List<User> getInfoByName(@RequestParam (name="name") String name)
    {
        return userService.getUserByName(name);
    }
    @GetMapping(value="/getUserName3/")
    public List<Room> getInfoByNameAndId(@RequestParam (name="id") int id, @RequestParam (name="name") String name)
    {
        return userService.getInfoByNameAndId(id,name);
    }

    @PostMapping(value="/addUser")
    public String addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @PutMapping(value="/updateData")
    public String updateData(@RequestBody User user)
    {
        return userService.updateUser(user);
    }

    @DeleteMapping(value="/deleteData")
    public String deleteData(@RequestParam (name="id") int id)
    {
        return userService.deleteUser(id);
    }
}
