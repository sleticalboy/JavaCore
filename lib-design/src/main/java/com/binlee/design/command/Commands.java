package com.binlee.design.command;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class Commands {

    public static void exec() {
        final AudioPlayer player = new AudioPlayer();

        final AudioController controller = new AudioController();
        controller.setPlay(new CmdPlay(player));
        controller.setPause(new CmdPause(player));
        controller.setResume(new CmdResume(player));

        controller.start();
        controller.stop();
        controller.restart();
    }
}
