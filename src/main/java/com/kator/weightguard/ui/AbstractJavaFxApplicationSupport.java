package com.kator.weightguard.ui;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by prats on 3/8/18.
 */
public abstract class AbstractJavaFxApplicationSupport extends Application {
    private static String[] savedArgs;
    protected ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(getClass(), savedArgs);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    protected static void launchApp(Class<? extends AbstractJavaFxApplicationSupport> appClass, String[] args) {
        AbstractJavaFxApplicationSupport.savedArgs = args;
        Application.launch(appClass, args);
    }
}
