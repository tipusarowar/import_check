package com.buet.facebook;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ListMain extends ListActivity {
	
	class Friend
	{
		String uid;
		String name;
	}
	
	JSONObject json;
	JSONArray jsonarray=null;
	List<Friend> FriendList =null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        Intent intent = getIntent();
        // getting attached intent data
        String friendlist = intent.getStringExtra("FriendList");
        try {
			json=new JSONObject(friendlist);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("Error json",e.getMessage());
		}
        
        try {
            // Getting Array of Contacts
            jsonarray = json.optJSONArray("data");
         
            // looping through All Contacts
            for(int i = 0; i < jsonarray.length(); i++){
                JSONObject c = jsonarray.getJSONObject(i);
                //Friend temp=new Friend();
         
                // Storing each json item in variable
                //temp.uid = c.optString("uid");
                //temp.name = c.optString("name");
                
               // FriendList.add(temp);
                
                Log.d("temp.uid","temp.main");
         
            }
        } catch (JSONException e) {
        	Log.d("Error jsonarray",e.getMessage());
        }
        
       //Log.d("FriendList",friendlist);
        
        
    }
}