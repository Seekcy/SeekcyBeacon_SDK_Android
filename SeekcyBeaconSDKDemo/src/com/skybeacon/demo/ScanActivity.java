package com.skybeacon.demo;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.skybeacon.sdk.RangingBeaconsListener;
import com.skybeacon.sdk.ScanServiceStateCallback;
import com.skybeacon.sdk.locate.SKYBeacon;
import com.skybeacon.sdk.locate.SKYBeaconManager;
import com.skybeacon.sdk.locate.SKYBeaconMultiIDs;
import com.skybeacon.sdk.locate.SKYRegion;

@SuppressLint("HandlerLeak")
public class ScanActivity extends Activity {
	private final int UPDATE_LIST_VIEW = 1;
	// listview
	private ListView listView;
	private LeDeviceListAdapter leDeviceListAdapter;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case UPDATE_LIST_VIEW:
				leDeviceListAdapter.addDevice((iBeaconView) msg.obj);
				leDeviceListAdapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		SKYBeaconManager.getInstance().init(this);
		SKYBeaconManager.getInstance().setCacheTimeMillisecond(3000);
		SKYBeaconManager.getInstance().setScanTimerIntervalMillisecond(2000);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onStart();
		initListView();
		startRanging();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onStop();
		stopRanging();
	}

	private void initListView() {
		listView = (ListView) findViewById(R.id.listview_scan);
		leDeviceListAdapter = new LeDeviceListAdapter(this);
		listView.setAdapter(leDeviceListAdapter);
		// TODO ListAdapter的notify更新必须减慢，不然影响点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				ListView lview = (ListView) arg0;
				iBeaconView beacon = (iBeaconView) lview.getItemAtPosition(arg2);
				String deviceAddress = beacon.mac;

				if (beacon.isMultiIDs) {
					Intent intent = new Intent(ScanActivity.this, ConfigMultiIDsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("deviceAddress", deviceAddress);
					intent.putExtras(bundle);
					startActivity(intent);
				} else {
					Intent intent = new Intent(ScanActivity.this, ConfigSingleIDActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("deviceAddress", deviceAddress);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
	}

	private void startRanging() {
		SKYBeaconManager.getInstance().startScanService(new ScanServiceStateCallback() {

			@Override
			public void onServiceDisconnected() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onServiceConnected() {
				// TODO Auto-generated method stub
				SKYBeaconManager.getInstance().startRangingBeacons(null);
			}
		});

		SKYBeaconManager.getInstance().setRangingBeaconsListener(new RangingBeaconsListener() {

			@Override
			public void onRangedBeacons(SKYRegion beaconRegion, @SuppressWarnings("rawtypes") List beaconList) {
				// TODO Auto-generated method stub
				for (int i = 0; i < beaconList.size(); i++) {
					iBeaconView beacon = new iBeaconView();
					beacon.mac = ((SKYBeacon) beaconList.get(i)).getDeviceAddress();
					beacon.rssi = ((SKYBeacon) beaconList.get(i)).getRssi();
					beacon.isMultiIDs = false;
					beacon.detailInfo = ((SKYBeacon) beaconList.get(i)).getProximityUUID() + "\r\nMajor: " + String.valueOf(((SKYBeacon) beaconList.get(i)).getMajor()) + "\tMinir: "
							+ String.valueOf(((SKYBeacon) beaconList.get(i)).getMinor()) + "\r\n";
					beacon.detailInfo += "version: " + String.valueOf(((SKYBeacon) beaconList.get(i)).getHardwareVersion()) + "."
							+ String.valueOf(((SKYBeacon) beaconList.get(i)).getFirmwareVersionMajor()) + "." + String.valueOf(((SKYBeacon) beaconList.get(i)).getFirmwareVersionMinor());
					Message msg = new Message();
					msg.obj = beacon;
					msg.what = UPDATE_LIST_VIEW;
					mHandler.sendMessage(msg);
				}
			}

			@Override
			public void onRangedBeaconsMultiIDs(SKYRegion beaconRegion, @SuppressWarnings("rawtypes") List beaconMultiIDsList) {
				// TODO Auto-generated method stub
				for (int i = 0; i < beaconMultiIDsList.size(); i++) {
					iBeaconView beacon = new iBeaconView();
					beacon.mac = ((SKYBeaconMultiIDs) beaconMultiIDsList.get(i)).getDeviceAddress();
					beacon.isMultiIDs = true;
					beacon.detailInfo = "version: " + String.valueOf(((SKYBeaconMultiIDs) beaconMultiIDsList.get(i)).getHardwareVersion()) + "."
							+ String.valueOf(((SKYBeaconMultiIDs) beaconMultiIDsList.get(i)).getFirmwareVersionMajor()) + "."
							+ String.valueOf(((SKYBeaconMultiIDs) beaconMultiIDsList.get(i)).getFirmwareVersionMinor());
					beacon.detailInfo += "\r\n";
					for (int j = 0; j < ((SKYBeaconMultiIDs) beaconMultiIDsList.get(i)).getBeaconList().size(); j++) {
						beacon.detailInfo += ((SKYBeacon) ((SKYBeaconMultiIDs) beaconMultiIDsList.get(i)).getBeaconList().get(j)).getDeviceAddress() + "\t"
								+ String.valueOf(((SKYBeacon) ((SKYBeaconMultiIDs) beaconMultiIDsList.get(i)).getBeaconList().get(j)).getMajor()) + "\t"
								+ String.valueOf(((SKYBeacon) ((SKYBeaconMultiIDs) beaconMultiIDsList.get(i)).getBeaconList().get(j)).getMinor()) + "\r\n";
					}
					Message msg = new Message();
					msg.obj = beacon;
					msg.what = UPDATE_LIST_VIEW;
					mHandler.sendMessage(msg);
				}
			}
		});
	}

	private void stopRanging() {
		SKYBeaconManager.getInstance().stopScanService();
		SKYBeaconManager.getInstance().stopRangingBeasons(null);
	}
}
