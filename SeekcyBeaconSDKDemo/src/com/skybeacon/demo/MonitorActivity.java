package com.skybeacon.demo;

import java.util.List;

import com.skybeacon.sdk.MonitoringBeaconsListener;
import com.skybeacon.sdk.ScanServiceStateCallback;
import com.skybeacon.sdk.locate.SKYBeaconManager;
import com.skybeacon.sdk.locate.SKYRegion;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class MonitorActivity extends Activity {

	private static final int UPDATE_TEXT_VIEW = 1;
	private static final SKYRegion MONITOR_REGION_TEST = new SKYRegion(
			"rid_test", null, "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0", 111, 111);

	private TextView textView;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case UPDATE_TEXT_VIEW:
				textView.setText((String) msg.obj);
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitor);

		SKYBeaconManager.getInstance().init(this);
		SKYBeaconManager.getInstance().setMonitoringBeaconsListener(
				monitoringBeaconsListener);

		textView = (TextView) findViewById(R.id.text_view);
	}

	MonitoringBeaconsListener monitoringBeaconsListener = new MonitoringBeaconsListener() {

		@SuppressWarnings("rawtypes")
		@Override
		public void onEnteredRegion(SKYRegion arg0, List arg1) {
			// TODO Auto-generated method stub
			String tmp = "onEnteredRgion: " + String.valueOf(arg1.size())
					+ "\r\n" + arg0.getIdentifier() + "\r\n"
					+ arg0.getDeviceAddress() + "\r\n"
					+ arg0.getProximityUUID() + "\r\n"
					+ String.valueOf(arg0.getMajor()) + "\r\n"
					+ String.valueOf(arg0.getMinor()) + "\r\n";
			Message msg = new Message();
			msg.what = UPDATE_TEXT_VIEW;
			msg.obj = tmp;
			handler.sendMessage(msg);
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void onExitedRegion(SKYRegion arg0, List arg1) {
			// TODO Auto-generated method stub
			String tmp = "onExitedRgion: " + String.valueOf(arg1.size())
					+ "\r\n" + arg0.getIdentifier() + "\r\n"
					+ arg0.getDeviceAddress() + "\r\n"
					+ arg0.getProximityUUID() + "\r\n"
					+ String.valueOf(arg0.getMajor()) + "\r\n"
					+ String.valueOf(arg0.getMinor()) + "\r\n";
			Message msg = new Message();
			msg.what = UPDATE_TEXT_VIEW;
			msg.obj = tmp;
			handler.sendMessage(msg);
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onStart();
		startMonitoring();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onStop();
		stopMonitoring();
	}

	private void startMonitoring() {
		SKYBeaconManager.getInstance().startScanService(
				new ScanServiceStateCallback() {
					@Override
					public void onServiceDisconnected() {
						// TODO Auto-generated method stub
					}

					@Override
					public void onServiceConnected() {
						// TODO Auto-generated method stub
						SKYBeaconManager.getInstance().startMonitoringBeacons(
								MONITOR_REGION_TEST);
					}
				});
	}

	private void stopMonitoring() {
		SKYBeaconManager.getInstance().stopScanService();
		SKYBeaconManager.getInstance().stopMonitoringBeacons(
				MONITOR_REGION_TEST);
	}
}
