package com.example.demo.ReportController;

import com.example.demo.Entity.Meeting;
import com.example.demo.Entity.Room;
import com.example.demo.Repository.MeetingRepository;
import com.example.demo.Repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class MeetingCleanupTask {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private RoomRepository roomRepository;
    // Run this every night at midnight
    //@Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    @Scheduled(cron = "0 */5 * * * ?")
    public void removePastMeetings() {
        List<Meeting> pastMeetings = meetingRepository.findPastMeetings();

        for(Meeting m:pastMeetings)
        {
            Optional<Room> room=roomRepository.findById(m.getRoomId());
            if(room.isPresent()) {
                Room r=room.get();
                r.setLeftCapacity(r.getLeftCapacity()+m.getParticipants().size());
            }
        }
        // Option 1: Delete past meetings
        if (!pastMeetings.isEmpty()) {
            meetingRepository.deleteAll(pastMeetings);
        }

    }
}