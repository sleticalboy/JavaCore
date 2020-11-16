package com.binlee.design.command;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class AudioController {

    private ICommand play;
    private ICommand pause;
    private ICommand resume;

    public void setPlay(ICommand play) {
        this.play = play;
    }

    public void setPause(ICommand pause) {
        this.pause = pause;
    }

    public void setResume(ICommand resume) {
        this.resume = resume;
    }

    public void start() {
        play.execute();
    }

    public void stop() {
        pause.execute();
    }

    public void restart() {
        resume.execute();
    }
}
