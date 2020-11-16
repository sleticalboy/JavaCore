package com.binlee.design.template;

import com.binlee.util.Logger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public class AndroidActivity {

    protected final Logger logger = Logger.get(getClass());

    protected void onCreate() {
        logger.d("onCreate() called");
    }

    protected void onStart() {
        logger.d("onStart() called");
    }

    protected void onResume() {
        logger.d("onResume() called");
    }

    protected void onPause() {
        logger.d("onPause() called");
    }

    protected void onRestart() {
        logger.d("onRestart() called");
    }

    protected void onStop() {
        logger.d("onStop() called");
    }

    protected void onDestroy() {
        logger.d("onDestroy() called");
    }
}
