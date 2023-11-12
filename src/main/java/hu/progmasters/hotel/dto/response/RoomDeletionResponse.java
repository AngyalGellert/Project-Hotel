package hu.progmasters.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDeletionResponse {

    private String deletionMessage;

    public void setDeletionMessage(Long id, String name) {
        deletionMessage = "Room named " + name + " with ID " + id + " is now deleted";
    }
}