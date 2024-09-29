package com.example.demo.Repository;

import com.example.demo.Entity.Room;
import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public List<User> findByName(String name);

    List<Room> findByNameAndId(String name, int id);

    @Query("SELECT p.id FROM Meeting m " +
            "JOIN m.participants p " +  // Assuming participants are a collection in Meeting
            "WHERE (m.startTime < :endTime AND m.endTime > :startTime) " +  // Time conflict check
            "AND p.id IN :participantIds " +  // Check conflicts for all participants
            "AND m.date = :date")
    List<Integer> checkAvailability(@Param("participantIds") List<Integer> participantIds,
                                    @Param("date") Date date,
                                    @Param("startTime") Time startTime,
                                    @Param("endTime") Time endTime);
}

