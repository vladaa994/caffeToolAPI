package caffeToolAPI.helper;

import org.springframework.stereotype.Component;

/**
 * Created by pc-mg on 7/11/2018.
 */
@Component
public class Helper {

    public float getMinutesFromMilis(long milis) {
        float minutes = milis / 60000;
        return minutes;
    }
}
