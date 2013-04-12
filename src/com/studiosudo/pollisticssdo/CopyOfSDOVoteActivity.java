/** Vote Activity Class
 * Displays the voter interface and sends votes to the server
 * @param none
 */
package com.studiosudo.pollisticssdo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class CopyOfSDOVoteActivity extends SDOPollActivity 
{

	private ProgressDialog downloadingDataDialog;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote);
		
		//question_textview = (TextView) findViewById(R.id.textViewTemperature);
		
		
	}

	private class executeVote extends AsyncTask<String, Integer, String> // <parameter types,  
																		 //progress type, return type>
	{
		@Override
		protected String doInBackground(String... uri) 
		{ 
		HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
        }
        
		@Override
	    protected void onPostExecute(String result) {
	       // super.onPostExecute(result);
	        
	        //updatingTemperature.setText("Voted"); // Not currently implemented
	   
          //  final String STARTTAG = "<h4>";
	      //  final String ENDTAG = "</h4>";
	        
	      //  int left = result.indexOf(STARTTAG);
	      //  int right = result.indexOf(ENDTAG, left);
	    //    String temperature = result.substring(left+STARTTAG.length(), right);
	      
	        downloadingDataDialog.dismiss();
	    //    temperatureDisplay.setText(Html.fromHtml(temperature));
	 //       temperatureIcon.setImageResource(R.drawable.mostlysunny);
	    }
    
} 	
		
	
	public void voteAClicked(View v){
		downloadingDataDialog = ProgressDialog.show(CopyOfSDOVoteActivity.this, "Pollistics", "Sending Answer A", true);
		new executeVote().execute(getString(R.string.http_string_vote_a));
	}
	
	public void voteBClicked(View v){
		downloadingDataDialog = ProgressDialog.show(CopyOfSDOVoteActivity.this, "Pollistics", "Sending Answer B", true);
		new executeVote().execute(getString(R.string.http_string_vote_a));
	}
	
	public void voteCClicked(View v){
		downloadingDataDialog = ProgressDialog.show(CopyOfSDOVoteActivity.this, "Pollistics", "Sending Answer C", true);
		new executeVote().execute(getString(R.string.http_string_vote_a));
	}
	
	public void voteDClicked(View v){
		downloadingDataDialog = ProgressDialog.show(CopyOfSDOVoteActivity.this, "Pollistics", "Sending Answer D", true);
		new executeVote().execute(getString(R.string.http_string_vote_a));
	}
	
	public void refreshButtonClicked(View v){

	}
	
	
	
	
	
}
