package com.buet.facebook;

import android.os.Bundle;
import android.view.Menu;
import com.facebook.FacebookActivity;
import com.facebook.Session;
import com.facebook.SessionState;

/**
 * 1. this class is the activity class and hence main ui thread class.
 * 2. without setting its own layout directly as we do in other activity classes, we set here layout through a fragment class :MainFragment.java
 * 3. when logged in/out callback method onsessionstatechanged is fired.
 * 4. Fragment class controls 3 things
 * 		a. layout.
 * 		b. login authentication and permissions required.
 * 		c. a button named fqlButton whose onclicklistener fires the request with fql query.
 * 5. we call onsessionstatechanged method of MainFragment class through this class's similar callback method.
 * 
*/
public class MainActivity extends FacebookActivity {

	private MainFragment mainFragment;
	private boolean isResumed = false;
	public Session session;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			mainFragment = new MainFragment();
			
			
			
			getSupportFragmentManager()
			.beginTransaction()
			.add(android.R.id.content, mainFragment)
			.commit();
		} else {
			// Or set the fragment from restored state info
			mainFragment = (MainFragment) getSupportFragmentManager()
					.findFragmentById(android.R.id.content);
		}

		//setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onSessionStateChange(SessionState state, Exception exception) {
	    super.onSessionStateChange(state, exception);
	    if (isResumed) {
	    	session = Session.getActiveSession();
	        mainFragment.onSessionStateChange(state, exception,Session.getActiveSession());
	    }
	}
	
	
	@Override
	protected void onResumeFragments() {
	    super.onResumeFragments();

	    Session session = Session.getActiveSession();
	    if (session != null &&
	            (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session.getState(), null);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    isResumed = true;
	}

	@Override
	public void onPause() {
	    super.onPause();
	    isResumed = false;
	}

}
