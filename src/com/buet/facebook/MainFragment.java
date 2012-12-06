package com.buet.facebook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;



/**
 * Keypoint is onsessionstatechanged() method.
 * 
 */
public class MainFragment extends Fragment {

	private LoginButton authButton;
	public Button fqlButton ;
	
	Intent intenttolistmain;
	
	//FRIEND LIST WITH ID:
	private String f1 = "SELECT uid, name FROM user WHERE uid IN " +
            "(SELECT uid2 FROM friend WHERE uid1 = me())";
	//STATUS OF ALL FRIENDS:
	private String f2 = "select message from status where uid in(select uid2 from friend where uid1=me())";
	//STATUS OF SHAHAN
	private String f3 = "select message from status where uid=1076004498";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_main, container, false);

		authButton = (LoginButton) view.findViewById(R.id.authButton);
		
		authButton.setReadPermissions(Arrays.asList("friends_status","friends_status","friends_likes","read_stream"));

		authButton.setApplicationId(getString(R.string.app_id));
		
		fqlButton = (Button) view.findViewById(R.id.FQL);
		fqlButton.setVisibility(View.INVISIBLE);
		
		intenttolistmain=new Intent(getActivity().getBaseContext(),ListMain.class);

		return view;
		
	}
	
	/**
	 *1.  this method is called from MainActivity class.
	 *2. this method makes the fqlButton visible and sets a listener for that button.
	 *3. when button's listener is fired.
	 *	a. A single request to be sent to the Facebook Platform .
	 *	b. request is sent in the fql format.( a string)
	 *	c. and a response object is returned if every thing goes well including permissions and else.
	 * 	
	*/
	
	public void onSessionStateChange(SessionState state, Exception exception,Session session) {
	    if (state.isOpened()) {
	            // Logged In
	    	final Session mSession = session;
	    	
	    	fqlButton.setVisibility(View.VISIBLE);
	    	fqlButton.setOnClickListener(new OnClickListener() {
				
	        	 public void onClick(View v) {
	        		 
	        	        String fqlQuery = f1;
	        	        Bundle params = new Bundle();
	        	        params.putString("q", fqlQuery);
	        	        Request request = new Request(mSession,
	        	            "/fql",                         
	        	            params,                         
	        	            HttpMethod.GET,                 
	        	            new Request.Callback(){         
	        	                public void onCompleted(Response response) {
	        	                   
	        	                    
	        	                    intenttolistmain.putExtra("FriendList",response.toString());
	        	                    Log.d("response",response.toString());
	        	                    Log.d("25th char",""+response.toString().charAt(25));
	        	                    startActivity(intenttolistmain);
	        	                    
	        	                }                  
	        	        }); 
	        	        Request.executeBatchAsync(request);                 
	        	    }

				
			});
	    	
	    	
	    } else if (state.isClosed()) {
	            // Logged Out
	    }
	}

}
