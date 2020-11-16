package com.binlee.design.command;

import com.binlee.util.Logger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class AudioPlayer {

    private final Logger logger = Logger.get(AudioPlayer.class);

    public void play() {
        logger.i("play() ...");
    }

    public void pause() {
        logger.i("pause() ...");
    }

    public void resume() {
        logger.i("resume() ...");
    }
}
