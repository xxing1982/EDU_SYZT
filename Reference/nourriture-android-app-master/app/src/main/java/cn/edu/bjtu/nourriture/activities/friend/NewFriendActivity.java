package cn.edu.bjtu.nourriture.activities.friend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.nourriture.Analytics;
import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.activities.MainActivity;
import cn.edu.bjtu.nourriture.adapters.ConsumersAdapter;
import cn.edu.bjtu.nourriture.models.Consumer;
import cn.edu.bjtu.nourriture.Constants;
import cn.edu.bjtu.nourriture.services.NourritureAPI;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class NewFriendActivity extends ActionBarActivity implements ListView.OnItemLongClickListener{



    // --- PROPERTIES ---
    private ConsumersAdapter mAdapter;

    private TextView emptyTextView;

    private AbsListView listView;

    private ArrayList<Consumer> myConsumers = new ArrayList<>();    //For data loaded from API



    // --- ACTIVITY lifecycle methods ---
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        emptyTextView = (TextView) findViewById(android.R.id.empty);

        mAdapter = new ConsumersAdapter(getBaseContext(), myConsumers);

        // can findViewById, because View already populated by setContentView
        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        //listView.setOnItemClickListener(this);

        // Set OnItemLongClickListener so we can be notified on item long clicks
        listView.setOnItemLongClickListener(this);

        // Get tracker.
        Tracker t = ((Analytics) getApplication()).getTracker(Analytics.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName(getString(R.string.ga_friend_new_screen));
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Fetch all consumers
        fetchAllConsumers();
    }



    // --- EVENT LISTENERS ---
    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }*/

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position);

        final Consumer c = myConsumers.get(position);

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(getString(R.string.message_add_friend) + " " + c.getName() + "?")
                .setTitle(getString(R.string.title_add_friend))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        startToFollowConsumer(c);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }



    // --- ACTION BAR ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel_friend) {
            discardFriend();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    // --- HANDLERS ---
    private void discardFriend() {

        // Present the "Main" activity modaly (slide down)
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.no_change_animation, R.anim.slide_down_animation);
    }

    private void showEmptyView(boolean showEmptyView){

        if (showEmptyView){
            emptyTextView.setText(getString(R.string.no_consumers));
        }
        else {
            emptyTextView.setText("");
        }
    }



    // --- API calls ---
    private void fetchAllConsumers() {

        // custom GSON parser http://stackoverflow.com/questions/18473011/retrofit-gson-serialize-date-from-json-string-into-java-util-date
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.NOURRITURE_PLATFORM_ANDROID_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        NourritureAPI api = restAdapter.create(NourritureAPI.class);
        api.getAllConsumers(new Callback<List<Consumer>>() {
            @Override
            public void success(List<Consumer> consumers, Response response) {

                SharedPreferences pref = getSharedPreferences(MainActivity.SHARED_PREFERENCES_CURRENT_PROFILE, 0); // 0 - for private mode
                String consumerID = pref.getString(Consumer.CONSUMER_ID, "");
                for (Consumer c:consumers) {
                    if (consumerID.equals(c.getId())) {
                        consumers.remove(c);
                    }
                }

                myConsumers.clear();

                myConsumers.addAll(consumers);

                if (myConsumers.size() == 0) {
                    showEmptyView(true);
                } else {
                    showEmptyView(false);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast toast = Toast.makeText(getBaseContext(), R.string.api_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void startToFollowConsumer(Consumer consumerToFollow){
        SharedPreferences pref = getSharedPreferences(MainActivity.SHARED_PREFERENCES_CURRENT_PROFILE, 0); // 0 - for private mode
        String consumerID = pref.getString(Consumer.CONSUMER_ID, "");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.NOURRITURE_PLATFORM_ANDROID_URL)
                .build();

        NourritureAPI api = restAdapter.create(NourritureAPI.class);
        api.postConsumerFollowing(consumerID, consumerToFollow.getId(), new Callback<Consumer>() {
            @Override
            public void success(Consumer consumer, Response response) {
                // if this button is clicked, close
                // current activity
                NewFriendActivity.this.finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast toast = Toast.makeText(getBaseContext(), R.string.api_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}