package expense.track.application.util;

import expense.track.application.entity.UserActivity;
import expense.track.application.repository.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class UserActivityUtils {

    @Autowired
    private static UserActivityRepository userActivityRepository;

    public static UserActivity userActivity(String email, String description){
        UserActivity userActivity = new UserActivity();
        userActivity.setId(CommonUtils.generateUUID());
        userActivity.setEmail(email);
        userActivity.setActivityDescription(description);
        userActivity.setLocalDateTime(LocalDateTime.now());
        return userActivityRepository.save(userActivity);
    }

}
