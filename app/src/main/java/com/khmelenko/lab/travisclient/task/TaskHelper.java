package com.khmelenko.lab.travisclient.task;

import com.khmelenko.lab.travisclient.TravisApp;
import com.khmelenko.lab.travisclient.network.retrofit.RestClient;

import javax.inject.Inject;

/**
 * Helper class for Task
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class TaskHelper {

    @Inject
    RestClient mRestClient;

    public TaskHelper() {
        TravisApp app = (TravisApp) TravisApp.getAppContext();
        app.getNetworkComponent().inject(this);
    }

}