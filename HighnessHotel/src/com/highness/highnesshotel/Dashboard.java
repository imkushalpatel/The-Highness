package com.highness.highnesshotel;

import helper.SessionManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class Dashboard extends Activity {
	ImageView btntbl, btnevent, btnroom, btnhall;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		btntbl = (ImageView) findViewById(R.id.tblbtn);
		btnevent = (ImageView) findViewById(R.id.eventbtn);
		btnroom = (ImageView) findViewById(R.id.roombtn);
		btnhall = (ImageView) findViewById(R.id.hallbtn);
		btntbl.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent tablebookintent =new Intent(getApplicationContext(),TableBooking.class);
				startActivity(tablebookintent);

			}
		});
		btnevent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent eventbookintent =new Intent(getApplicationContext(),EventBooking.class);
				startActivity(eventbookintent);
			}
		});
		btnroom.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent roombookintent =new Intent(getApplicationContext(),RoomBooking.class);
				startActivity(roombookintent);

			}
		});
		btnhall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent hallbookintent =new Intent(getApplicationContext(),HallBooking.class);
				startActivity(hallbookintent);

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
		SessionManager session = new SessionManager(getApplicationContext());
		if (session.isLoggedIn()) {
			if (id == R.id.logout) {

				// finish();
				// startActivity(getIntent());
				invalidateOptionsMenu();
				session.logoutUser();
				return true;
			}
			if (id == R.id.cntcus) {
				Intent i = new Intent(getApplicationContext(), ContactUs.class);
				startActivity(i);

				return true;
			}
			if (id == R.id.shareapp) {
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
			}
			if (id == R.id.cntcus) {
				Intent i = new Intent(getApplicationContext(), ContactUs.class);
				startActivity(i);

				return true;
			}
			if (id == R.id.shareapp) {
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

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		// finish();
		// startActivity(getIntent());
		invalidateOptionsMenu();
	}

}
