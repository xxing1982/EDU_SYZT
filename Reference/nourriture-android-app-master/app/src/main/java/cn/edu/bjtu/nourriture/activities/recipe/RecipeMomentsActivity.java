package cn.edu.bjtu.nourriture.activities.recipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import cn.edu.bjtu.nourriture.Analytics;
import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.activities.MainActivity;
import cn.edu.bjtu.nourriture.activities.moment.NewMomentActivity;
import cn.edu.bjtu.nourriture.fragments.moments.MomentsFragment;

public class RecipeMomentsActivity extends ActionBarActivity implements MomentsFragment.OnFragmentInteractionListener {



    // --- PROPERTIES ---
    private String recipeID;



    // --- ACTIVITY lifecycle methods ---
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_moments);

        //coming from the DetailRecipeActivity, which passes intentExtra
        if (getIntent().getStringExtra(DetailRecipeActivity.INTENT_RECIPE_ID) != null){
            recipeID = getIntent().getStringExtra(DetailRecipeActivity.INTENT_RECIPE_ID);
        }
        else {  //coming from the NewMomentActivity, which cannot pass anything (got here by pressing UP button) so have to read from SharedPreferences
            SharedPreferences pref = getSharedPreferences(MainActivity.SHARED_PREFERENCES_RECIPE_DETAIL, MODE_PRIVATE);
            recipeID = pref.getString(MainActivity.CURRENT_RECIPE_ID, "");
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MomentsFragment.newInstance(101, MomentsFragment.MOMENTS_QUERY_TYPE.RECIPE, recipeID))
                    .commit();
        }

        // Get tracker.
        Tracker t = ((Analytics) getApplication()).getTracker(Analytics.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName(getString(R.string.ga_recipe_moments_screen));
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }



    // --- ACTION BAR ---
    // Will get overwritten by the MomentsFragment anyways
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_moments, menu);
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
    }*/



    // --- Fragments INTERFACE methods implementation ---
    @Override
    public void onMomentSelected(String id) {
        System.out.println(id);
        //TODO: probably should show moment detail...
    }

    @Override
    public void onNewMomentSelected() {

        // Present the "New Moment" activity modaly (slide up)
        Intent intent_info = new Intent(RecipeMomentsActivity.this, NewMomentActivity.class);
        intent_info.putExtra(DetailRecipeActivity.INTENT_RECIPE_ID, recipeID);
        startActivity(intent_info);
        overridePendingTransition(R.anim.slide_up_animation,R.anim.no_change_animation);
    }
}
