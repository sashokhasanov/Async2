package ru.sashok.study.async2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RetainFragment.LoadListener {

    RetainFragment retainFragment;

    GetDataTask dataTask;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> displayData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, displayData);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);


        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            retainFragment = (RetainFragment) supportFragmentManager.findFragmentByTag(RetainFragment.RETAIN_FRAGMENT_TAG);
            if (retainFragment == null) {
                retainFragment = new RetainFragment();
                supportFragmentManager.beginTransaction().add(retainFragment, RetainFragment.RETAIN_FRAGMENT_TAG).commit();
            }
        } else {
            retainFragment = new RetainFragment();
            supportFragmentManager.beginTransaction().add(retainFragment, RetainFragment.RETAIN_FRAGMENT_TAG).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    dataTask = new GetDataTask(retainFragment);
                    dataTask.execute();
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("data", displayData);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            ArrayList<String> strings = savedInstanceState.getStringArrayList("data");
            displayData.clear();
            displayData.addAll(strings);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        if (!isChangingConfigurations()) {
            // leaving app
            if (dataTask != null) {
                dataTask.cancel(true);
            }
        }

        super.onStop();
    }

    @Override
    public void onResult(List<String> data) {

        if (data != null) {

            displayData.addAll(data);
            arrayAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResult(String data) {

        if (data != null) {
            displayData.add(data);
            arrayAdapter.notifyDataSetChanged();
        }

    }
}
