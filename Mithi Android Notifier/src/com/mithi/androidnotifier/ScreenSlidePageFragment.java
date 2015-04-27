package com.mithi.androidnotifier;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ScreenSlidePageFragment extends Fragment {

    public static final String ARG_PAGE = "page";

    private int mPageNumber;

    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView;
        int pg=getPageNumber();
        
        rootView = (ViewGroup) inflater
                .inflate(R.layout.welcome1, container, false);
        
    	switch(pg)
    	{
    	case 0:
    		rootView = (ViewGroup) inflater
                .inflate(R.layout.welcome1, container, false);
        	return rootView;
    	
    	case 1:
    		 rootView = (ViewGroup) inflater
             .inflate(R.layout.welcome2, container, false);
    		 return rootView;
    		 
    		 
    	case 2:
    		rootView = (ViewGroup) inflater
            .inflate(R.layout.welcome3, container, false);
   		 return rootView;
   		
    	case 3:
    		rootView = (ViewGroup) inflater
            .inflate(R.layout.welcome4, container, false);
   		 return rootView;
   		 
    	case 4:
    		rootView = (ViewGroup) inflater
            .inflate(R.layout.welcome5, container, false);
   		 return rootView;
    		
        // Set the title view to show the page number.
       // ((TextView) rootView.findViewById(android.R.id.text1)).setText(getString(R.string.title_template_step, mPageNumber + 1));
    	}

    	return rootView;
    }


    public int getPageNumber() {
        return mPageNumber;
    }
}
