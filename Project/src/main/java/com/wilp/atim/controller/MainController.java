package com.wilp.atim.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	 @RequestMapping("/home")
	 public String home(/*@RequestParam("")*/) throws Exception {    	
	      return "index";
	    }
	 
	 @RequestMapping("/migration")
	 public String migration(/*@RequestParam("")*/HttpSession session) throws Exception {	  
		 if((String)session.getAttribute("username") != null && new String((String)session.getAttribute("username")).length() > 0 )
	      {
			 return "migration";
		  }
		  else
		  {
			 return "invalidSession";
		  }
	    }
	 
	 @RequestMapping("/auth")
	 @ResponseBody
	 public int auth(   @RequestParam("username") String username, 
			 			@RequestParam("password") String password,
			 			HttpSession session
	  ) throws Exception 
	 {
		 	JSONParser parser = new JSONParser();
		 	Object obj = parser.parse(new FileReader("/etc/atim/atim_auth.json"));
 			JSONObject jsonObject = (JSONObject) obj;
 			String usr = (String) jsonObject.get("username");
 			String pwd = (String) jsonObject.get("password");
 			if(usr.equals(username) && pwd.equals(password))
 			{
 				session.setAttribute("username", username);
 				return 1;
 			}
 			else
 			{
 				return 0;
 			}
	 }
	 
	 @RequestMapping("/getRepo")
	 @ResponseBody
	 public String getRepo() throws FileNotFoundException, IOException, ParseException
	 {
		 	String options="";
		 	JSONParser parser = new JSONParser();
		 	Object obj = parser.parse(new FileReader("/etc/atim/atim.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray rep = (JSONArray) jsonObject.get("repositories");
			Iterator<String> i = rep.iterator();
			while(i.hasNext())
			{
				String val = i.next(); 
				String option = "<option value='"+val+"'>"+val+"</option>";
				options+= option;
			}
			return options;
	 }
	 
	 @RequestMapping("/getDom")
	 @ResponseBody
	 public String getDom() throws FileNotFoundException, IOException, ParseException
	 {
		 	String options="";
		 	JSONParser parser = new JSONParser();
		 	Object obj = parser.parse(new FileReader("/etc/atim/atim.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray rep = (JSONArray) jsonObject.get("domains");
			Iterator<String> i = rep.iterator();
			while(i.hasNext())
			{
				String val = i.next(); 
				String option = "<option value='"+val+"'>"+val+"</option>";
				options+= option;
			}
			return options;
	 }
	 
	 @RequestMapping("/get_tkt_no")
	 @ResponseBody
	 public long get_tkt_no() throws FileNotFoundException, IOException, ParseException
	 {
		 	JSONParser parser = new JSONParser();
		 	Object obj = parser.parse(new FileReader("/etc/atim/atim_tkt.json"));
			JSONObject jsonObject = (JSONObject) obj;
			long tkt = (long)jsonObject.get("tkt_no");
			
			return tkt;
	 }
	 @RequestMapping("/genXML")
	 @ResponseBody
	 public int genXML(@RequestParam("control_param_text") String cpt) throws FileNotFoundException, IOException, ParseException
	 {
		    String[] lines = cpt.split(";");
		 	JSONParser parser = new JSONParser();
		 	Object obj = parser.parse(new FileReader("/etc/atim/atim.json"));
			JSONObject jsonObject = (JSONObject) obj;
			String baseDir = (String)jsonObject.get("base_dir");
			String xmlFileDg = (String)jsonObject.get("control_file_dg");
			String fileName = baseDir+"/"+xmlFileDg;
			FileWriter file = new FileWriter(fileName);
			String xmlText = "<?xml version='1.0' encoding='UTF-8'?>"+
			"<!DOCTYPE DEPLOYPARAMS SYSTEM 'depcntl.dtd'>"+
			"<DEPLOYPARAMS DEFAULTSERVERNAME='server' COPYPROGRAMINFO='YES' COPYMAPVARPERVALS='NO' COPYWFLOWVARPERVALS='NO' COPYWFLOWSESSLOGS='NO' COPYDEPENDENCY='YES' LATESTVERSIONONLY='YES' CHECKIN_COMMENTS='PMREP_SAMPLE_DEPLOY' RETAINGENERATEDVAL='YES' RETAINSERVERNETVALS='YES'>"+
			"<DEPLOYGROUP CLEARSRCDEPLOYGROUP='NO'>";
            for(String line: lines)
            {
            		String[] folders = line.split(",");
            		String srcFolder = folders[0];
            		String destFolder = folders[1];
            		xmlText += "<OVERRIDEFOLDER SOURCEFOLDERNAME='"+srcFolder+"' SOURCEFOLDERTYPE='LOCAL' TARGETFOLDERNAME='"+destFolder+"' TARGETFOLDERTYPE='LOCAL'/>";
            }
            xmlText+= "</DEPLOYGROUP>"+"</DEPLOYPARAMS>";

			
	  		file.write(xmlText);
	  		file.flush();
	  		file.close();
	  		return 1;
	 }

}
