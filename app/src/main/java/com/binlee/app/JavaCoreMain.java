package com.binlee.app;

import com.binlee.asm.AsmMain;
import com.binlee.design.Design;
import com.binlee.thread.ConcurrenceMain;

public final class JavaCoreMain {

    public JavaCoreMain() {
    }

    public static void main(String[] args) {
        ConcurrenceMain.run(args);
        Design.run(args);
        AsmMain.run(args);
        // Statistics.run(new String[]{"/home/binli/Documents/work-log-quarter-3.md", "Bug#\\d{6}"});
    }
}
