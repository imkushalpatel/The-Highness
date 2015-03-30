package com.highness.highnesshotel;

import java.util.List;

import org.apache.http.NameValuePair;

import helper.JSONParser;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	Button btn;
	EditText user,pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btn=(Button)findViewById(R.id.login);
		user=(EditText)findViewById(R.id.et_username);
		pass=(EditText)findViewById(R.id.et_password);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new LoginCheck().execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

private class LoginCheck extends AsyncTask<Void, Void, Void>
{

	ProgressDialog progress;
	JSONParser jsonparser =new JSONParser();
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progress= new ProgressDialog(Login.this);
		progress.setMessage("Validating User...");
		progress.setIndeterminate(false);
		progress.setCancelable(false);
		progress.show();
		
	}

	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progress.cancel();
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		//List<NameValuePair> param=
		return null;
	}
	
}
}