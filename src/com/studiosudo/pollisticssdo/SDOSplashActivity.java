/** Splash Activity Class  
 * Displays splash screen with animations
 * @param none
 */
package com.studiosudo.pollisticssdo;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.util.Log;  

public class SDOSplashActivity extends SDOPollActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		Log.i(APPTAG,"App Info: Entering Splash Activity OnCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		//TextView logo1 = (TextView) findViewById(R.id.textViewTopTitle);
		Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
	
		TextView logo2 = (TextView) findViewById(R.id.textViewBottomTitle);
		Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in_offset);
	
				
		fade2.setAnimationListener ( new AnimationListener()  
		{	
				@Override
				public void onAnimationEnd(Animation animation)
				{
							Log.i(APPTAG,"App Info: Splash Activity OnCreate, OnAnimationEnd, starting Start Vote Activity");
							startActivity(new Intent(SDOSplashActivity.this, SDOVoteActivity.class));// This line is failing.
							Log.i(APPTAG,"App Info: Splash Activity OnCreate, OnAnimationEnd, finished starting Start Vote Activity");
					SDOSplashActivity.this.finish();
							Log.i(APPTAG,"App Info: Splash Activity OnCreate, OnAnimationEnd, finished the finish() method.");
				}
				
				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub				
				}
				
				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub								
				}
		});	
		
		
		Animation spinin = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        LayoutAnimationController controller = new LayoutAnimationController(spinin);
        TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            row.setLayoutAnimation(controller);
        }
		//logo1.startAnimation(fade1);	
		logo2.startAnimation(fade2);
	}//end of onCreate method.
	
	@Override
	protected void onPause(){
	super.onPause();
	    	// stop our splash screen animation
	//TextView splashText = (TextView) findViewById(R.id.textViewTopTitle);
	    //	splashText.clearAnimation();	    	
	}
}
