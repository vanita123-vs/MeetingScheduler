package com.example.demo.ReportController;

import com.example.demo.Entity.Meeting;
import com.example.demo.Request.MeetingRequest;
import com.example.demo.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @GetMapping(value="/getMeetings")
    public List<Meeting> getMeetings()
    {
        return meetingService.getMeetings();
    }
    @GetMapping(value="/getMeetingById/{id}")
    public Meeting getMeetingById(@PathVariable(name="id") int id)
    {
        return meetingService.getMeetingById(id);
    }

    @GetMapping(value = "/meeting/conflict")
    public boolean checkConflict( @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                  @RequestParam("startTime") String startTimeStr,
                                  @RequestParam("endTime") String endTimeStr,
                                  @RequestParam("roomId") Integer roomId)
    {

        Time startTime = convertStringToTime(startTimeStr);
        Time endTime = convertStringToTime(endTimeStr);

        return meetingService.checkConflict(roomId,date, startTime, endTime);
    }

    private Time convertStringToTime(String timeStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date parsedDate = format.parse(timeStr);
            return new Time(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid time format: " + timeStr, e);
        }
    }
}
