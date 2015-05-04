package com.highness.highnesshotel;

import helper.SessionManager;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class HallBooking extends Activity {
	EditText etdate,ettime;
	Calendar calendar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hall_booking);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		calendar=Calendar.getInstance();
		etdate=(EditText)findViewById(R.id.hdate);
		ettime=(EditText)findViewById(R.id.htime);
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
	                DatePickerDialog dpd = new DatePickerDialog(HallBooking.this,
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
				TimePickerDialog tpd =new TimePickerDialog(HallBooking.this, new TimePickerDialog.OnTimeSetListener() {
					
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
}
