package com.kator.weightguard.ui.media;

import java.awt.*;

/**
 * Created by prats on 4/17/18.
 */
public class SystemSoundPlayer implements SoundPlayer {
    @Override
    public void playAlarm() {
        final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
        if (runnable != null) runnable.run();
    }
}
