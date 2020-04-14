package com.product.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class WindowConfig {

	private static InetAddress inetAddress = null;
	
	//获得InetAddress
	public static InetAddress getInetAddress() throws UnknownHostException{
		return inetAddress = InetAddress.getLocalHost();
	}
	
	//获得地址
	public static String getIp() throws UnknownHostException{
		return getInetAddress().getHostAddress();
	}
	
	//获得名称
	public static String getConputName(){
		return System.getProperty("os.name");
	}
	
	//获得jdk版本
	public static String getJdkVersion(){
		return System.getProperty("java.version");
	}
	
	//获得安装目录
	public static String getInstallDir(){
		return System.getProperty("java.home");
	}
	
	//获得系统版本
	public static String getComputVersion(){
		return System.getProperty("os.version");
	}
	
	//获得用户的账户名称
	public static String getUserName(){
		return System.getProperty("user.name");
	} 
	
	//获得本机mac地址
	public static String getMacAddress() throws SocketException, UnknownHostException{
		byte[] mac = NetworkInterface.getByInetAddress(getInetAddress()).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString();
	}
}
