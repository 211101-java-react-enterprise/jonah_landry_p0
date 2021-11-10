package com.revature.cmdBanking;

import com.revature.cmdBanking.util.AppState;

/**
 *  Main driver for the entire program.
 *  Launches us into the app.startup() function which loads up our screens.
 */

public class cmdDriver {

    public static void main(String[] args) {
        AppState app = new AppState();
        app.startup();
    }
}
