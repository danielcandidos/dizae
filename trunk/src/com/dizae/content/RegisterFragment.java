package com.dizae.content;


import com.dizae.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RegisterFragment extends Fragment{
	
public RegisterFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_user_register, container, false);    
	    
	    
	    getActivity().setTitle("Login");
	    return rootView;
	}

}