package com.example.demo.Service;

import com.example.demo.Entity.Room;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public List<User> getUsers()
    {
        return repository.findAll();
    }

    public String addUser(User e)
    {
        User savedUser=repository.save(e);
        if(savedUser.getId()!=null)
            return "Saved Successfully!";
        else
            return "Failed";
    }
    public String updateUser(User e) {
        User savedUser = repository.save(e);
        if (savedUser.getId() != null)
            return "Updated Successfully!";
        else
            return "Failed";
    }

    public User getUserById(int id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent())
            return user.get();
        else
            return null;
    }

    public List<User> getUserByName(String name) {
        List<User> user = repository.findByName(name);
        return  user;
    }

    public String deleteUser(int id) {
        Optional<User> emp = repository.findById(id);
        if (emp.isPresent()) {
            repository.deleteById(id);
            return "Deleted";
        }
        else
            return "Not Found";
    }

    public List<Room> getInfoByNameAndId(int id, String name) {
        return repository.findByNameAndId(name,id);
    }

    public boolean checkAvailibilityUser(List<Integer> id, Date date, Time startTime, Time endTime)
    {
        List<Integer> list=repository.checkAvailability(id,date,startTime,endTime);
        return list.isEmpty();
    }
}
