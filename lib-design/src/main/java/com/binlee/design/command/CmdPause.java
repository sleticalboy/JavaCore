package com.binlee.design.command;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public class CmdPause implements ICommand {

    private final AudioPlayer player;

    public CmdPause(AudioPlayer player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.pause();
    }
}
