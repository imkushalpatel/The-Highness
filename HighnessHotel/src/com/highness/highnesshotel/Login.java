package com.highness.highnesshotel;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;




import helper.JSONParser;
import helper.SessionManager;
import helper.ValidationMethod;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
				if(check()){
					
				
				
						new LoginCheck().execute();
				
				}
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
	public boolean check(){
		if ((ValidationMethod.checkEmpty(user))
				&& (ValidationMethod.checkEmpty(pass)) != true) {

			Toast.makeText(getBaseContext(), "Please fill up all details",
					Toast.LENGTH_LONG).show();
		} else {
			if (ValidationMethod.checkEmail(user.getText().toString().trim()) != true) {
				Toast.makeText(getBaseContext(), "Invalid Email id",
						Toast.LENGTH_LONG).show();
			} else {
				return true;
			}
			}
		
		
		return false;
		
	}

private class LoginCheck extends AsyncTask<Void, Void, Void>
{

	ProgressDialog progress;
	JSONParser jsonparser =new JSONParser();
	JSONObject jobj;
	SessionManager session=new SessionManager(Login.this);
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
		progress.dismiss();
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		List<NameValuePair> par= new ArrayList<NameValuePair>();
		par.add(new BasicNameValuePair("user", user.getText().toString()));
		par.add(new BasicNameValuePair("pass", pass.getText().toString()));
		String url="http://192.168.2.33/mobile/login.php";
		jobj=jsonparser.makeHttpRequest(url, "POST", par);
		Log.i("parser",jobj.toString());
		try {
			if(jobj.getBoolean("status")){
			session.createLoginSession(jobj.getString("fname"),jobj.getString("lname"),jobj.getString("email"),jobj.getString("userid"));
			Intent interntDashboard = new Intent(getApplicationContext(),
					Dashboard.class);
			interntDashboard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			interntDashboard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(interntDashboard);
			finish();
			}
			else
			{
				Login.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								Login.this);
						builder.setTitle("Login Error.");
						builder.setMessage("User not Found.")
								.setCancelable(false)
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,
													int id) {
											}
										});
						AlertDialog alert = builder.create();
						alert.show();
					}
				});
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
@Override
public void onBackPressed() {
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setTitle("Exit");
	builder.setMessage("Are You Sure?");

	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
			finish();
		}
	});

	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	});
	AlertDialog alert = builder.create();
	alert.show();
}
}