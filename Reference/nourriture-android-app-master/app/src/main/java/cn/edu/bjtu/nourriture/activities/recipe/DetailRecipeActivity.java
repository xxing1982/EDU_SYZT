package cn.edu.bjtu.nourriture.activities.recipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.bjtu.nourriture.Analytics;
import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.activities.MainActivity;
import cn.edu.bjtu.nourriture.models.Recipe;
import cn.edu.bjtu.nourriture.Constants;
import cn.edu.bjtu.nourriture.services.NourritureAPI;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class DetailRecipeActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{



    // --- PROPERTIES ---
    private String current_recipe_id;   //used for API call

    private RecipeAdapter adapter;

    private ArrayList<HashMap> currentRecipeDataToShow; //data source for our adapter

    private Recipe currentRecipe;   //data source used for social list of moments

    private ListView listView;



    // --- INTENTs EXTRAs ---
    public static final String INTENT_RECIPE_ID = "currentRecipeID";



    // --- ACTIVITY lifecycle methods ---
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        // Reading the recipe_id from SharedPreferences to avoid problems, when MomentsOfRecipeActivity dismissed
        SharedPreferences pref = getSharedPreferences(MainActivity.SHARED_PREFERENCES_RECIPE_DETAIL, MODE_PRIVATE); // 0 - for private mode
        current_recipe_id = pref.getString(MainActivity.CURRENT_RECIPE_ID, "");

        currentRecipeDataToShow = new ArrayList<>();

        currentRecipe = null;

        adapter = new RecipeAdapter();

        // can findViewById, because View already populated by setContentView
        listView = (ListView) findViewById(R.id.recipeDetailsListView);
        listView.setAdapter(adapter);

        /**
         * Register a callback to be invoked when an item in this AdapterView has
         * been clicked.
         *
         * Set OnItemClickListener so we can be notified on item clicks
         */
        listView.setOnItemClickListener(this);

        // Get tracker.
        Tracker t = ((Analytics) getApplication()).getTracker(Analytics.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName(getString(R.string.ga_recipe_detail_screen));
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public void onResume() {
        super.onResume();

        // Always fetch recipe when comes to the foreground
        fetchRecipeDetails();
    }



    // --- AdapterView.OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        /*HashMap item = currentMomentDataToShow.get(position);

        // User wants to see likes
        if (item.containsKey(Moment.MOMENT_LIKES) && !currentMoment.getLikes().isEmpty()){

            // Present the social list activity with "Likes"
            Intent intent = new Intent(DetailMomentActivity.this, SocialListOfMomentActivity.class);
            intent.putExtra(DETAILED_MOMENT_ACTIVITY_TITLE,  getString(R.string.listTypeLikes));
            intent.putExtra(DETAILED_MOMENT, currentMoment);
            startActivity(intent);
        }
        // User wants to see comments
        else if (item.containsKey(Moment.MOMENT_COMMENTS) && !currentMoment.getComments().isEmpty()){

            // Present the social list activity with "Comments"
            Intent intent = new Intent(DetailMomentActivity.this, SocialListOfMomentActivity.class);
            intent.putExtra(DETAILED_MOMENT_ACTIVITY_TITLE,  getString(R.string.listTypeComments));
            intent.putExtra(DETAILED_MOMENT, currentMoment);
            startActivity(intent);
        }*/
    }



    // --- ACTION BAR ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cook_recipe) {
            startCookRecipe();
            return true;
        }
        else if (id == R.id.action_recipe_moments){
            showRecipeMoments();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startCookRecipe() {
        Toast toast = Toast.makeText(getApplicationContext(), "Feature not implemented yet", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showRecipeMoments() {

        // Passing intentExtra -> used for distinguishing in MomentsOfRecipeActivity
        Intent intent = new Intent(DetailRecipeActivity.this, RecipeMomentsActivity.class);
        intent.putExtra(INTENT_RECIPE_ID, current_recipe_id);
        startActivity(intent);
    }



    // --- API calls ---
    private void fetchRecipeDetails() {
        // custom GSON parser http://stackoverflow.com/questions/18473011/retrofit-gson-serialize-date-from-json-string-into-java-util-date
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.NOURRITURE_PLATFORM_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        NourritureAPI api = restAdapter.create(NourritureAPI.class);
        api.getRecipe(current_recipe_id, new Callback<Recipe>() {
            @Override
            public void success(Recipe recipe, Response response) {
                currentRecipeDataToShow.clear();

                currentRecipeDataToShow.addAll(recipe.getRecipeInfoToDisplay());

                currentRecipe = recipe;

                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.api_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }



    // --- CUSTOM INNER CLASS of ArrayAdapter ---
    private class RecipeAdapter extends ArrayAdapter {

        // takes CONTEXT, LAYOUT and DATA
        public RecipeAdapter() {
            super(getBaseContext(), R.layout.row_consumer_title_and_value, currentRecipeDataToShow);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            View rowView = null;

            HashMap recipeInfo = currentRecipeDataToShow.get(position);

            // row with IMAGE
            if (recipeInfo.containsKey(Recipe.RECIPE_PICTURE)){
                rowView = inflater.inflate(R.layout.row_consumer_image, parent, false);

                ImageView imgView = (ImageView) rowView.findViewById(R.id.consumerImageView);

                String url = recipeInfo.get(Recipe.RECIPE_PICTURE).toString();
                Picasso.with(getContext())
                        .load(url)
                        .resize(160, 160)
                        .centerCrop()
                        .placeholder(R.drawable.image_placeholder)
                        .into(imgView);
            }
            // row with HEADER
            else if (recipeInfo.containsKey(Recipe.RECIPE_RECIPE_HEADER)){
                rowView = inflater.inflate(R.layout.group_header_item, parent, false);

                TextView titleView = (TextView) rowView.findViewById(R.id.header);
                titleView.setText(recipeInfo.get(Recipe.RECIPE_RECIPE_HEADER).toString());
            }
            // row with TEXT only
            else if (recipeInfo.containsKey(Recipe.RECIPE_INGREDIENSTS)){
                rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

                TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
                textView.setText(recipeInfo.get(Recipe.RECIPE_INGREDIENSTS).toString());
            }
            // row with TITLE and VALUE
            else {
                rowView = inflater.inflate(R.layout.row_consumer_title_and_value, parent, false);

                TextView titleTextView = (TextView) rowView.findViewById(R.id.titleTextView);
                TextView valueTextView = (TextView) rowView.findViewById(R.id.valueTextView);

                if (recipeInfo.containsKey(Recipe.RECIPE_TITLE)){
                    titleTextView.setText(R.string.recipeDetailName);
                    valueTextView.setText(recipeInfo.get(Recipe.RECIPE_TITLE).toString());
                }
                else if (recipeInfo.containsKey(Recipe.RECIPE_DESCRIPTION)){
                    titleTextView.setText(R.string.recipeDetailDescription);
                    valueTextView.setText(recipeInfo.get(Recipe.RECIPE_DESCRIPTION).toString());
                }
                else if (recipeInfo.containsKey(Recipe.RECIPE_DIFFICULTY)){
                    titleTextView.setText(R.string.recipeDetailDifficulty);
                    valueTextView.setText(recipeInfo.get(Recipe.RECIPE_DIFFICULTY).toString());
                }
                else if (recipeInfo.containsKey(Recipe.RECIPE_CALORIES)){
                    titleTextView.setText(R.string.recipeDetailCalories);
                    valueTextView.setText(recipeInfo.get(Recipe.RECIPE_CALORIES).toString());
                }
                else if (recipeInfo.containsKey(Recipe.RECIPE_CARBS)){
                    titleTextView.setText(R.string.recipeDetailCarbs);
                    valueTextView.setText(recipeInfo.get(Recipe.RECIPE_CARBS).toString());
                }
                else if (recipeInfo.containsKey(Recipe.RECIPE_PROTEINS)){
                    titleTextView.setText(R.string.recipeDetailProteins);
                    valueTextView.setText(recipeInfo.get(Recipe.RECIPE_PROTEINS).toString());
                }
                else if (recipeInfo.containsKey(Recipe.RECIPE_FATS)){
                    titleTextView.setText(R.string.recipeDetailFats);
                    valueTextView.setText(recipeInfo.get(Recipe.RECIPE_FATS).toString());
                }
            }

            return rowView;
        }
    }
}
