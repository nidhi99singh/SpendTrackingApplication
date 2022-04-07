package expense.track.application.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageByLocale {
    @Autowired
    MessageSource messageSource;

    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
    public String getMessage(String msg) {
        return messageSource.getMessage(msg, null, getLocale());
    }

}
