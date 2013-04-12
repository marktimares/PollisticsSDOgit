package com.studiosudo.pollisticssdo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;



import android.R.id;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SDOVoteActivity extends Activity {
	Post testPost;
	int postToggle;
	Post p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_vote);
        p = new Post();
        p.execute();    
    }

  
    
    @Override
    protected void onPause() {
    	if (p != null && p.getStatus() != AsyncTask.Status.FINISHED) {
    		p.cancel(true);
    	} else {
    		p.cancel(true);
    	}
    }
    
    public void refreshQuestion(View v) {
    	p = new Post();
    	p.execute();
    }
    
    public void postAnswer(View v) {
    	Button tempButton = (Button) v;
    	String postValue = (String) tempButton.getHint();
    	p = new Post(postValue);
    	p.execute();
    }
   
private class Post extends AsyncTask<String, String, String> {
	String pollisticsURL = "http://www.pollistics.com/index.php";
    
    private Post(String answer) {
    	pollisticsURL += "?x=" + answer;
    	postToggle = 1;
    }
    
    private Post() {
    	postToggle = 0;
    }
    
    @Override
    protected void onPreExecute() {
    	SDOVoteActivity.this.setProgressBarIndeterminateVisibility(true);
    }
    
    @Override
    protected void onPostExecute(String result) {
    	//Log.i(DEBUG_TAG, "onPostExecute");
    	SDOVoteActivity.this.setProgressBarIndeterminateVisibility(false);
		TextView tv1 = (TextView) findViewById(R.id.responseField);
		TextView tv2 = (TextView) findViewById(R.id.questionField);
		//Button bt = (Button) findViewById(R.id.ButtonA);
		Button[] buttons = {(Button) findViewById(R.id.buttonA), (Button) findViewById(R.id.buttonB), 
							(Button) findViewById(R.id.buttonC), (Button) findViewById(R.id.buttonD)};
		
		TextView[] selectionsText  = {(TextView)findViewById(R.id.textViewA), (TextView)findViewById(R.id.textViewB),
				(TextView)findViewById(R.id.textViewC ), (TextView)findViewById(R.id.textViewD)};
		
		
		tv1.setVisibility(View.INVISIBLE);
    	if (postToggle == 1) {
    		//result = result.substring(result.indexOf("<h4>"), result.indexOf("</h4>") + 5);
    		tv1.setText("You voted!");
    		tv1.setVisibility(View.VISIBLE);
    	} else if (postToggle == 0) {
    		
    		
    		/*int right = 0;
    		int counter = 0;
    		int left;
    		char letter = 'A';
    		String firstTag = "<p>" + letter + ")";
    		while (result.indexOf(firstTag, right) != -1) {
    		left = result.indexOf(firstTag, right);
    		right = result.indexOf("</p>", left);
    		tv1.setText(result);
    		String choice = result.substring(left + 12, right - 4);
    		choice = Html.fromHtml(choice).toString().substring(0);
    		buttons[counter].setText(choice);
    		buttons[counter].setHint(String.valueOf(letter).toLowerCase());
    		counter++;
    		letter++;
    		firstTag = "<p>" + letter + ")";
    		}*/
    		
    		int left = result.indexOf("data.addRows([") + 14;
    		int right = result.indexOf("])", left);
    		String answerText = result.substring(left, right);
    		left = answerText.indexOf("[\"", 1);
    		right = answerText.indexOf("\"", left + 2);
    		char letter = 'a';
    		int counter = 0;
    		while (answerText.indexOf("\"", right) != -1 && left != -1) {

    			String choice = answerText.substring(left + 2, right);
    			selectionsText[counter].setText(choice);
    			buttons[counter].setHint(String.valueOf(letter));
    			counter++;
    			letter++;
    			left = answerText.indexOf("[\"", right + 1);
    			right = answerText.indexOf("\"", left + 2);
    		}
    		left = result.indexOf("<h3>Q");
    		right = result.indexOf("?</span></h3>", left);
    		result = result.substring(left, right + 6);
    		result = Html.fromHtml(result).toString();
    		result = result.substring(9);
    		tv2.setText(result);
    	}
    }
    
    @Override
    protected void onCancelled() {
    	SDOVoteActivity.this.setProgressBarIndeterminateVisibility(false);
    }

	@Override
	protected String doInBackground(String... answer) {
		
		String responseString = "";
    	
    	//TextView tv = (TextView) findViewById(R.id.textView1);
    	HttpResponse response = null;

    	

    	
    	try {
    		
        	HttpClient hc = new DefaultHttpClient();
        	HttpPost hp = new HttpPost(pollisticsURL);
    		
    	/*List<NameValuePair> nvp = new ArrayList<NameValuePair>(1);
    	nvp.add(new BasicNameValuePair("x","a"));
    	hp.setEntity(new UrlEncodedFormEntity(nvp));*/
    		
    	response = hc.execute(hp);
    	
    	StatusLine sl = response.getStatusLine();
    	//System.out.println("before response crap");
    	//String responseString = "";
    	if (sl.getStatusCode() == HttpStatus.SC_OK) {
    		ByteArrayOutputStream out = new ByteArrayOutputStream();
    		response.getEntity().writeTo(out);
    		out.close();
    		responseString = out.toString();
    		//System.out.println("testing" + responseString);
    	} else {
    		response.getEntity().getContent().close();
    		//responseString = "<p>A failed to load</p>";
            throw new IOException(sl.getReasonPhrase());
    	}

    		/*if (EntityUtils.toString(response.getEntity()) != null) {
    			String progress = EntityUtils.toString(response.getEntity());
    			System.err.println("before publish progress");
    			publishProgress("published!");
    			System.err.println("after publish progress");
    		} else {
    			publishProgress("didn't publish");
    		}*/
    	
    		
    	} catch (ClientProtocolException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

		return responseString;
	}
	
	@Override
	protected void onProgressUpdate(String... rawOutput) {
		/*if (rawOutput.length == 1) {
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Progress Update");
			tv.setText(rawOutput[0]);
		}*/
		
	}
}
    
}
