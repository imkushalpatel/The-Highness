package com.highness.highnesshotel;

import helper.JSONParser;
import helper.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;



public class EventBooking extends Activity {
EditText etdate,ettime;
Calendar calendar;
Spinner spvanue,sptype,spdecor,sppack;
List<String> listvanue,listtype,listdecor,listpack;
ArrayAdapter<String> advanue,adtype,addecor,adpack;
JSONParser jsonparser ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_booking);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		calendar=Calendar.getInstance();
		etdate=(EditText)findViewById(R.id.edate);
		ettime=(EditText)findViewById(R.id.etime);
		spvanue=(Spinner)findViewById(R.id.venue);
		sptype=(Spinner)findViewById(R.id.etype);
		spdecor=(Spinner)findViewById(R.id.edecor);
		sppack=(Spinner)findViewById(R.id.epack);
		jsonparser= new JSONParser();
		new loaddata().execute();
		 etdate.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
	                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	                if (imm != null) {
	                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	                }
	                return true;
				}
			});
		 etdate.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                DatePickerDialog dpd = new DatePickerDialog(EventBooking.this,
	                        new DatePickerDialog.OnDateSetListener() {

	                            @Override
	                            public void onDateSet(DatePicker view, int year,
	                                                  int monthOfYear, int dayOfMonth) {
	                                etdate.setText(year + "-"
	                                        + (monthOfYear + 1) + "-" + dayOfMonth);

	                            }
	                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	                dpd.show();
	            }
	        });
		 ettime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerDialog tpd =new TimePickerDialog(EventBooking.this, new TimePickerDialog.OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						
					}
				}, calendar.HOUR_OF_DAY, calendar.MINUTE, true);
				tpd.show();
				
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
			}
		} else {
			if (id == R.id.login) {
				Intent i = new Intent(getApplicationContext(), Login.class);
				startActivity(i);

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
	class loaddata extends AsyncTask<Void, Void, Void>{
		ProgressDialog progress;
		
		JSONObject object;
		@Override
		protected Void doInBackground(Void... params) {
			listvanue=new ArrayList<String>();
			listtype=new ArrayList<String>();
			listdecor=new ArrayList<String>();
			listpack=new ArrayList<String>();
			listvanue.add("Select Vanue");
			listtype.add("Select Event Type");
			listdecor.add("Select Decoration");
			listpack.add("Select Package");
			object=jsonparser.getJSONFromUrl("http://192.168.1.153/mobile/geteventvanuetype.php");
			try {
	                JSONArray jsonArray = object.getJSONArray("vanuelist");
	                for (int i = 0; i < jsonArray.length(); i++) {
	                    JSONObject object1 = jsonArray.getJSONObject(i);
	                    listvanue.add(object1.getString("vanuename"));
	                    

	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
			advanue=new ArrayAdapter<String>(EventBooking.this,android.R.layout.simple_spinner_item,listvanue);
			object=jsonparser.getJSONFromUrl("http://192.168.1.153/mobile/geteventtype.php");
			try {
	                JSONArray jsonArray = object.getJSONArray("eventlist");
	                for (int i = 0; i < jsonArray.length(); i++) {
	                    JSONObject object1 = jsonArray.getJSONObject(i);
	                    listtype.add(object1.getString("eventname"));
	                    

	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
			adtype=new ArrayAdapter<String>(EventBooking.this,android.R.layout.simple_spinner_item,listtype);
			object=jsonparser.getJSONFromUrl("http://192.168.1.153/mobile/geteventdecortype.php");
			try {
	                JSONArray jsonArray = object.getJSONArray("decorlist");
	                for (int i = 0; i < jsonArray.length(); i++) {
	                    JSONObject object1 = jsonArray.getJSONObject(i);
	                    listdecor.add(object1.getString("decorname"));
	                    

	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
			addecor=new ArrayAdapter<String>(EventBooking.this,android.R.layout.simple_spinner_item,listdecor);
			object=jsonparser.getJSONFromUrl("http://192.168.1.153/mobile/geteventpackagetype.php");
			try {
	                JSONArray jsonArray = object.getJSONArray("paclist");
	                for (int i = 0; i < jsonArray.length(); i++) {
	                    JSONObject object1 = jsonArray.getJSONObject(i);
	                    listpack.add(object1.getString("pacname"));
	                    

	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
			adpack=new ArrayAdapter<String>(EventBooking.this,android.R.layout.simple_spinner_item,listpack);
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress = new ProgressDialog(EventBooking.this);
			progress.setMessage("Loading Data");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			spvanue.setAdapter(advanue);
			sptype.setAdapter(adtype);
			spdecor.setAdapter(addecor);
			sppack.setAdapter(adpack);
			progress.dismiss();
		}
		
	}
}
