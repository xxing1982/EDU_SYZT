package cn.edu.bjtu.nourriture.activities.moment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.edu.bjtu.nourriture.Analytics;
import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.activities.MainActivity;
import cn.edu.bjtu.nourriture.activities.recipe.DetailRecipeActivity;
import cn.edu.bjtu.nourriture.activities.recipe.RecipeMomentsActivity;
import cn.edu.bjtu.nourriture.models.Author;
import cn.edu.bjtu.nourriture.models.Consumer;
import cn.edu.bjtu.nourriture.models.Moment;
import cn.edu.bjtu.nourriture.Constants;
import cn.edu.bjtu.nourriture.services.NourritureAPI;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class NewMomentActivity extends ActionBarActivity {



    // --- PROPERTIES ---
    private String recipeIDtoPOST;



    // --- ACTIVITY lifecycle methods ---
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_moment);

        //will be NULL if coming from the MainActivity
        //will be an ID if coming from the MomentsOfRecipeActivity (WANT TO POST MOMENT ABOUT a SPECIFIC RECIPE)
        recipeIDtoPOST = getIntent().getStringExtra(DetailRecipeActivity.INTENT_RECIPE_ID);

        // Get tracker.
        Tracker t = ((Analytics) getApplication()).getTracker(Analytics.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName(getString(R.string.ga_moment_new_screen));
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }



    // --- ACTION BAR ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_moment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_moment) {
            postMoment();
            return true;
        }
        else if (id == R.id.action_cancel_moment){
            discardMoment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // --- Moment handlers ---
    private void postMoment() {
        if (isMomentReady()){
            // 1) create a moment object
            EditText t = (EditText) findViewById(R.id.new_moment_edit_text);

            SharedPreferences pref = getSharedPreferences(MainActivity.SHARED_PREFERENCES_CURRENT_PROFILE, 0); // 0 - for private mode
            String consumerID = pref.getString(Consumer.CONSUMER_ID, "");
            String consumerName = pref.getString(Consumer.CONSUMER_NAME, "");

            Author a = new Author();
            a.setCId(consumerID);
            a.setName(consumerName);

            Moment m = new Moment();
            m.setAuthor(a);
            m.setText(t.getText().toString());

            // In case of posting a moment about specific recipe
            if (recipeIDtoPOST != null && !recipeIDtoPOST.isEmpty()){
                m.setSubjectID(recipeIDtoPOST);
            }

            // 2) POST request to API
            // custom GSON parser http://stackoverflow.com/questions/18473011/retrofit-gson-serialize-date-from-json-string-into-java-util-date
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Constants.NOURRITURE_PLATFORM_ANDROID_URL)
                    .setConverter(new GsonConverter(gson))
                    .build();

            NourritureAPI api = restAdapter.create(NourritureAPI.class);
            api.postMoment(m, new Callback<Moment>() {
                @Override
                public void success(Moment moment, Response response) {

                    Toast toast = Toast.makeText(getApplicationContext(), R.string.moment_posted, Toast.LENGTH_SHORT);
                    toast.show();

                    discardMoment();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.api_error, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.moment_text_missing, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void discardMoment() {

        Intent intent = null;
        if (recipeIDtoPOST != null){
            // Present the "MomentsOfRecipe" activity modaly (slide down)
            intent = new Intent(getApplicationContext(), RecipeMomentsActivity.class);
        }
        else {
            // Present the "Main" activity modaly (slide down)
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.no_change_animation, R.anim.slide_down_animation);
    }

    private boolean isMomentReady(){
        EditText t = (EditText) findViewById(R.id.new_moment_edit_text);

        if (t.getText().toString().isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }
}