package com.example.demo.Repository;

import com.example.demo.Entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Integer> {
    @Query("SELECT COUNT(m.id) FROM Meeting m " +
            "WHERE m.roomId = :roomId " +  // Check the room
            "AND m.date = :date " +  // On the same date
            "AND (m.startTime < :end AND m.endTime > :start)")
    public Integer countMeetingConfict (Integer roomId,Date date, Time start, Time end);

    @Query("SELECT m FROM Meeting m WHERE DATE(m.date) < CURRENT_DATE OR (DATE(m.date) = CURRENT_DATE AND m.endTime < CURRENT_TIME)")
    List<Meeting> findPastMeetings();
}
