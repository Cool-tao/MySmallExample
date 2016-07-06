package example.mysmallexample.ui;

import android.os.AsyncTask;

import example.mysmallexample.ui.listener.TestListener;

public class LoadJosnTask extends AsyncTask<String, Void, String> {

    private TestListener testListener;

    public LoadJosnTask(TestListener testListener) {
        this.testListener = testListener;
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonData = params[0];
        return jsonData;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            testListener.onTestListener(result);
        }

    }
}