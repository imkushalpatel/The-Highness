package com.highness.highnesshotel;

import helper.SessionManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RoomBooking extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_booking);
		getActionBar().setDisplayHomeAsUpEnabled(true);
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
