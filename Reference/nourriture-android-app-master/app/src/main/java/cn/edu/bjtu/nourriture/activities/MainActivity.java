package cn.edu.bjtu.nourriture.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.activities.friend.NewFriendActivity;
import cn.edu.bjtu.nourriture.activities.moment.DetailMomentActivity;
import cn.edu.bjtu.nourriture.activities.moment.NewMomentActivity;
import cn.edu.bjtu.nourriture.activities.recipe.DetailRecipeActivity;
import cn.edu.bjtu.nourriture.fragments.NavigationDrawerFragment;
import cn.edu.bjtu.nourriture.fragments.friends.FriendsFragment;
import cn.edu.bjtu.nourriture.fragments.moments.MomentsFragment;
import cn.edu.bjtu.nourriture.fragments.profile.ConsumerFragment;
import cn.edu.bjtu.nourriture.fragments.recipes.RecipesFragment;
import cn.edu.bjtu.nourriture.models.Consumer;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        MomentsFragment.OnFragmentInteractionListener,
        RecipesFragment.OnFragmentInteractionListener,
        FriendsFragment.OnFragmentInteractionListener,
        ConsumerFragment.OnFragmentInteractionListener{



    // --- PROPERTIES ---
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    // SharedPreferences names
    public static final String SHARED_PREFERENCES_CURRENT_PROFILE   = "myProfile";  //used for Consumer info

    public static final String SHARED_PREFERENCES_CURRENT_SECTION   = "currentSection"; //used to maintain selected section in menu
    public static final String CURRENT_SECTION_ID                   = "currentSectionID";

    public static final String SHARED_PREFERENCES_MOMENT_DETAIL     = "myMomentDetail";
    public static final String CURRENT_MOMENT_ID                    = "myMomentDetailID";


    public static final String SHARED_PREFERENCES_RECIPE_DETAIL     = "myRecipeDetail";
    public static final String CURRENT_RECIPE_ID                    = "myRecipeDetailID";



    // --- ACTIVITY lifecycle methods ---
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the navigation drawer fragment
        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));

        // Save hardcoded consumer values into the SharedPreferences
        loggedInConsumer();

        // Read what is the last opened activity instance
        lastOpenedActivityInstance();
    }



    // --- Fragments INTERFACE methods implementation ---
    /**
     * from "NavigationDrawerFragment" FRAGMENT
     *
     * Implementation of method from NavigationDrawerFragment interface
     * Invoked on "selectItem(int position)" -> changes fragment
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position){
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MomentsFragment.newInstance(position + 1, MomentsFragment.MOMENTS_QUERY_TYPE.FOLLOWED_BY, ""))
                        .addToBackStack("MomentTag")
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, RecipesFragment.newInstance(position + 1))
                        .addToBackStack( "RecipeTag" )
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FriendsFragment.newInstance(position + 1))
                        .addToBackStack( "FriendTag" )
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ConsumerFragment.newInstance(position + 1))
                        .addToBackStack( "ConsumerTag" )
                        .commit();
                break;
            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .addToBackStack( "Placeholdertag" )
                        .commit();
                break;
        }
    }

    /**
     * from "MomentsFragment" FRAGMENT
     *
     * Implementation of method from MomentsFragment interface
     */
    @Override
    public void onMomentSelected(String id) {
        System.out.println("Moment " + id);

        SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCES_MOMENT_DETAIL, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit(); // used for save data
        editor.putString(CURRENT_MOMENT_ID, id); // Storing string value
        editor.commit(); // commit changes into sharedpreferences file.

        // Present the "Detail Moment" activity
        Intent intent = new Intent(MainActivity.this, DetailMomentActivity.class);
        startActivity(intent);

        // To persist state
        saveLastOpenedActivityInstance(0);
    }

    @Override
    public void onNewMomentSelected() {

        // Present the "New Moment" activity modaly (slide up)
        Intent intent_info = new Intent(MainActivity.this, NewMomentActivity.class);
        startActivity(intent_info);
        overridePendingTransition(R.anim.slide_up_animation,R.anim.no_change_animation);

        // To persist state
        saveLastOpenedActivityInstance(0);
    }

    /**
     * from "RecipesFragment" FRAGMENT
     *
     * Implementation of method from RecipesFragment interface
     */
    @Override
    public void onRecipeSelected(String id) {
        System.out.println("Recipe " + id);

        SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCES_RECIPE_DETAIL, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit(); // used for save data
        editor.putString(CURRENT_RECIPE_ID, id); // Storing string value
        editor.commit(); // commit changes into sharedpreferences file.

        // Present the "Detail Recipe" activity
        Intent intent = new Intent(MainActivity.this, DetailRecipeActivity.class);
        startActivity(intent);

        // To persist state
        saveLastOpenedActivityInstance(1);
    }

    /**
     * from "FriendsFragment" FRAGMENT
     *
     * Implementation of method from FriendsFragment interface
     */
    @Override
    public void onFriendSelected(String id) {
        System.out.println("Friend " + id);

        // To persist state
        // saveLastOpenedActivityInstance(2);
    }

    @Override
    public void onNewFriendSelected() {

        // Present the "New Moment" activity modaly (slide up)
        Intent intent_info = new Intent(MainActivity.this, NewFriendActivity.class);
        startActivity(intent_info);
        overridePendingTransition(R.anim.slide_up_animation,R.anim.no_change_animation);

        // To persist state
        saveLastOpenedActivityInstance(2);
    }

    /**
     * from "ConsumerFragment" FRAGMENT
     *
     * Implementation of method from ConsumerFragment interface
     */
    @Override
    public void onConsumerInteraction(String id) {
        System.out.println("yeah");
    }



    // --- OTHER methods ---
    /**
     * Invoked from FRAGMENTS upon "onAttach" method
     * Changes the Action Bar title
     */
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    private void loggedInConsumer() {

        //FIXME: hardcoded the currently logged in Consumer. Usually this would be verified with login API call
        SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCES_CURRENT_PROFILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit(); // used for save data

        /*editor.putString(Consumer.CONSUMER_ID, "54a688dc7048351b5d2972a3"); // Storing string value
        editor.putString(Consumer.CONSUMER_USERNAME, "ctverecek"); // Storing string value
        editor.putString(Consumer.CONSUMER_NAME, "Pavel Prochazka"); // Storing string value*/

        editor.putString(Consumer.CONSUMER_ID, "54a6893e7048351b5d2972a5"); // Storing string value
        editor.putString(Consumer.CONSUMER_USERNAME, "nielssj"); // Storing string value
        editor.putString(Consumer.CONSUMER_NAME, "Niels Jensen"); // Storing string value

        /*editor.putString(Consumer.CONSUMER_ID, "54a689007048351b5d2972a4"); // Storing string value
        editor.putString(Consumer.CONSUMER_USERNAME, "arnaudkevin"); // Storing string value
        editor.putString(Consumer.CONSUMER_NAME, "Arnaud Kevin"); // Storing string value*/

        editor.commit(); // commit changes into sharedpreferences file.
    }

    private void lastOpenedActivityInstance(){
        SharedPreferences pref = getSharedPreferences(MainActivity.SHARED_PREFERENCES_CURRENT_SECTION, MODE_PRIVATE); // 0 - for private mode
        int sectionID = pref.getInt(MainActivity.CURRENT_SECTION_ID, 0);    //default will be 0

        onNavigationDrawerItemSelected(sectionID);
    }

    private void saveLastOpenedActivityInstance(int number){
        SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCES_CURRENT_SECTION, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit(); // used for save data
        editor.putInt(CURRENT_SECTION_ID, number);
        editor.commit();
    }



    // --- ACTION BAR ---
    /**
     *  Update the Action bar!
     */
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
    To place the menu items into the action bar, implement the onCreateOptionsMenu() callback
    method in your activity to inflate the menu resource into the given Menu object.
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.

            //getMenuInflater().inflate(R.menu.main, menu);     TODO: may wanna uncomment in the future to provide this menu everywhere

            restoreActionBar(); //update Action bar
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /** Handle action bar item clicks here. The action bar will
    automatically handle clicks on the Home/Up button, so long
    as you specify a parent activity in AndroidManifest.xml. */
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



    // --- NESTED PLACEHOLDER FRAGMENT ---
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);    // set as arguments of this Fragment
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}