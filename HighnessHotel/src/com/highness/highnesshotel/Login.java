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
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	Button btn;
	EditText user,pass;
	TextView signup,forgotpass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btn=(Button)findViewById(R.id.login);
		user=(EditText)findViewById(R.id.uname);
		pass=(EditText)findViewById(R.id.pass);
		signup=(TextView)findViewById(R.id.newuser);
		forgotpass=(TextView)findViewById(R.id.forgotpass);
		
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
		
		jobj=jsonparser.makeHttpRequest(getResources().getString(R.string.login_url), "POST", par);
		Log.i("parser",jobj.toString());
		try {
			if(jobj.getBoolean("status")){
			session.createLoginSession(jobj.getString("fname"),jobj.getString("lname"),jobj.getString("email"),jobj.getString("userid"));
			//Intent interntDashboard = new Intent(getApplicationContext(),
				//	Dashboard.class);
			//interntDashboard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//interntDashboard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//startActivity(interntDashboard);
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
/*@Override
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
}*/
}