package com.highness.highnesshotel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.highness.highnesshotel.TableBooking.insertdata;

import helper.JSONParser;
import helper.SessionManager;
import helper.ValidationMethod;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RoomBooking extends Activity {
	List<String> listtype;
	ArrayAdapter<String> adtype;
	Spinner sptype;
	EditText etnoofrooms, etelder,etchild,etdatein,etdateout;
	Button btnbook;
	SessionManager sessionmanager;
	JSONParser jsonparser;
	Calendar calendar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_booking);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		sessionmanager=new SessionManager(getApplicationContext());
		jsonparser=new JSONParser();
		calendar=Calendar.getInstance();
		sptype=(Spinner)findViewById(R.id.rtype);
		btnbook=(Button)findViewById(R.id.rumbuk);
		etnoofrooms=(EditText)findViewById(R.id.rnumber);
		etelder=(EditText)findViewById(R.id.elder);
		etchild=(EditText)findViewById(R.id.child);
		etdatein=(EditText)findViewById(R.id.chckin);
		etdateout=(EditText)findViewById(R.id.chckout);
		new loaddata().execute();
		etdatein.setOnTouchListener(new View.OnTouchListener() {
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
		etdateout.setOnTouchListener(new View.OnTouchListener() {

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
		etdatein.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dpd = new DatePickerDialog(RoomBooking.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								etdatein.setText(dayOfMonth + "-"
										+ (monthOfYear + 1) + "-" + year);

							}
						}, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH));
				dpd.show();
			}
		});
		etdateout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dpd = new DatePickerDialog(RoomBooking.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								etdateout.setText(dayOfMonth + "-"
										+ (monthOfYear + 1) + "-" + year);

							}
						}, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH));
				dpd.show();
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
	class loaddata extends AsyncTask<Void, Void, Void>{
		ProgressDialog progress;
		
		JSONObject object;
		@Override
		protected Void doInBackground(Void... params) {
			
			listtype=new ArrayList<String>();
			
			listtype.add("Select Room Type");
			
			object=jsonparser.getJSONFromUrl(getResources().getString(R.string.room_type_url));
			try {
	                JSONArray jsonArray = object.getJSONArray("roomtypelist");
	                for (int i = 0; i < jsonArray.length(); i++) {
	                    JSONObject object1 = jsonArray.getJSONObject(i);
	                    listtype.add(object1.getString("roomname"));
	                    

	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
			adtype=new ArrayAdapter<String>(RoomBooking.this,android.R.layout.simple_spinner_item,listtype);
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress = new ProgressDialog(RoomBooking.this);
			progress.setMessage("Loading Data");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			sptype.setAdapter(adtype);
			
			progress.dismiss();
		}
		
	}
	boolean check() {
		if (sptype.getSelectedItemId()==0) {
			Toast.makeText(getBaseContext(), "Please Select Room Type",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etnoofrooms)) {
			Toast.makeText(getBaseContext(), "Please fill up No Of Room ",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etelder)) {
			Toast.makeText(getBaseContext(), "Please fill up No Of Elder",
					Toast.LENGTH_LONG).show();
		}else if (ValidationMethod.checkEmpty(etchild)) {
			Toast.makeText(getBaseContext(), "Please fill up No Of Child",
					Toast.LENGTH_LONG).show();
		} else if (ValidationMethod.checkEmpty(etdatein)) {
			Toast.makeText(getBaseContext(), "Please fill up Check-In Date",
					Toast.LENGTH_LONG).show();
		}else if (ValidationMethod.checkEmpty(etdateout)) {
			Toast.makeText(getBaseContext(), "Please fill up Check-Out Date",
					Toast.LENGTH_LONG).show();
		}else {
			return true;
		}
		return false;
	}

	void show_dialog() {
		final Dialog dialog = new Dialog(RoomBooking.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_room_booking);

		Button ok, cancle;
		TextView roomtype,noofroom, elder, child,datein,dateout;
		ok = (Button) dialog.findViewById(R.id.btnyes);
		cancle = (Button) dialog.findViewById(R.id.btnno);
		roomtype = (TextView) dialog.findViewById(R.id.diaroomtype);
		noofroom = (TextView) dialog.findViewById(R.id.dianoofroom);
		elder = (TextView) dialog.findViewById(R.id.dianoofelder);
		child = (TextView) dialog.findViewById(R.id.dianoofchild);
		datein = (TextView) dialog.findViewById(R.id.diacheckin);
		dateout= (TextView) dialog.findViewById(R.id.diacheckout);
		roomtype
				.setText(roomtype.getText() + sptype.getSelectedItem().toString());
		noofroom.setText(noofroom.getText() + etnoofrooms.getText().toString());
		elder.setText(elder.getText() + etelder.getText().toString());
		child.setText(child.getText() + etchild.getText().toString());
		datein.setText(datein.getText() + etdatein.getText().toString());
		dateout.setText(dateout.getText() + etdateout.getText().toString());


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
			progress = new ProgressDialog(RoomBooking.this);
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
