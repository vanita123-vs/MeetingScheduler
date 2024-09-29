package com.example.demo.Service;

import com.example.demo.Entity.Meeting;
import com.example.demo.Entity.Room;
import com.example.demo.Entity.User;
import com.example.demo.Repository.MeetingRepository;
import com.example.demo.Repository.RoomRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Request.MeetingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeetingService {
    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;
    public List<Meeting> getMeetings()
    {
        return meetingRepository.findAll();
    }

    public boolean checkConflict(Integer room, Date date, Time start, Time end)
    {
        int count=meetingRepository.countMeetingConfict(room,date,start,end);
        return count >= 1;
    }
    public String addMeeting(MeetingRequest meetingRequest)
    {
        Calendar now = Calendar.getInstance(); // Current date and time
        Date currentDate = now.getTime(); // Current date and time
        Time currentTime = new Time(now.getTimeInMillis()); // Current time

// Convert the meeting request date to a Calendar instance
        Calendar meetingDateTime = Calendar.getInstance();
        meetingDateTime.setTime(meetingRequest.getDate());
        meetingDateTime.set(Calendar.HOUR_OF_DAY, meetingRequest.getEndTime().getHours());
        meetingDateTime.set(Calendar.MINUTE, meetingRequest.getEndTime().getMinutes());
        meetingDateTime.set(Calendar.SECOND, meetingRequest.getEndTime().getSeconds());

// Check if the meeting's date is in the past
        if (meetingRequest.getDate().before(new Date(currentDate.getTime() - currentDate.getTime() % (24 * 60 * 60 * 1000)))) {
            return "Cannot schedule a meeting in the past day.";
        }

// If the meeting is for today, check if the start time is in the past
        if (meetingRequest.getDate().equals(new Date(currentDate.getTime() - currentDate.getTime() % (24 * 60 * 60 * 1000))) &&
                meetingDateTime.before(now)) {
            return "Cannot schedule a meeting in the past time.";
        }
        List<User> participants = userRepository.findAllById(meetingRequest.getParticipants());
        List<Integer> fetchedIds = participants.stream()
                .map(User::getId) // Assuming User class has getId() method
                .collect(Collectors.toList());
        List<Integer> missingIds = new ArrayList<>(meetingRequest.getParticipants());
        missingIds.removeAll(fetchedIds);
        String msg="";
        msg+=missingIds;
        // Create and populate the Meeting entity
        Meeting meeting = new Meeting();
        meeting.setPersonId(meetingRequest.getPersonId());
        meeting.setDate(meetingRequest.getDate());
        meeting.setStartTime(meetingRequest.getStartTime());
        meeting.setEndTime(meetingRequest.getEndTime());
        meeting.setRoomId(meetingRequest.getRoomId());
        meeting.setParticipants(participants);
        if(checkConflict(meeting.getRoomId(),meeting.getDate(),meeting.getStartTime(),meeting.getEndTime()))
            return "Failed-Same Time booked in room";
        List<Integer> participantIds = meeting.getParticipants()
                .stream()
                .map(User::getId)  // Extract the IDs from User objects
                .collect(Collectors.toList());

        List<Integer> list=userRepository.checkAvailability(participantIds,meeting.getDate(),meeting.getStartTime(),meeting.getEndTime());

        if(!list.isEmpty())
            return  "Failed-Person "+ list + " not available";

        Optional<Room> room=roomRepository.findById(meeting.getRoomId());
        if(room.isPresent())
        {
            Room r=room.get();
            if(r.getLeftCapacity()>=meeting.getParticipants().size())
            {
                Meeting savedMeeting=meetingRepository.save(meeting);
                if(savedMeeting.getId()!=null) {
                    r.setLeftCapacity(r.getLeftCapacity()-meeting.getParticipants().size());
                    roomRepository.save(r);
                    return !msg.isEmpty() ?"Invalid user "+msg+" others Saved Successfully!":"All Saved Successfully!";
                }
                else
                    return "Failed";
            }
            else
                return "No space in room";
        }
        else
            return "Room Invalid";
    }

    public Meeting getMeetingById(int id) {
        Optional<Meeting> meeting = meetingRepository.findById(id);
        if (meeting.isPresent())
            return meeting.get();
        else
            return null;
    }

}
