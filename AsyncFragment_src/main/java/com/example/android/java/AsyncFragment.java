package com.example.android.java;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Chinmaya on 9/30/2017.
 */

public class AsyncFragment extends Fragment {
//    To retain fragment in the memory I need to call method setRetainInstance
//    We need to call it in onCreate method.

    private ParentActivity mParent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public interface ParentActivity{
        void handleTaskUpdate(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParent = (ParentActivity) context;
        Log.i("AsyncFragment", "Attached");
    }
    public void runAsyncTask(String...params){
        MyTask task = new MyTask();
        task.execute(params);
    }
    class MyTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... params) {
            for (String s : params) {
                publishProgress("I got " + s);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mParent.handleTaskUpdate(values[0]);
        }
    }
}

//This fragment will have no user interface. It's entire purpose is to host asynchronous task.