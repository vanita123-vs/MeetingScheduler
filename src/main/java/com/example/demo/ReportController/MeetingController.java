package com.example.demo.ReportController;

import com.example.demo.Entity.Meeting;
import com.example.demo.Entity.User;
import com.example.demo.Request.MeetingRequest;
import com.example.demo.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @PostMapping(value="/addMeeting")
    public String addMeeting(@RequestBody MeetingRequest meetingRequest)
    {
        return meetingService.addMeeting(meetingRequest);
    }

    @RequestMapping(value="/getMeetings")
    public List<Meeting> getMeetings()
    {
        return meetingService.getMeetings();
    }
    @RequestMapping(value="/getMeetingById/{id}")
    public Meeting getMeetingById(@PathVariable(name="id") int id)
    {
        return meetingService.getMeetingById(id);
    }

    @RequestMapping(value = "/meeting/conflict")
    public boolean checkConflict(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                 @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) Time startTime,
                                 @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) Time endTime,
                                 @RequestParam("roomId") Integer roomId)
    {
        return meetingService.checkConflict(roomId,date, startTime, endTime);
    }
}
