# MeetingScheduler
Project on scheduling Meeting
## Project Overview
This project aims to build a meeting scheduler with the ability to:
- Detect meeting collisions (schedule conflicts).
- Schedule meetings with individuals or multiple participants.
- Include room accommodation and check availability of rooms and participants.

## Features
- Schedule a meeting with a specified day, time, and duration.
- Detect time conflicts when scheduling a new meeting.
- Include meeting rooms in the scheduling process.
- Detect time conflicts for participants and room availability.
- Schedule meetings with multiple participants.
- Remove past meetings automatically.
- Restriction on adding user obusy in other meeting.
- Restriction on adding past meeting where endtime<curtime.

## API Contract

### Endpoints
1. **Add a User**
   - **POST** `/addUser`
   - **Description**: Add a new User.
   - **Request Body**:
     ```json
     {
        "name":"Username"
     }
     ```
2. **Get all User**
   - **GET** `/getUsers`
   - **Description**: Get all User.

3. **Get User By Id**
   - **GET** `/getUserById/1`
   - **Description**: Get User By Id.

4. **Add a Room**
   - **POST** `/addRoom`
   - **Description**: Add a new Room.
   - **Request Body**:
     ```json
     {
       "name":"R1",
       "capacity":10
     }
     ```
5. **Get all Room**
   - **GET** `getRooms`
   - **Description**: Get all Rooms.

6. **Get Room By Id**
   - **GET** `/getRoomId/1`
   - **Description**: Get Room By Id.
     
7. **Add a Meeting**
   - **POST** `/addMeeting`
   - **Description**: Add a new Meeting.
   - **Request Body**:
     ```json
     {
        "personId":3,
        "date":"2024-09-26",
        "startTime":"10:00:00",
        "endTime":"11:00:00",
        "roomId":1
     }
     ```
     
8. **Get all Meetings**
   - **GET** `/getMeetings`
   - **Description**: Get all Meetings.

9. **Get Room By Id**
   - **GET** `/getMeetingById/9`
   - **Description**: Get Meeting By Id.
  
10. **Check Conflict**
   - **GET** `/meeting/conflict?date=2024-09-29&startTime=15:44:00&endTime=15:48:00&roomId=1`
   - **Description**: Check Conflict.
   - **Request Body**:
     ```json
     {
         "personId": 1,
         "participants": [2, 3, 4, 8],
         "date": "2024-09-29",
         "startTime": "15:40:00",
         "endTime": "15:50:00",
         "roomId": 2
     }
     ```
