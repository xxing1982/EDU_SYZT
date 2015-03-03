package cn.edu.bjtu.nourriture.fragments.friends;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import cn.edu.bjtu.nourriture.activities.MainActivity;
import cn.edu.bjtu.nourriture.R;

import cn.edu.bjtu.nourriture.adapters.ConsumersAdapter;
import cn.edu.bjtu.nourriture.models.Consumer;
import cn.edu.bjtu.nourriture.Constants;
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
 public class FriendsFragment extends Fragment implements AbsListView.OnItemLongClickListener {



     // --- PROPERTIES ---
     /**
     * The fragment argument representing the section number for this
     * fragment.
     */
     private static final String ARG_SECTION_NUMBER = "section_number";

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
     private ConsumersAdapter mAdapter;

     /**
      * For data loaded from API
      */
     private ArrayList<Consumer> myConsumers = new ArrayList<>();



     // --- CONSTRUCTOR ---
     public static FriendsFragment newInstance(int sectionNumber) {
         FriendsFragment fragment = new FriendsFragment();
         Bundle args = new Bundle();
         args.putInt(ARG_SECTION_NUMBER, sectionNumber);
         fragment.setArguments(args);
         return fragment;
     }

     /**
      * Mandatory empty constructor for the fragment manager to instantiate the
      * fragment (e.g. upon screen orientation changes).
      */
     public FriendsFragment() {
     }



    // --- FRAGMENT lifecycle methods ---
     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         setHasOptionsMenu(true);

         mAdapter = new ConsumersAdapter(getActivity().getBaseContext(), myConsumers);

         // Get tracker.
         Tracker t = ((Analytics) getActivity().getApplication()).getTracker(Analytics.TrackerName.APP_TRACKER);
         // Set screen name.
         t.setScreenName(getString(R.string.ga_friends_all_screen));
         // Send a screen view.
         t.send(new HitBuilders.AppViewBuilder().build());
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_friends, container, false);

         emptyTextView = (TextView) view.findViewById(android.R.id.empty);

         // Set the adapter
         mListView = (AbsListView) view.findViewById(android.R.id.list);
         ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

         // Set OnItemLongClickListener so we can be notified on item long clicks
         mListView.setOnItemLongClickListener(this);

         return view;
     }

    @Override
    public void onResume() {
        super.onResume();

        // Always fetch consumers when comes to the foreground
        fetchConsumersFollowing();
    }

     @Override
     public void onAttach(Activity activity) {
         super.onAttach(activity);
         try {
             mListener = (OnFragmentInteractionListener) activity;

             // Tell the main activity that fragment has been attached (will change the title)
             ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
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



     // --- EVENT LISTENERS ---
     /*@Override
     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         //TODO: show consumer's profile?
         if (null != mListener) {
             // Notify the active callbacks interface (the activity, if the
             // fragment is attached to one) that an item has been selected.
             //mListener.onFriendSelected(DummyContent.FRIENDS.get(position).id);
         }
     }*/
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position);

        final Consumer c = myConsumers.get(position);

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(getString(R.string.message_remove_friend) + " " + c.getName() + "?")
                .setTitle(getString(R.string.title_remove_friend))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        stopToFollowConsumer(c);
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
    /**
     *  Inflate the menu resource into the given Menu to add each item to the action bar:
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.menu_friends, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_friend:
                openNewFriendActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    // --- HELPERs ---
    private void openNewFriendActivity() {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            // Calls the MainActivity, which then presents the NewMomentActivity
            mListener.onNewFriendSelected();
        }

    }

    private void showEmptyView(boolean showEmptyView){

        if (showEmptyView){
            emptyTextView.setText(getString(R.string.no_friends));
        }
        else {
            emptyTextView.setText("");
        }
    }



    // --- API calls ---
    private void fetchConsumersFollowing() {

        // custom GSON parser http://stackoverflow.com/questions/18473011/retrofit-gson-serialize-date-from-json-string-into-java-util-date
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.NOURRITURE_PLATFORM_ANDROID_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_CURRENT_PROFILE, 0); // 0 - for private mode
        String username = pref.getString(Consumer.CONSUMER_USERNAME, "");

        NourritureAPI api = restAdapter.create(NourritureAPI.class);
        api.getConsumerFollowing(username, new Callback<List<Consumer>>() {
            @Override
            public void success(List<Consumer> consumers, Response response) {

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
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.api_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void stopToFollowConsumer(Consumer consumerToDelete){
        SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_CURRENT_PROFILE, 0); // 0 - for private mode
        String consumerID = pref.getString(Consumer.CONSUMER_ID, "");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.NOURRITURE_PLATFORM_ANDROID_URL)
                .build();

        NourritureAPI api = restAdapter.create(NourritureAPI.class);
        api.deleteConsumerFollowing(consumerID, consumerToDelete.getcId(), new Callback<Consumer>() {
            @Override
            public void success(Consumer consumer, Response response) {
                fetchConsumersFollowing();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast toast = Toast.makeText(getActivity(), R.string.api_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
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
         public void onFriendSelected(String id);
         public void onNewFriendSelected();
     }
 }