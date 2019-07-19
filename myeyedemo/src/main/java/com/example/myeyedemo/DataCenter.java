package com.example.myeyedemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.basic.G;
import com.lib.cloud.SMediaFileInfo;
import com.lib.sdk.struct.H264_DVR_DEVICEINFO;
import com.lib.sdk.struct.SDBDeviceInfo;
import com.lib.sdk.struct.SDK_ChannelNameConfigAll;

public class DataCenter {
	public static DataCenter s_instance = null;
	List<MyDeviceInfo> _devList = new ArrayList<MyDeviceInfo>();
	public String strOptDevID;
	public int nOptChannel;
	public int mLoginSType;

	public SDBDeviceInfo currentSDBDeviceInfo;
	public TreeMap<Integer, Boolean> mFileMap = new TreeMap<Integer, Boolean>();
	
	public Map<SDBDeviceInfo, List<SMediaFileInfo>> deviceVideo 
	= new HashMap<SDBDeviceInfo, List<SMediaFileInfo>>();
	
	public static DataCenter Instance() {
		if (s_instance == null) {
			s_instance = new DataCenter();
		}

		return s_instance;
	}

	public static void Clear() {
		s_instance = null;
		System.gc();
	}

	private DataCenter() {
	}

	public List<MyDeviceInfo> GetDevList() {
		return _devList;
	}


	//
	public List<MyDeviceInfo> UpdateData(byte[] pData) {
		_devList.clear();
		if (null == pData || pData.length <= 0) {
			return _devList;
		}
		SDBDeviceInfo info = new SDBDeviceInfo();
		int nItemLen = G.Sizeof(info);
		int nCount = pData.length / nItemLen;
		SDBDeviceInfo infos[] = new SDBDeviceInfo[nCount];
		for (int i = 0; i < nCount; ++i) {
			infos[i] = new SDBDeviceInfo();
		}
		G.BytesToObj(infos, pData);
		for (int i = 0; i < nCount; ++i) {
			MyDeviceInfo myDev = new MyDeviceInfo();
			myDev.dbInfo = infos[i]; 
			_devList.add(myDev);
		}
		return _devList;
	}

	private String _lastDevID = "";
	private MyDeviceInfo _lastDBDevInfo = null;

	public MyDeviceInfo GetDBDeviceInfo(String devID) {
		if (_lastDevID.equals(devID)) {
			return _lastDBDevInfo;
		}
		for (MyDeviceInfo vv : _devList) {
			if (vv.GetDevId().equals(devID)) {
				_lastDBDevInfo = vv;
				_lastDevID = devID;
				return vv;
			}
		}
		return null;
	}

	public SDK_ChannelNameConfigAll ChannelNameConfigAll(byte[] pData) {
		if (null == pData || pData.length <= 0) {
			return null;
		}
		SDK_ChannelNameConfigAll channel = new SDK_ChannelNameConfigAll();
		G.BytesToObj(channel, pData);
		return channel;
	}


	public int getmLoginSType() {
		return mLoginSType;
	}

	public void setmLoginSType(int mLoginSType) {
		this.mLoginSType = mLoginSType;
	}

	public Map<SDBDeviceInfo, List<SMediaFileInfo>> getDeviceVideo() {
		return deviceVideo;
	}

}
