package expense.track.application.util;

import java.util.UUID;

public class CommonUtils {
    public static String generateUUID(){
        // dash less UUID
        return UUID.randomUUID().toString().replaceAll("-" , "");
    }
}
