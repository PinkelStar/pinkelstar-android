package com.pinkelstar.android.ui;

import android.app.Application;

import com.pinkelstar.android.ui.tasks.SessionTask;

public class PSApplication extends Application {

	public void onCreate() {
		SessionTask.initialize(this);
	}
	
}
