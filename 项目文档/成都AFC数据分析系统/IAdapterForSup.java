package com.afc.cmn.interfaces.adapter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.afc.cmn.entity.adapter.DeviceStatusJson;
import com.afc.cmn.entity.adapter.StationStatusJson;
import com.afc.cmn.entity.adapter.SupDevice;
import com.afc.cmn.entity.adapter.SupLine;
import com.afc.cmn.entity.adapter.SupStation;
import com.afc.cmn.entity.sup.PassengerMount;
import com.afc.cmn.entity.sup.RmiAIInfo;
import com.afc.cmn.entity.sup.message.Message3002;
import com.afc.cmn.entity.sup.message.Message3007;
import com.afc.cmn.entity.sup.message.Message8009;
import com.afc.cmn.entity.sup.message.Message8013;
import com.afc.cmn.entity.sup.message.Message8017;
import com.afc.cmn.entity.sup.message.Message8018;
import com.afc.cmn.entity.sup.message.Message8021;
import com.afc.cmn.entity.sup.message.Message8027;




public interface IAdapterForSup  extends Remote {

	//获取线路信息
	public List<SupLine> queryLinesFromMemdb() throws RemoteException;
	//获取车站信息
	public List<SupStation> queryStationsFromMemdb(List<String> lineid) throws RemoteException;
	//获取设备信息
	public List<SupDevice> queryDevicesFromMemdb(List<String> stationid) throws RemoteException;
	//获取车站名字
	public SupStation getSupStationByStationId(String lineid, String stationid) throws RemoteException;
	//获取设备名字
	public SupDevice getSupDeviceByDeviceId(String deviceid) throws RemoteException;
	
	//获取事件信息
	public Map<String,Message8009> queryEvent(List<String> deviceids) throws RemoteException; 
	public Message8009 queryEvent(String deviceid, Date buzDay) throws RemoteException; 
	//获取状态信息
	public Message3007 queryStatus(List<String>  deviceids) throws RemoteException;
	public Message3007 queryStatus(String deviceid) throws RemoteException;
	//获取TVM钱箱票箱信息
	public Map<String,Message8017> queryTvmBox(List<String> deviceids) throws RemoteException;
	public Message8017 queryTvmBox(String deviceid) throws RemoteException;
	//获取TVM钱箱票箱信息
	public List<Message8027> queryTvmBox2(List<String> deviceids) throws RemoteException;
	public Message8027 queryTvmBox2(String deviceid) throws RemoteException;
	//GATE和BOM钱箱信息
	public List<Message8018> queryGateBomBox(List<String> deviceids) throws RemoteException;
	public Message8018 queryGateBomBox(String deviceid) throws RemoteException;
	//获取参数版本信息
	public Map<String, Message8013> queryEodVersion(List<String> deviceids) throws RemoteException; 
	public Message8013 queryEodVersion(String deviceid)  throws RemoteException;
	//获取软件版本信息
	public Map<String, Message8021> querySoftVersion(List<String> deviceids) throws RemoteException;
	public Message8021 querySoftVersion(String deviceid)  throws RemoteException;
	//查询车站模式
	public List<Message3002> queryStationMode(String linestationid, Date buzday) throws RemoteException;
	public List<Message3002> queryStationMode(String stationid) throws RemoteException;
	public Map<String,List<Message3002>> queryStationMode(Date buzday) throws RemoteException;
	public List<Message3002> queryStationModeList(String linestationid, Date buzday) throws RemoteException;
		
	//public Message3002 queryStationMode(String deviceid)  throws RemoteException;
	//public Map<String,Message3002> queryStationMode(List<String> deviceids)  throws RemoteException;
	//为监控提供位置判断
	public boolean isSC()  throws RemoteException;
	//查询车站综合状态
	public List<StationStatusJson> queryStationComStatus(List<String> stationdeviceid)  throws RemoteException;
	public StationStatusJson queryStationComStatus(String lineid, String stationid)  throws RemoteException;
	
	//查询设备综合状态
	public DeviceStatusJson queryDeviceComStatus(String deviceid)  throws RemoteException;
	
	//获取本地DEVICEID
	public String getLocalDeviceId() throws RemoteException;
	
	//ISCS使用
	//车站客流汇总
	public RmiAIInfo getStationPFSummary(Date buzDay, String lineId, String stationId) throws RemoteException;
	//线路客流汇总
	public RmiAIInfo getLinePFSummary(Date buzDay, String lineId) throws RemoteException;
	
	//sup
	public List<PassengerMount> getLinePFSummary(Date buzDay, String lineId, int interval) throws RemoteException;
	public List<PassengerMount> getLinePFByTimeDiv(Date buzDay, String lineId, int interval) throws RemoteException;
	public List<PassengerMount> getStationPFByTimeDiv(Date buzDay, String stationId, int interval) throws RemoteException;
	public List<PassengerMount> getStationPFByTicketSum(Date buzDay, String stationId) throws RemoteException;
			
}