package aedalert;

import com.ciscospark.*;
import java.net.URI;
import java.util.Iterator;

class Example {
    public static void main(String[] args) {
        // To obtain a developer access token, visit http://developer.ciscospark.com
        String accessToken = "NTNkZTAwNzAtNDY0ZS00YzE2LTliMzktNGQ5ZmYwYWVmY2VhYTI5Y2E1ZjUtODdh";

        // Initialize the client
        Spark spark = Spark.builder()
                .baseUrl(URI.create("https://api.ciscospark.com/v1"))
                .accessToken(accessToken)
                .build();

        // List the rooms that I'm in
        // spark.rooms()
        //         .iterate()
        //         .forEachRemaining(room -> {
        //             System.out.println(room.getTitle() + ", created " + room.getCreated() + ": " + room.getId());
        //         });
        Iterator<Room> rooms = spark.rooms().iterate();
        while (rooms.hasNext()) {
        	Room room = rooms.next();
        	System.out.println(room.getTitle() + ", created " + room.getCreated() + ": " + room.getId());
        }

        // Create a new room
        Room room = new Room();
        room.setTitle("Dispatch");
        room = spark.rooms().post(room);


        // Add a coworker to the room
        Membership membership = new Membership();
        membership.setRoomId(room.getId());
        membership.setPersonEmail("ycfhan@gmail.com");
        spark.memberships().post(membership);


        // List the members of the room
        // spark.memberships()
        //         .queryParam("roomId", room.getId())
        //         .iterate()
        //         .forEachRemaining(member -> {
        //             System.out.println(member.getPersonEmail());
        //         });


        // Post a text message to the room
        Message message = new Message();
        message.setRoomId(room.getId());
        message.setText("Choose which AED to alert: "
        		+ "\n1) location1"
        		+ "\n2) location2"
        		+ "\n3) location3"
        		+ "\n4) location4");
        spark.messages().post(message);


        // Share a file with the room
        message = new Message();
        message.setRoomId(room.getId());
        message.setFiles(URI.create("http://example.com/hello_world.jpg"));
        spark.messages().post(message);
    }
}