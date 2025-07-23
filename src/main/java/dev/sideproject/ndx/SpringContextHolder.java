package dev.sideproject.ndx;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private  ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext ac) throws BeansException {
        context = ac;
    }
}

