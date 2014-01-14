package com.dizae;

import com.dizae.content.HomeFragment;
import com.dizae.content.MapFragment;
import com.dizae.content.RegisterFragment;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
    private TextView mapOption;
    private TextView userOption;

    //private CharSequence mDrawerTitle;
    //private CharSequence mTitle;    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        inicialize();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.sidebar);
        
        
    }
    
    private void changeFragment(Fragment fragment){
    	 
         Bundle args = new Bundle();         

         FragmentManager fragmentManager = getFragmentManager();
         fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

               
         mDrawerLayout.closeDrawer(mDrawerList);
    }
    
    
    
    private void inicialize(){
    	mapOption = (TextView) findViewById(R.id.sidebar_map_option);    	
    	mapOption.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				changeFragment(new MapFragment());
			}
		});
    	
    	userOption = (TextView) findViewById(R.id.sidebar_user_email);
    	userOption.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				changeFragment(new HomeFragment());
			}
		});
    	
    }

}
