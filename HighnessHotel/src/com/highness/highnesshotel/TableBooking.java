package com.highness.highnesshotel;

import helper.JSONParser;
import helper.SessionManager;
import helper.ValidationMethod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TableBooking extends Activity {
	EditText etdate, ettime, etperson;
	Button btnbook;
	Calendar calendar;
	SessionManager sessionmanager;
	JSONParser jsonparser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_table_booking);
		calendar = Calendar.getInstance();
		sessionmanager = new SessionManager(getApplicationContext());
		jsonparser = new JSONParser();
		etdate = (EditText) findViewById(R.id.tbldate);
		ettime = (EditText) findViewById(R.id.tbltime);
		etperson = (EditText) findViewById(R.id.tblpersonnum);
		btnbook = (Button) findViewById(R.id.tblbuk);
		etdate.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.onTouchEvent(event);
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return true;
			}
		});
		ettime.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.onTouchEvent(event);
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return true;
			}
		});
		etdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dpd = new DatePickerDialog(TableBooking.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								etdate.setText(dayOfMonth + "-"
										+ (monthOfYear + 1) + "-" + year);

							}
						}, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH));
				dpd.show();
			}
		});
		ettime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TimePickerDialog tpd = new TimePickerDialog(TableBooking.this,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								// TODO Auto-generated method stub
								ettime.setText(hourOfDay + ":" + minute);
							}
						}, calendar.HOUR_OF_DAY, calendar.MINUTE, true);
				tpd.show();

			}
		});
		btnbook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (check()) {
					if (sessionmanager.isLoggedIn()) {

						show_dialog();
					} else {
						Toast.makeText(getBaseContext(),
								"You must have to Logged In", Toast.LENGTH_LONG)
								.show();
						Intent loginintent = new Intent(
								getApplicationContext(), Login.class);
						startActivity(loginintent);
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		SessionManager session = new SessionManager(getApplicationContext());
		if (session.isLoggedIn())
			getMenuInflater().inflate(R.menu.logout, menu);
		else
			getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {

			finish();
		}
		SessionManager session = new SessionManager(getApplicationContext());
		if (session.isLoggedIn()) {
			if (id == R.id.logout) {

				// finish();
				// startActivity(getIntent());
				invalidateOptionsMenu();
				session.logoutUser();
				return true;
			}if (id == R.id.cntcus) {
				Intent i = new Intent(getApplicationContext(), ContactUs.class);
				startActivity(i);

				return true;
			}if (id == R.id.shareapp) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");
				sendIntent.setType("text/plain");
				startActivity(Intent.createChooser(sendIntent,"Share App To..."));
				return true;
			}
			if (id == R.id.rateapp) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
			    intent.setData(Uri.parse("market://details?id=com.google.android.googlequicksearchbox"));
			   //intent.setData(Uri.parse("https://play.google.com/store/apps/details?[Id]"));
			     startActivity(intent);
			       return true;
			    }
		} else {
			if (id == R.id.login) {
				Intent i = new Intent(getApplicationContext(), Login.class);
				startActivity(i);

				return true;
			}if (id == R.id.cntcus) {
				Intent i = new Intent(getApplicationContext(), ContactUs.class);
				startActivity(i);

				return true;
			}if (id == R.id.shareapp) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");
				sendIntent.setType("text/plain");
				startActivity(Intent.createChooser(sendIntent,"Share App To..."));
				return true;
			}
			if (id == R.id.rateapp) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
			    intent.setData(Uri.parse("market://details?id=com.google.android.googlequicksearchbox"));
			   //intent.setData(Uri.parse("https://play.google.com/store/apps/details?[Id]"));
			     startActivity(intent);
			       return true;
			    }
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		// finish();
		// startActivity(getIntent());
		invalidateOptionsMenu();
	}

	boolean check() {
		if (ValidationMethod.checkEmpty(etperson)) {
			Toast.makeText(getBaseContext(), "Please fill up No Of Persons",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etdate)) {
			Toast.makeText(getBaseContext(), "Please fill up Date ",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(ettime)) {
			Toast.makeText(getBaseContext(), "Please fill up Time",
					Toast.LENGTH_LONG).show();
		} else {
			return true;
		}
		return false;
	}

	void show_dialog() {
		final Dialog dialog = new Dialog(TableBooking.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_table_booking);

		Button ok, cancle;
		TextView noofperson, date, time;
		ok = (Button) dialog.findViewById(R.id.btnyes);
		cancle = (Button) dialog.findViewById(R.id.btnno);
		noofperson = (TextView) dialog.findViewById(R.id.dianoofperson);
		date = (TextView) dialog.findViewById(R.id.diadate);
		time = (TextView) dialog.findViewById(R.id.diatime);
		noofperson
				.setText(noofperson.getText() + etperson.getText().toString());
		date.setText(date.getText() + etdate.getText().toString());
		time.setText(time.getText() + ettime.getText().toString());
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new insertdata().execute();
				dialog.dismiss();
			}
		});
		cancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	class insertdata extends AsyncTask<Void, Void, Void> {
		ProgressDialog progress;
		JSONObject object;

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
//			List<NameValuePair> par = new ArrayList<NameValuePair>();
//			par.add(new BasicNameValuePair("email", etemail.getText()
//			 .toString()));
//			object = jsonparser.makeHttpRequest(
//					getResources().getString(R.string.signup_email_url),
//					"POST", par);
//
//			try {
//				if (object.getBoolean("status")) {
//				}
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			super.onPreExecute();
			progress = new ProgressDialog(TableBooking.this);
			progress.setMessage("Placing Order..");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progress.dismiss();
			Toast.makeText(getBaseContext(), "Order Placed Successfully",
					Toast.LENGTH_LONG).show();
			finish();
		}

	}
}
