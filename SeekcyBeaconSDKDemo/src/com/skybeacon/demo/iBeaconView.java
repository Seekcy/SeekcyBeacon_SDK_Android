package com.skybeacon.demo;

public class iBeaconView {
	public String mac = "";
	public int rssi = -1;
	public String detailInfo = "";
	public boolean isMultiIDs = false;

	public void reset(iBeaconView beacon) {
		this.mac = beacon.mac;
		this.rssi = beacon.rssi;
		this.detailInfo = beacon.detailInfo;
		this.isMultiIDs = beacon.isMultiIDs;
	}
}
