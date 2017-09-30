package com.example.android.java;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

// A fragment object can retain memory during a configuration change.
// So best way to make sure that async task and that activity is not destroyed when
// orientation changes is to create a fragment and host the task there.
// It can keep the task running when activity is destroyed and recreated.
public class MainActivity extends AppCompatActivity implements AsyncFragment.ParentActivity {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private ScrollView mScroll;
    private TextView mLog;
    private AsyncFragment mFragment;//Declare instance of a fragment as a field
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mLog.setText("");

        android.app.FragmentManager manager = getFragmentManager();
        mFragment = (AsyncFragment) manager.findFragmentByTag(FRAGMENT_TAG);
//        Find out if fragment is already in the memory and if it isn't then instantiate and add it to the current aactivity.
        if(mFragment==null){
            mFragment = new AsyncFragment();
            manager.beginTransaction().add(mFragment,FRAGMENT_TAG).commit();
        }
    }

    public void onRunBtnClick(View v) {
        mFragment.runAsyncTask("Chocolate","Vanilla","Strawberry");
    }

    public void onClearBtnClick(View v) {
        mLog.setText("");
        mScroll.scrollTo(0, mScroll.getBottom());
    }

    public void displayMessage(String message) {
        mLog.append(message + "\n");
        mScroll.scrollTo(0, mScroll.getBottom());
    }

    @Override
    public void handleTaskUpdate(String message) {
        displayMessage(message);
    }
//    When the user clicks the "run" button,
//    I'm running the code to run the AsyncTask in the fragment.
//    In the fragment, I'm creating an instance of the AsyncTask class,
//    and executing it, and then as the task is executing,
//    it's passing information back to the parent, through the "handleTaskUpdate" method,
//    that's declared in the interface ParentActivity.
}