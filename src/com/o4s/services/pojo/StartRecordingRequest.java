package src.com.o4s.services.pojo;

import lombok.Data;

@Data
public class StartRecordingRequest {
    String operation;
    String room_id;
    String meeting_url;
    Boolean record;
    Resolution resolution;
}
