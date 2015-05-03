package com.highness.highnesshotel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import helper.JSONParser;
import helper.ValidationMethod;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUp extends Activity {
	EditText etfname, etlname, etemail, etpass, etrepass, etaddress, etmobile;
	Button btnsignup;
	RadioGroup radiogender;
	JSONParser jsonparser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		etfname = (EditText) findViewById(R.id.fname);
		etlname = (EditText) findViewById(R.id.lname);
		etemail = (EditText) findViewById(R.id.email);
		etpass = (EditText) findViewById(R.id.pass);
		etrepass = (EditText) findViewById(R.id.repass);
		etmobile = (EditText) findViewById(R.id.phone);
		etaddress = (EditText) findViewById(R.id.address);
		btnsignup = (Button) findViewById(R.id.signup);
		radiogender = (RadioGroup) findViewById(R.id.gender);
		btnsignup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (check()) {
					new signup().execute();
				}

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == android.R.id.home) {

			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	boolean check() {
		if (ValidationMethod.checkEmpty(etfname)) {
			Toast.makeText(getBaseContext(), "Please fill up First Name",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etlname)) {
			Toast.makeText(getBaseContext(), "Please fill up Last Name",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etemail)) {
			Toast.makeText(getBaseContext(), "Please fill up Email",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etpass)) {
			Toast.makeText(getBaseContext(), "Please fill up Password",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etrepass)) {
			Toast.makeText(getBaseContext(), "Please fill up Retype Password",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etmobile)) {
			Toast.makeText(getBaseContext(), "Please fill up Mobile No",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etaddress)) {
			Toast.makeText(getBaseContext(), "Please fill up Address",
					Toast.LENGTH_LONG).show();
		} else if (!etpass.getText().toString()
				.equals(etrepass.getText().toString())) {
			Toast.makeText(getBaseContext(), "Both Password Does not Match ",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmail(etemail) != true) {
			Toast.makeText(getBaseContext(), "Please fill Valid Email",
					Toast.LENGTH_LONG).show();
		} else {
			try {
				if (new validateemail().execute().get()) {
					return true;
				}
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	private class signup extends AsyncTask<Void, Void, Void> {
		ProgressDialog progress;
		JSONObject object;
		Boolean status = false;

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> par = new ArrayList<NameValuePair>();
			par.add(new BasicNameValuePair("fname", etfname.getText()
					.toString()));
			par.add(new BasicNameValuePair("lname", etlname.getText()
					.toString()));
			par.add(new BasicNameValuePair("email", etemail.getText()
					.toString()));
			par.add(new BasicNameValuePair("pass", etpass.getText().toString()));
			par.add(new BasicNameValuePair("mobile", etmobile.getText()
					.toString()));
			par.add(new BasicNameValuePair("address", etaddress.getText()
					.toString()));
			RadioButton rb = (RadioButton) findViewById(radiogender
					.getCheckedRadioButtonId());
			par.add(new BasicNameValuePair("gender", rb.getText().toString()
					.toLowerCase()));
			object = jsonparser.makeHttpRequest(
					getResources().getString(R.string.signup_url), "POST", par);
			try {
				if (object.getBoolean("status")) {
					status = true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress = new ProgressDialog(SignUp.this);
			progress.setMessage("Please Wait..");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progress.dismiss();
			if (status) {
				Toast.makeText(getBaseContext(),
						"Signup Successful. Please Login", Toast.LENGTH_LONG)
						.show();
				finish();
			}
		}

	}

	private class validateemail extends AsyncTask<Void, Void, Boolean> {
		ProgressDialog progress;
		JSONObject object;

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> par = new ArrayList<NameValuePair>();
			par.add(new BasicNameValuePair("email", etemail.getText()
					.toString()));
			object = jsonparser.makeHttpRequest(
					getResources().getString(R.string.signup_email_url),
					"POST", par);

			try {
				if (object.getBoolean("status")) {
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress = new ProgressDialog(SignUp.this);
			progress.setMessage("Validating Email..");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progress.dismiss();
			if (result == false) {
				Toast.makeText(getBaseContext(), "Email Already Exists",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
