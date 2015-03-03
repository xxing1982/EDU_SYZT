package cn.edu.bjtu.nourriture.fragments.moments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.nourriture.Analytics;
import cn.edu.bjtu.nourriture.Constants;
import cn.edu.bjtu.nourriture.activities.MainActivity;
import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.activities.recipe.RecipeMomentsActivity;
import cn.edu.bjtu.nourriture.adapters.MomentsAdapter;
import cn.edu.bjtu.nourriture.models.Consumer;
import cn.edu.bjtu.nourriture.models.Moment;
import cn.edu.bjtu.nourriture.services.NourritureAPI;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MomentsFragment extends Fragment implements AbsListView.OnItemClickListener {

    // To distingush, how to query "GET /moment" endpoint
    public enum MOMENTS_QUERY_TYPE{
        RECIPE, CONSUMER, FOLLOWED_BY, ALL
    }



    // --- PROPERTIES ---
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER  = "section_number";
    private static final String ARG_MOMENTS_QUERY_TYPE   = "moments_query";
    private static final String ARG_QUERY_STRING    = "query_string";

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The text view for possible "empty text"
     */
    private TextView emptyTextView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private MomentsAdapter mAdapter;

    /**
     * For data loaded from API
     */
    private ArrayList<Moment> myMoments = new ArrayList<>();

    private MOMENTS_QUERY_TYPE queryType;
    private String query;



    // --- CONSTRUCTOR ---
    public static MomentsFragment newInstance(int sectionNumber, MOMENTS_QUERY_TYPE queryType, String query) {
        MomentsFragment fragment = new MomentsFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(ARG_MOMENTS_QUERY_TYPE, queryType.ordinal());
        args.putString(ARG_QUERY_STRING, query);

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MomentsFragment() {
    }



    // --- FRAGMENT lifecycle methods ---
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            queryType = MOMENTS_QUERY_TYPE.values()[getArguments().getInt(ARG_MOMENTS_QUERY_TYPE)];
            query = getArguments().getString(ARG_QUERY_STRING);
        }

        setHasOptionsMenu(true);

        mAdapter = new MomentsAdapter(getActivity().getBaseContext(), myMoments);

        // Get tracker.
        Tracker t = ((Analytics) getActivity().getApplication()).getTracker(Analytics.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName(getActivity().getString(R.string.ga_moments_all_screen));
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        emptyTextView = (TextView) view.findViewById(android.R.id.empty);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Always fetch moments when comes to the foreground
        fetchMoments();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;

            if (activity.getClass() == MainActivity.class){
                // Tell the main activity that fragment has been attached (will change the title)
                ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
            }
            else if (activity.getClass() == RecipeMomentsActivity.class){
                //TODO:do I need to let activity know about something?
            }

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    // --- AdapterView.OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onMomentSelected(myMoments.get(position).getId());
        }
    }



    // --- ACTION BAR ---
    /**
     *  Inflate the menu resource into the given Menu to add each item to the action bar:
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.menu_moments, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_moment:
                openAddNewMomentFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    // --- HELPERs ---
    private void openAddNewMomentFragment() {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            // Calls the MainActivity, which then presents the NewMomentActivity
            mListener.onNewMomentSelected();
        }

    }

    private void showEmptyView(boolean showEmptyView){

        if (showEmptyView){
            emptyTextView.setText(getString(R.string.no_moments));
        }
        else {
            emptyTextView.setText("");
        }
    }



    // --- API calls ---
    private void fetchMoments() {

        // custom GSON parser http://stackoverflow.com/questions/18473011/retrofit-gson-serialize-date-from-json-string-into-java-util-date
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.NOURRITURE_PLATFORM_ANDROID_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        NourritureAPI api = restAdapter.create(NourritureAPI.class);

        if (queryType == MOMENTS_QUERY_TYPE.RECIPE){
            api.getMomentsForRecipe(query, new Callback<List<Moment>>() {
                @Override
                public void success(List<Moment> moments, Response response) {
                    handleSuccessRequest(moments, response);
                }

                @Override
                public void failure(RetrofitError error) {
                    handleFailureRequest(error);
                }
            });
        }
        else if (queryType == MOMENTS_QUERY_TYPE.FOLLOWED_BY){
            SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_CURRENT_PROFILE, 0); // 0 - for private mode
            //String username = pref.getString(Consumer.CONSUMER_USERNAME, "");
            String consumerID = pref.getString(Consumer.CONSUMER_ID, "");

            api.getMomentsFollowedBy(consumerID, new Callback<List<Moment>>() {
                @Override
                public void success(List<Moment> moments, Response response) {
                    handleSuccessRequest(moments, response);
                }

                @Override
                public void failure(RetrofitError error) {
                    handleFailureRequest(error);
                }
            });
        }
        else if (queryType == MOMENTS_QUERY_TYPE.ALL){
            api.getAllMoments(new Callback<List<Moment>>() {
                @Override
                public void success(List<Moment> moments, Response response) {
                    handleSuccessRequest(moments, response);
                }

                @Override
                public void failure(RetrofitError error) {
                    handleFailureRequest(error);
                }
            });
        }
    }

    private void handleSuccessRequest(List<Moment> moments, Response response){
        myMoments.clear();

        myMoments.addAll(moments);

        if (myMoments.size() == 0) {
            showEmptyView(true);
        }
        else {
            showEmptyView(false);
        }

        mAdapter.notifyDataSetChanged();    // Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself
    }

    private void handleFailureRequest(RetrofitError error){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.api_error, Toast.LENGTH_SHORT);
        toast.show();
    }



    // --- INTERFACE methods decleration ---
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onMomentSelected(String id);

        public void onNewMomentSelected();
    }

}