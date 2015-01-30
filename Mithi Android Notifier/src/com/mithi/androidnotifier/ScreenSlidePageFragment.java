package com.mithi.androidnotifier;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mithi.androidnotifier.R;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
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
    	// Inflate the layout containing a title and body text.
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

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}