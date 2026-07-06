package com.cym.controller.adminPage;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;

import com.cym.sqlhelper.bean.Page;

@Controller
@Mapping("/adminPage/userPage/")
public class CdnController {

	public static String host = "https://my.freecdn.vip";
//	public static String host = "http://127.0.0.1:8000";

	@Mapping("*")
	public ModelAndView index(ModelAndView modelAndView, Context ctx) {

		String url = host + ctx.path().replace("/adminPage", "") + "?machineId=" + ctx.param("machineId") + "&pageSize=20";

		modelAndView.put("url", url);
		modelAndView.view("/adminPage/userPage/index.html");
		return modelAndView;
	}

}
