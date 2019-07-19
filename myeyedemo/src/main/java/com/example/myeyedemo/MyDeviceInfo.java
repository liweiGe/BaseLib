package com.example.myeyedemo;

import com.basic.G;
import com.lib.sdk.struct.SDBDeviceInfo;

public class MyDeviceInfo {
	public SDBDeviceInfo dbInfo;
	public int nState = -1;

	public String GetDevId() {
		if (dbInfo == null) {
			return "";
		}
		return G.ToString(dbInfo.st_0_Devmac);
	}

	public String GetDevName() {
		if (dbInfo == null) {
			return "";
		}
		return G.ToString(dbInfo.st_1_Devname);
	}
}
