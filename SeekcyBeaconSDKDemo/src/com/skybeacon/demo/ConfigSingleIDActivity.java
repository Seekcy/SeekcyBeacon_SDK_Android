package com.skybeacon.demo;

import java.util.ArrayList;
import java.util.List;

import com.skybeacon.demo.widget.CheckSwitchButton;
import com.skybeacon.sdk.ConfigCallback;
import com.skybeacon.sdk.ConnectionStateCallback;
import com.skybeacon.sdk.config.SKYBeaconCommunication;
import com.skybeacon.sdk.config.SKYBeaconConfig;
import com.skybeacon.sdk.config.SKYBeaconConfigException;
import com.skybeacon.sdk.config.SKYBeaconPower;
import com.skybeacon.sdk.locate.SKYBeacon;
import com.skybeacon.sdk.locate.SKYBeaconMultiIDs;
import com.skybeacon.sdk.utils.DefaultStaticValues;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ConfigSingleIDActivity extends Activity {
	private SKYBeaconCommunication skyBeaconCommunication;
	private String deviceAddress = null;
	private TextView stateInfo;

	private EditText uuid1;
	private EditText major1;
	private EditText minor1;
	private EditText measuredPower1;
	private EditText txPower1;
	private EditText interval;
	private EditText key;
	private EditText deviceName;
	private CheckSwitchButton lockSB;
	private Spinner spinnerUuid1;
	private EditText ledState;
	private Spinner spinnerLed;
	private List<String> mUuidList = null;
	private List<String> mLedList = null;
	private boolean firstIn1 = true;
	private boolean firstIn2 = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_single_id);

		stateInfo = (TextView) findViewById(R.id.state_info);
		deviceAddress = getIntent().getExtras().getString("deviceAddress");
		initView();
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				stateInfo.setText("connecting!");
			}
		});
		connectBeacon();
	}

	private void initView() {
		uuid1 = (EditText) findViewById(R.id.config_uuid1);
		major1 = (EditText) findViewById(R.id.config_major1);
		minor1 = (EditText) findViewById(R.id.config_minor1);
		measuredPower1 = (EditText) findViewById(R.id.config_measured_power1);
		txPower1 = (EditText) findViewById(R.id.config_txpower1);
		interval = (EditText) findViewById(R.id.config_interval);
		key = (EditText) findViewById(R.id.config_key);
		ledState = (EditText) findViewById(R.id.config_led);

		deviceName = (EditText) findViewById(R.id.config_device_name);
		lockSB = (CheckSwitchButton) findViewById(R.id.config_lock_switchbutton);

		mUuidList = new ArrayList<String>();
		mUuidList.add("FDA50693-A4E2-4FB1-AFCF-C6EB07647825");
		mUuidList.add("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.spinner_broadcast_id, R.id.spinner_broadcast_id_view,
				mUuidList);
		spinnerUuid1 = (Spinner) findViewById(R.id.config_uuid_spinner1);
		spinnerUuid1.setAdapter(adapter);
		spinnerUuid1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (!firstIn1) {
					uuid1.setText(mUuidList.get(arg2));
				} else {
					firstIn1 = false;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		mLedList = new ArrayList<String>();
		mLedList.add("不变");
		mLedList.add("关闭");
		mLedList.add("打开");
		mLedList.add("切换");
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				R.layout.spinner_broadcast_id, R.id.spinner_broadcast_id_view,
				mLedList);
		spinnerLed = (Spinner) findViewById(R.id.config_led_spinner);
		spinnerLed.setAdapter(adapter1);
		spinnerLed.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (!firstIn2) {
					ledState.setText(mLedList.get(arg2));
				} else {
					firstIn2 = false;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setView(SKYBeacon skyBeacon) {
		if (!skyBeacon.getProximityUUID().equals(
				DefaultStaticValues.DEFAULT_SKY_BEACON_PROXIMITY_UUID)) {
			uuid1.setText(skyBeacon.getProximityUUID());
		}
		if (skyBeacon.getMajor() != DefaultStaticValues.DEFAULT_SKY_BEACON_MAJOR_FALSE) {
			major1.setText(String.valueOf(skyBeacon.getMajor()));
		}
		if (skyBeacon.getMinor() != DefaultStaticValues.DEFAULT_SKY_BEACON_MINOR_FALSE) {
			minor1.setText(String.valueOf(skyBeacon.getMinor()));
		}
		if (skyBeacon.getMeasuredPower() != DefaultStaticValues.DEFAULT_SKY_BEACON_MEASURED_POWER_FALSE) {
			measuredPower1
					.setText(String.valueOf(skyBeacon.getMeasuredPower()));
		}
		if (SKYBeaconPower.getPower(skyBeacon.getTxpower()) != DefaultStaticValues.DEFAULT_SKY_BEACON_TXPOWER_FALSE) {
			txPower1.setText(String.valueOf(skyBeacon.getTxpower()));
		}

		if (skyBeacon.getIntervalMillisecond() != DefaultStaticValues.DEFAULT_SKY_BEACON_INTERVAL_MILLISECOND_FALSE) {
			interval.setText(String.valueOf(skyBeacon.getIntervalMillisecond()));
		}
		if (!skyBeacon.getDeviceName().equals(
				DefaultStaticValues.DEFAULT_SKY_BEACON_DEVICE_NAME)) {
			deviceName.setText(skyBeacon.getDeviceName());
		}
		if (skyBeacon.isLocked() == 1) {
			lockSB.setChecked(true);
		} else {
			lockSB.setChecked(false);
		}

		if (skyBeacon.getLedState() == DefaultStaticValues.DEFAULT_SKY_BEACON_LED_STATE) {
			ledState.setText("不变");
		} else if (skyBeacon.getLedState() == 0) {
			ledState.setText("关闭");
		} else if (skyBeacon.getLedState() == 1) {
			ledState.setText("打开");
		} else if (skyBeacon.getLedState() == 2) {
			ledState.setText("切换");
		}
	}

	public SKYBeaconConfig getView() {
		SKYBeaconConfig skyBeaconConfig1 = new SKYBeaconConfig();
		if (!uuid1.getText().toString().equals("")) {
			skyBeaconConfig1.setProximityUUID(uuid1.getText().toString());
		}
		if (!major1.getText().toString().equals("")) {
			skyBeaconConfig1.setMajor(Integer.valueOf(major1.getText()
					.toString()));
		}
		if (!minor1.getText().toString().equals("")) {
			skyBeaconConfig1.setMinor(Integer.valueOf(minor1.getText()
					.toString()));
		}
		if (!measuredPower1.getText().toString().equals("")) {
			skyBeaconConfig1.setMeasuredPower(Integer.valueOf(measuredPower1
					.getText().toString()));
		}
		if (!txPower1.getText().toString().equals("")) {
			skyBeaconConfig1.setTxpower(SKYBeaconPower.getPower(Integer
					.valueOf(txPower1.getText().toString())));
		}
		if (!interval.getText().toString().equals("")) {
			skyBeaconConfig1.setIntervalMillisecond(Integer.valueOf(interval
					.getText().toString()));
		}
		if (!key.getText().toString().equals("")) {
			skyBeaconConfig1.setLockedKey(key.getText().toString());
		}
		if (!deviceName.getText().toString().equals("")) {
			skyBeaconConfig1.setDeviceName(deviceName.getText().toString());
		}
		if (lockSB.isChecked()) {
			skyBeaconConfig1.setLocked(1);
		} else {
			skyBeaconConfig1.setLocked(0);
		}

		if (ledState.getText().toString().equals("不变")) {
			skyBeaconConfig1
					.setLedState(DefaultStaticValues.DEFAULT_SKY_BEACON_LED_STATE);
		} else if (ledState.getText().toString().equals("关闭")) {
			skyBeaconConfig1.setLedState(0);
		} else if (ledState.getText().toString().equals("打开")) {
			skyBeaconConfig1.setLedState(1);
		} else if (ledState.getText().toString().equals("切换")) {
			skyBeaconConfig1.setLedState(2);
		}

		return skyBeaconConfig1;
	}

	public void connectBeacon() {
		ConnectionStateCallback connectionStateCallback = new ConnectionStateCallback() {

			@Override
			public void onDisconnected() {
				// TODO Auto-generated method stub
				setDialog("提示", "连接断开", true);
			}

			@Override
			public void onConnectedSuccess(SKYBeaconMultiIDs skyBeaconMultiIDs) {
				// final SKYBeaconMultiIDs skyBeaconMultiIDsTmp =
				// skyBeaconMultiIDs;
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						stateInfo.setText("connect success! multiBeacon");
					}
				});
			}

			@Override
			public void onConnectedSuccess(SKYBeacon skyBeacon) {
				final SKYBeacon skyBeaconTmp = skyBeacon;
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						stateInfo.setText("connect success! singleBeacon");
						setView(skyBeaconTmp);
					}
				});
			}

			@Override
			public void onConnectedFailed(
					SKYBeaconConfigException skyBeaconConfigException) {
				final String exceptionInfo = skyBeaconConfigException
						.getMessage()
						+ String.valueOf(skyBeaconConfigException.getCode());
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						stateInfo.setText("connect failed!" + exceptionInfo);
					}
				});

				setDialog("提示", "连接失败", true);
			}
		};
		skyBeaconCommunication = new SKYBeaconCommunication(this);
		SKYBeacon skyBeaconConnect = new SKYBeacon(deviceAddress);
		skyBeaconCommunication.connect(skyBeaconConnect,
				connectionStateCallback);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		skyBeaconCommunication.disconnect();
	}

	private void setDialog(String title, String info, final boolean isFinish) {
		Dialog dialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(info)
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						if (isFinish) {
							finish();
						}
					}
				}).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.config_command:
			// TODO Auto-generated method stub
			SKYBeaconConfig skyBeaconConfig = getView();
			skyBeaconCommunication.configSKYBeacon(skyBeaconConfig,
					new ConfigCallback() {

						@Override
						public void onConfigSuccess() {
							// TODO Auto-generated method stub
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method
									// stub
									stateInfo.setText("config success!");
									setDialog("提示", "配置成功", true);
								}
							});
						}

						@Override
						public void onConfigFailed(
								SKYBeaconConfigException skyBeaconConfigException) {
							final String exceptionInfo = skyBeaconConfigException
									.getMessage()
									+ String.valueOf(skyBeaconConfigException
											.getCode());
							// TODO Auto-generated method stub
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method
									// stub
									stateInfo.setText("config failed!"
											+ exceptionInfo);
								}
							});
						}
					});
			break;
		}
	}

}
