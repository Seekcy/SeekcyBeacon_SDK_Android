package com.skybeacon.demo;

import java.util.ArrayList;
import java.util.List;

import com.skybeacon.demo.widget.CheckSwitchButton;

import com.skybeacon.sdk.ConfigCallback;
import com.skybeacon.sdk.ConnectionStateCallback;
import com.skybeacon.sdk.config.SKYBeaconBroadcastID;
import com.skybeacon.sdk.config.SKYBeaconCellsSituation;
import com.skybeacon.sdk.config.SKYBeaconCommunication;
import com.skybeacon.sdk.config.SKYBeaconConfig;
import com.skybeacon.sdk.config.SKYBeaconConfigException;
import com.skybeacon.sdk.config.SKYBeaconConfigMultiIDs;
import com.skybeacon.sdk.config.SKYBeaconPower;
import com.skybeacon.sdk.locate.SKYBeacon;
import com.skybeacon.sdk.locate.SKYBeaconMultiIDs;
import com.skybeacon.sdk.utils.DefaultStaticValues;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class ConfigMultiIDsActivity extends Activity {
	private SKYBeaconCommunication skyBeaconCommunication;
	private String deviceAddress = null;
	private TextView stateInfo;

	private EditText uuid1;
	private EditText major1;
	private EditText minor1;
	private EditText measuredPower1;
	private EditText txPower1;
	private CheckSwitchButton encryptSB1;
	private EditText uuid2;
	private EditText major2;
	private EditText minor2;
	private EditText measuredPower2;
	private EditText txPower2;
	private CheckSwitchButton encryptSB2;
	private EditText uuid3;
	private EditText major3;
	private EditText minor3;
	private EditText measuredPower3;
	private EditText txPower3;
	private CheckSwitchButton encryptSB3;
	private EditText interval;
	private EditText key;
	private EditText deviceName;
	private CheckSwitchButton lockSB;
	private EditText grid1;
	private EditText grid2;
	private EditText grid3;
	private EditText grid4;
	private EditText grid5;
	private EditText grid6;
	private EditText grid7;
	private EditText grid8;
	private EditText grid9;
	private Spinner spinnerUuid1;
	private Spinner spinnerUuid2;
	private Spinner spinnerUuid3;
	private List<String> mUuidList = null;
	private boolean firstIn1 = true;
	private boolean firstIn2 = true;
	private boolean firstIn3 = true;

	private CheckSwitchButton configTime;
	private CheckSwitchButton configReset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_multi_id);

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
		encryptSB1 = (CheckSwitchButton) findViewById(R.id.config_single_encrypt_switchbutton1);
		encryptSB1.setChecked(false);
		uuid2 = (EditText) findViewById(R.id.config_uuid2);
		major2 = (EditText) findViewById(R.id.config_major2);
		minor2 = (EditText) findViewById(R.id.config_minor2);
		measuredPower2 = (EditText) findViewById(R.id.config_measured_power2);
		txPower2 = (EditText) findViewById(R.id.config_txpower2);
		encryptSB2 = (CheckSwitchButton) findViewById(R.id.config_single_encrypt_switchbutton2);
		encryptSB2.setChecked(false);
		uuid3 = (EditText) findViewById(R.id.config_uuid3);
		major3 = (EditText) findViewById(R.id.config_major3);
		minor3 = (EditText) findViewById(R.id.config_minor3);
		measuredPower3 = (EditText) findViewById(R.id.config_measured_power3);
		txPower3 = (EditText) findViewById(R.id.config_txpower3);
		encryptSB3 = (CheckSwitchButton) findViewById(R.id.config_single_encrypt_switchbutton3);
		encryptSB3.setChecked(false);
		interval = (EditText) findViewById(R.id.config_interval);
		key = (EditText) findViewById(R.id.config_key);

		deviceName = (EditText) findViewById(R.id.config_device_name);
		lockSB = (CheckSwitchButton) findViewById(R.id.config_lock_switchbutton);
		grid1 = (EditText) findViewById(R.id.config_grid1);
		grid2 = (EditText) findViewById(R.id.config_grid2);
		grid3 = (EditText) findViewById(R.id.config_grid3);
		grid4 = (EditText) findViewById(R.id.config_grid4);
		grid5 = (EditText) findViewById(R.id.config_grid5);
		grid6 = (EditText) findViewById(R.id.config_grid6);
		grid7 = (EditText) findViewById(R.id.config_grid7);
		grid8 = (EditText) findViewById(R.id.config_grid8);
		grid9 = (EditText) findViewById(R.id.config_grid9);

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
		spinnerUuid2 = (Spinner) findViewById(R.id.config_uuid_spinner2);
		spinnerUuid2.setAdapter(adapter);
		spinnerUuid2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (!firstIn2) {
					uuid2.setText(mUuidList.get(arg2));
				} else {
					firstIn2 = false;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spinnerUuid3 = (Spinner) findViewById(R.id.config_uuid_spinner3);
		spinnerUuid3.setAdapter(adapter);
		spinnerUuid3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (!firstIn3) {
					uuid3.setText(mUuidList.get(arg2));
				} else {
					firstIn3 = false;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		configTime = (CheckSwitchButton) findViewById(R.id.config_time_switchbutton);
		configTime.setChecked(false);
		configReset = (CheckSwitchButton) findViewById(R.id.config_reset_switchbutton);
		configReset.setChecked(false);
	}

	private void setView(SKYBeaconMultiIDs skyBeaconMultiIDs) {
		@SuppressWarnings("unchecked")
		List<SKYBeacon> skyBeaconList = skyBeaconMultiIDs.getBeaconList();
		if (skyBeaconList.size() > 0) {
			if (!skyBeaconList
					.get(0)
					.getProximityUUID()
					.equals(DefaultStaticValues.DEFAULT_SKY_BEACON_PROXIMITY_UUID)) {
				uuid1.setText(skyBeaconList.get(0).getProximityUUID());
			}
			if (skyBeaconList.get(0).getMajor() != DefaultStaticValues.DEFAULT_SKY_BEACON_MAJOR_FALSE) {
				major1.setText(String.valueOf(skyBeaconList.get(0).getMajor()));
			}
			if (skyBeaconList.get(0).getMinor() != DefaultStaticValues.DEFAULT_SKY_BEACON_MINOR_FALSE) {
				minor1.setText(String.valueOf(skyBeaconList.get(0).getMinor()));
			}
			if (skyBeaconList.get(0).getMeasuredPower() != DefaultStaticValues.DEFAULT_SKY_BEACON_MEASURED_POWER_FALSE) {
				measuredPower1.setText(String.valueOf(skyBeaconList.get(0)
						.getMeasuredPower()));
			}
			if (SKYBeaconPower.getPower(skyBeaconList.get(0).getTxpower()) != DefaultStaticValues.DEFAULT_SKY_BEACON_TXPOWER_FALSE) {
				txPower1.setText(String.valueOf(skyBeaconList.get(0)
						.getTxpower()));
			}
			if (skyBeaconList.get(0).isEncrypted() == 1) {
				encryptSB1.setChecked(true);
			} else {
				encryptSB1.setChecked(false);
			}
		}
		if (skyBeaconList.size() > 1) {
			if (!skyBeaconList
					.get(1)
					.getProximityUUID()
					.equals(DefaultStaticValues.DEFAULT_SKY_BEACON_PROXIMITY_UUID)) {
				uuid2.setText(skyBeaconList.get(1).getProximityUUID());
			}
			if (skyBeaconList.get(1).getMajor() != DefaultStaticValues.DEFAULT_SKY_BEACON_MAJOR_FALSE) {
				major2.setText(String.valueOf(skyBeaconList.get(1).getMajor()));
			}
			if (skyBeaconList.get(1).getMinor() != DefaultStaticValues.DEFAULT_SKY_BEACON_MINOR_FALSE) {
				minor2.setText(String.valueOf(skyBeaconList.get(1).getMinor()));
			}
			if (skyBeaconList.get(1).getMeasuredPower() != DefaultStaticValues.DEFAULT_SKY_BEACON_MEASURED_POWER_FALSE) {
				measuredPower2.setText(String.valueOf(skyBeaconList.get(1)
						.getMeasuredPower()));
			}
			if (SKYBeaconPower.getPower(skyBeaconList.get(1).getTxpower()) != DefaultStaticValues.DEFAULT_SKY_BEACON_TXPOWER_FALSE) {
				txPower2.setText(String.valueOf(skyBeaconList.get(1)
						.getTxpower()));
			}
			if (skyBeaconList.get(1).isEncrypted() == 1) {
				encryptSB2.setChecked(true);
			} else {
				encryptSB2.setChecked(false);
			}
		}
		if (skyBeaconList.size() > 2) {
			if (!skyBeaconList
					.get(2)
					.getProximityUUID()
					.equals(DefaultStaticValues.DEFAULT_SKY_BEACON_PROXIMITY_UUID)) {
				uuid3.setText(skyBeaconList.get(2).getProximityUUID());
			}
			if (skyBeaconList.get(2).getMajor() != DefaultStaticValues.DEFAULT_SKY_BEACON_MAJOR_FALSE) {
				major3.setText(String.valueOf(skyBeaconList.get(2).getMajor()));
			}
			if (skyBeaconList.get(2).getMinor() != DefaultStaticValues.DEFAULT_SKY_BEACON_MINOR_FALSE) {
				minor3.setText(String.valueOf(skyBeaconList.get(2).getMinor()));
			}
			if (skyBeaconList.get(2).getMeasuredPower() != DefaultStaticValues.DEFAULT_SKY_BEACON_MEASURED_POWER_FALSE) {
				measuredPower3.setText(String.valueOf(skyBeaconList.get(2)
						.getMeasuredPower()));
			}
			if (SKYBeaconPower.getPower(skyBeaconList.get(2).getTxpower()) != DefaultStaticValues.DEFAULT_SKY_BEACON_TXPOWER_FALSE) {
				txPower3.setText(String.valueOf(skyBeaconList.get(2)
						.getTxpower()));
			}
			if (skyBeaconList.get(2).isEncrypted() == 1) {
				encryptSB3.setChecked(true);
			} else {
				encryptSB3.setChecked(false);
			}
		}

		if (skyBeaconMultiIDs.getIntervalMillisecond() != DefaultStaticValues.DEFAULT_SKY_BEACON_INTERVAL_MILLISECOND_FALSE) {
			interval.setText(String.valueOf(skyBeaconMultiIDs
					.getIntervalMillisecond()));
		}
		if (!skyBeaconMultiIDs.getDeviceName().equals(
				DefaultStaticValues.DEFAULT_SKY_BEACON_DEVICE_NAME)) {
			deviceName.setText(skyBeaconMultiIDs.getDeviceName());
		}
		if (skyBeaconMultiIDs.isLocked() == 1) {
			lockSB.setChecked(true);
		} else {
			lockSB.setChecked(false);
		}
		if (skyBeaconMultiIDs.getCellsSituation() != null) {
			if (SKYBeaconBroadcastID.getInt(skyBeaconMultiIDs
					.getCellsSituation().getUsedCellsSituation()[0]) != -1) {
				grid1.setText(String.valueOf(SKYBeaconBroadcastID
						.getInt(skyBeaconMultiIDs.getCellsSituation()
								.getUsedCellsSituation()[0])));
			}
			if (SKYBeaconBroadcastID.getInt(skyBeaconMultiIDs
					.getCellsSituation().getUsedCellsSituation()[1]) != -1) {
				grid2.setText(String.valueOf(SKYBeaconBroadcastID
						.getInt(skyBeaconMultiIDs.getCellsSituation()
								.getUsedCellsSituation()[1])));
			}
			if (SKYBeaconBroadcastID.getInt(skyBeaconMultiIDs
					.getCellsSituation().getUsedCellsSituation()[2]) != -1) {
				grid3.setText(String.valueOf(SKYBeaconBroadcastID
						.getInt(skyBeaconMultiIDs.getCellsSituation()
								.getUsedCellsSituation()[2])));
			}
			if (SKYBeaconBroadcastID.getInt(skyBeaconMultiIDs
					.getCellsSituation().getUsedCellsSituation()[3]) != -1) {
				grid4.setText(String.valueOf(SKYBeaconBroadcastID
						.getInt(skyBeaconMultiIDs.getCellsSituation()
								.getUsedCellsSituation()[3])));
			}
			if (SKYBeaconBroadcastID.getInt(skyBeaconMultiIDs
					.getCellsSituation().getUsedCellsSituation()[4]) != -1) {
				grid5.setText(String.valueOf(SKYBeaconBroadcastID
						.getInt(skyBeaconMultiIDs.getCellsSituation()
								.getUsedCellsSituation()[4])));
			}
			if (SKYBeaconBroadcastID.getInt(skyBeaconMultiIDs
					.getCellsSituation().getUsedCellsSituation()[5]) != -1) {
				grid6.setText(String.valueOf(SKYBeaconBroadcastID
						.getInt(skyBeaconMultiIDs.getCellsSituation()
								.getUsedCellsSituation()[5])));
			}
			if (SKYBeaconBroadcastID.getInt(skyBeaconMultiIDs
					.getCellsSituation().getUsedCellsSituation()[6]) != -1) {
				grid7.setText(String.valueOf(SKYBeaconBroadcastID
						.getInt(skyBeaconMultiIDs.getCellsSituation()
								.getUsedCellsSituation()[6])));
			}
			if (SKYBeaconBroadcastID.getInt(skyBeaconMultiIDs
					.getCellsSituation().getUsedCellsSituation()[7]) != -1) {
				grid8.setText(String.valueOf(SKYBeaconBroadcastID
						.getInt(skyBeaconMultiIDs.getCellsSituation()
								.getUsedCellsSituation()[7])));
			}
			if (SKYBeaconBroadcastID.getInt(skyBeaconMultiIDs
					.getCellsSituation().getUsedCellsSituation()[8]) != -1) {
				grid9.setText(String.valueOf(SKYBeaconBroadcastID
						.getInt(skyBeaconMultiIDs.getCellsSituation()
								.getUsedCellsSituation()[8])));
			}
		}
	}

	public SKYBeaconConfigMultiIDs getView() {
		SKYBeaconConfigMultiIDs skyBeaconConfigMultiIDs = new SKYBeaconConfigMultiIDs();
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
		if (encryptSB1.isChecked()) {
			skyBeaconConfig1.setEncrypted(1);
		} else {
			skyBeaconConfig1.setEncrypted(0);
		}
		SKYBeaconConfig skyBeaconConfig2 = new SKYBeaconConfig();
		if (!uuid2.getText().toString().equals("")) {
			skyBeaconConfig2.setProximityUUID(uuid2.getText().toString());
		}
		if (!major2.getText().toString().equals("")) {
			skyBeaconConfig2.setMajor(Integer.valueOf(major2.getText()
					.toString()));
		}
		if (!minor2.getText().toString().equals("")) {
			skyBeaconConfig2.setMinor(Integer.valueOf(minor2.getText()
					.toString()));
		}
		if (!measuredPower2.getText().toString().equals("")) {
			skyBeaconConfig2.setMeasuredPower(Integer.valueOf(measuredPower2
					.getText().toString()));
		}
		if (!txPower2.getText().toString().equals("")) {
			skyBeaconConfig2.setTxpower(SKYBeaconPower.getPower(Integer
					.valueOf(txPower2.getText().toString())));
		}
		if (encryptSB2.isChecked()) {
			skyBeaconConfig2.setEncrypted(1);
		} else {
			skyBeaconConfig2.setEncrypted(0);
		}
		SKYBeaconConfig skyBeaconConfig3 = new SKYBeaconConfig();
		if (!uuid3.getText().toString().equals("")) {
			skyBeaconConfig3.setProximityUUID(uuid3.getText().toString());
		}
		if (!major3.getText().toString().equals("")) {
			skyBeaconConfig3.setMajor(Integer.valueOf(major3.getText()
					.toString()));
		}
		if (!minor3.getText().toString().equals("")) {
			skyBeaconConfig3.setMinor(Integer.valueOf(minor3.getText()
					.toString()));
		}
		if (!measuredPower3.getText().toString().equals("")) {
			skyBeaconConfig3.setMeasuredPower(Integer.valueOf(measuredPower3
					.getText().toString()));
		}
		if (!txPower3.getText().toString().equals("")) {
			skyBeaconConfig3.setTxpower(SKYBeaconPower.getPower(Integer
					.valueOf(txPower3.getText().toString())));
		}
		if (encryptSB3.isChecked()) {
			skyBeaconConfig3.setEncrypted(1);
		} else {
			skyBeaconConfig3.setEncrypted(0);
		}
		skyBeaconConfigMultiIDs.setConfigBeaconsID1(skyBeaconConfig1);
		skyBeaconConfigMultiIDs.setConfigBeaconsID2(skyBeaconConfig2);
		skyBeaconConfigMultiIDs.setConfigBeaconsID3(skyBeaconConfig3);
		if (!interval.getText().toString().equals("")) {
			skyBeaconConfigMultiIDs.setIntervalMillisecond(Integer
					.valueOf(interval.getText().toString()));
		}
		if (!key.getText().toString().equals("")) {
			skyBeaconConfigMultiIDs.setLockedKey(key.getText().toString());
		}
		if (!deviceName.getText().toString().equals("")) {
			skyBeaconConfigMultiIDs.setDeviceName(deviceName.getText()
					.toString());
		}
		if (lockSB.isChecked()) {
			skyBeaconConfigMultiIDs.setLocked(1);
		} else {
			skyBeaconConfigMultiIDs.setLocked(0);
		}

		SKYBeaconBroadcastID skyBeaconBroadcastID1 = SKYBeaconBroadcastID.BROADCAST_ID_FALSE;
		if (!grid1.getText().toString().equals("")) {
			skyBeaconBroadcastID1 = SKYBeaconBroadcastID.getBroadcastID(Integer
					.valueOf(grid1.getText().toString()));
		}
		SKYBeaconBroadcastID skyBeaconBroadcastID2 = SKYBeaconBroadcastID.BROADCAST_ID_FALSE;
		if (!grid2.getText().toString().equals("")) {
			skyBeaconBroadcastID2 = SKYBeaconBroadcastID.getBroadcastID(Integer
					.valueOf(grid2.getText().toString()));
		}
		SKYBeaconBroadcastID skyBeaconBroadcastID3 = SKYBeaconBroadcastID.BROADCAST_ID_FALSE;
		if (!grid3.getText().toString().equals("")) {
			skyBeaconBroadcastID3 = SKYBeaconBroadcastID.getBroadcastID(Integer
					.valueOf(grid3.getText().toString()));
		}
		SKYBeaconBroadcastID skyBeaconBroadcastID4 = SKYBeaconBroadcastID.BROADCAST_ID_FALSE;
		if (!grid4.getText().toString().equals("")) {
			skyBeaconBroadcastID4 = SKYBeaconBroadcastID.getBroadcastID(Integer
					.valueOf(grid4.getText().toString()));
		}
		SKYBeaconBroadcastID skyBeaconBroadcastID5 = SKYBeaconBroadcastID.BROADCAST_ID_FALSE;
		if (!grid5.getText().toString().equals("")) {
			skyBeaconBroadcastID5 = SKYBeaconBroadcastID.getBroadcastID(Integer
					.valueOf(grid5.getText().toString()));
		}
		SKYBeaconBroadcastID skyBeaconBroadcastID6 = SKYBeaconBroadcastID.BROADCAST_ID_FALSE;
		if (!grid6.getText().toString().equals("")) {
			skyBeaconBroadcastID6 = SKYBeaconBroadcastID.getBroadcastID(Integer
					.valueOf(grid6.getText().toString()));
		}
		SKYBeaconBroadcastID skyBeaconBroadcastID7 = SKYBeaconBroadcastID.BROADCAST_ID_FALSE;
		if (!grid7.getText().toString().equals("")) {
			skyBeaconBroadcastID7 = SKYBeaconBroadcastID.getBroadcastID(Integer
					.valueOf(grid7.getText().toString()));
		}
		SKYBeaconBroadcastID skyBeaconBroadcastID8 = SKYBeaconBroadcastID.BROADCAST_ID_FALSE;
		if (!grid8.getText().toString().equals("")) {
			skyBeaconBroadcastID8 = SKYBeaconBroadcastID.getBroadcastID(Integer
					.valueOf(grid8.getText().toString()));
		}
		SKYBeaconBroadcastID skyBeaconBroadcastID9 = SKYBeaconBroadcastID.BROADCAST_ID_FALSE;
		if (!grid9.getText().toString().equals("")) {
			skyBeaconBroadcastID9 = SKYBeaconBroadcastID.getBroadcastID(Integer
					.valueOf(grid9.getText().toString()));
		}
		SKYBeaconCellsSituation skyBeaconCellsSituation = new SKYBeaconCellsSituation(
				skyBeaconBroadcastID1, skyBeaconBroadcastID2,
				skyBeaconBroadcastID3, skyBeaconBroadcastID4,
				skyBeaconBroadcastID5, skyBeaconBroadcastID6,
				skyBeaconBroadcastID7, skyBeaconBroadcastID8,
				skyBeaconBroadcastID9);
		skyBeaconConfigMultiIDs.setCellsSituation(skyBeaconCellsSituation);
		if (configTime.isChecked()) {
			skyBeaconConfigMultiIDs.setSetTime(true);
		}
		if (configReset.isChecked()) {
			skyBeaconConfigMultiIDs.setReset(true);
		}

		return skyBeaconConfigMultiIDs;
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
				final SKYBeaconMultiIDs skyBeaconMultiIDsTmp = skyBeaconMultiIDs;
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						stateInfo.setText("connect success! multiBeacon");
						setView(skyBeaconMultiIDsTmp);
					}
				});
			}

			@Override
			public void onConnectedSuccess(SKYBeacon skyBeacon) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						stateInfo.setText("connect success! singleBeacon");
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
		SKYBeaconMultiIDs skyBeaconMultiIDs = new SKYBeaconMultiIDs(
				deviceAddress);
		skyBeaconCommunication.connect(skyBeaconMultiIDs,
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
			SKYBeaconConfigMultiIDs skyBeaconConfigMultiIDs = getView();
			skyBeaconCommunication.configSKYBeaconMultiIDs(
					skyBeaconConfigMultiIDs, new ConfigCallback() {

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
