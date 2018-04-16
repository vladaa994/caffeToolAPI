package caffeToolAPI.dto;

/**
 * Created by pc-mg on 1/31/2018.
 */
public class MessageDto {

    private String message;

    public MessageDto(){

    }

    public MessageDto(String message){
        this.message =  message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
