package com.example.testproject;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	private EditText messageNumber;
	private static final String SENT = "SMS_SENT";
	private static final String TAG = "SMSTestApp";
	private int msgParts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageNumber = (EditText) findViewById(R.id.messageNumber);

	}

	public void sayHello(View v) {

		String _messageNumber = messageNumber.getText().toString();
		String messageText = "This is a really long text message - longer than 160 characters! This is a really long text message - longer than 160 characters! This is a really long text message - longer than 160 characters!";

		SmsManager sms = SmsManager.getDefault();

		ArrayList<String> messageParts = sms.divideMessage(messageText);
		msgParts = messageParts.size();

		ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
		BroadcastReceiver sentReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				Log.d(TAG, "SMS onReceive intent received.");
				String resultString = null;
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					resultString = "Error - Generic failure";
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					resultString = "Error - No Service";
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					resultString = "Error - Null PDU";
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					resultString = "Error - Radio off";
					break;
				}
				msgParts--;
				if (msgParts == 0) {
					if (resultString != null) {
						Toast.makeText(getBaseContext(),
								"SMS could not be sent", Toast.LENGTH_SHORT)
								.show();
						Log.i(TAG, "SMS could not be sent: " + resultString);
					} else {
						// success
						Toast.makeText(getBaseContext(), "SMS sent",
								Toast.LENGTH_SHORT).show();
						Log.i(TAG, "SMS sent: " + getResultData());

					}

					unregisterReceiver(this);
				}

			}
		};
		registerReceiver(sentReceiver, new IntentFilter(SENT));

		for (int i = 0; i < msgParts; i++) {
			sentIntents.add(PendingIntent.getBroadcast(this, 0,
					new Intent(SENT), 0));
		}

		sms.sendMultipartTextMessage(_messageNumber, null, messageParts,
				sentIntents, null);

	}

	public boolean canSendSMS() {
		// Source:
		// http://stackoverflow.com/questions/19512336/how-to-reliably-determine-users-capability-to-call-or-sms
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null) {
			return (tm.getNetworkType() != android.telephony.TelephonyManager.NETWORK_TYPE_UNKNOWN);
		} else {
			return false;
		}
	}
}