package caffeToolAPI.dto;

import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;

/**
 * Created by pc-mg on 7/18/2018.
 */
public class PlayerDto {

    private String firstname;
    private String lastname;
    private MultipartFile image;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
