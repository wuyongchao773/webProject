package com.product.listener;

import java.net.SocketException;
import java.net.UnknownHostException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.product.config.WechatConfig;
import com.product.http.HttpUtils;
import com.product.redis.RadisPoolUtils;
import com.product.utils.WindowConfig;

import net.sf.json.JSONObject;

public class WebRunListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent sce) {
		try {
			System.out.println("ip地址:=================================="+WindowConfig.getIp());
			System.out.println("名称:=================================="+WindowConfig.getConputName());
			System.out.println("jdk版本:=================================="+WindowConfig.getJdkVersion());
			System.out.println("jdk安装目录:=================================="+WindowConfig.getInstallDir());
			System.out.println("系统版本:=================================="+WindowConfig.getComputVersion());
			System.out.println("用户名称:=================================="+WindowConfig.getUserName());
			System.out.println("mac地址:=================================="+WindowConfig.getMacAddress());	
			//系统启动的时候自动获取access_token
			System.out.println("开始获取access_token");
			String responseJson = HttpUtils.HttpsGet(WechatConfig.accessTokenUrl,"UTF-8");
			JSONObject tokenObject = JSONObject.fromObject(responseJson);
			String accessToken = String.valueOf(tokenObject.get("access_token"));
			int expiresIn = Integer.parseInt(String.valueOf(tokenObject.get("expires_in")));
			//将accessToken存储到radis中
			RadisPoolUtils.set("access_token",accessToken);
			//设置失效时间
			RadisPoolUtils.expire("access_token",expiresIn);
			System.out.println("成功获取access_token");
			System.out.println("获取的access_token是："+accessToken);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
