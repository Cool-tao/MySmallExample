package example.mysmallexample;

import android.app.Application;

/**
 * Created by afwang on 13-9-17.
 */
public class CmgameApplication extends Application {

	protected static final String TAG = "CmgameApplication";

	public void onCreate() {
		System.loadLibrary("megjb");
		HookManager.load();
	}
}
