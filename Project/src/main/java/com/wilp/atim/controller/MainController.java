package com.wilp.atim.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	 @RequestMapping("/home")
	 public String execShell(/*@RequestParam("")*/) throws Exception {
	    	
	      return "index";
	    }
	 
	 @RequestMapping("/auth")
	 @ResponseBody
	 public int auth(   @RequestParam("username") String username, 
			 			@RequestParam("password") String password
	  ) throws Exception 
	 {
		 	JSONParser parser = new JSONParser();
		 	Object obj = parser.parse(new FileReader("/etc/atim/atim_auth.json"));
 			JSONObject jsonObject = (JSONObject) obj;
 			String usr = (String) jsonObject.get("username");
 			String pwd = (String) jsonObject.get("password");
 			if(usr.equals(username) && pwd.equals(password))
 			{
 				return 1;
 			}
 			else
 			{
 				return 0;
 			}
	 }

}
