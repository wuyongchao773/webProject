package com.product.utils;

import java.util.UUID;

/***
 * @Title id生成工具类
 * @author wuyongchao
 * @date 2018-11-04
 */
public class IdGenerator {
	
	public static String makeUUidGenerator(){
		UUID uuid = UUID.randomUUID();
		StringBuffer sb = new StringBuffer();
		String[] arr = uuid.toString().split("-");
		for(int i = 0 ; i < arr.length ; i++){
			sb.append(arr[i].trim());
		}
		return sb.toString();
	}

}
