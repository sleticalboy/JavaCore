package com.binlee.design.template;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class Templates {

    public static void exec() {
        final AndroidInstrumentation instrumentation = new AndroidInstrumentation();
        AndroidActivity activity = null;
        try {
            activity = instrumentation.newActivity("com.binlee.design.template.ProfileActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (activity != null) {
            instrumentation.callLifecycleMethod(activity);
        }
    }
}
