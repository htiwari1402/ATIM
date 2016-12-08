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
	 @RequestMapping("/logout")
	 public String logout( HttpSession session ) throws Exception 
	 {
		 	session.invalidate();
		 	return "logout";
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
		    cpt = cpt.replace(";\n", ";");
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
	 
	 @RequestMapping("/genXML2")
	 @ResponseBody
	 public int genXML2(@RequestParam("control_param_text") String cpt,
			 			@RequestParam("src_repo") String src_repo, @RequestParam("tgt_repo") String tgt_repo) 
			 			throws FileNotFoundException, IOException, ParseException
	 {
		    cpt = cpt.replace(";\n", ";");
		    String[] lines = cpt.split(";");
		 	JSONParser parser = new JSONParser();
		 	Object obj = parser.parse(new FileReader("/etc/atim/atim.json"));
			JSONObject jsonObject = (JSONObject) obj;
			String baseDir = (String)jsonObject.get("base_dir");
			String xmlFileDg = (String)jsonObject.get("control_file_oi");
			String fileName = baseDir+"/"+xmlFileDg;
			FileWriter file = new FileWriter(fileName);
			String xmlText = "<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE IMPORTPARAMS SYSTEM 'impcntl.dtd'><!--IMPORTPARAMS This inputs the options and inputs required for import operation --><!--FOLDERMAP matches the folders in the imported file with the folders in the target repository --><IMPORTPARAMS CHECKIN_AFTER_IMPORT='YES' CHECKIN_COMMENTS='Imported_test'> ";
            for(String line: lines)
            {
            		String[] folders = line.split(",");
            		String srcFolder = folders[0];
            		String destFolder = folders[1];
            		xmlText += "<FOLDERMAP SOURCEFOLDERNAME='"+srcFolder+"' SOURCEREPOSITORYNAME='"+src_repo+"' TARGETFOLDERNAME='"+destFolder+"' TARGETREPOSITORYNAME='"+tgt_repo+"'/>";
            }
            xmlText+= "<!--Import will only import the objects in the selected types in TYPEFILTER node --><!--TYPENAME type name to import. This should conforming to the element name in powermart.dtd, e.g. SOURCE, TARGET and etc.--><TYPEFILTER TYPENAME='SOURCE'/><TYPEFILTER TYPENAME='TARGET'/><TYPEFILTER TYPENAME='MAPPLET'/><TYPEFILTER TYPENAME='MAPPING'/><TYPEFILTER TYPENAME='TRANSFORMATION'/><TYPEFILTER TYPENAME='CONFIG'/><TYPEFILTER TYPENAME='TASK'/><TYPEFILTER TYPENAME='SESSION'/><TYPEFILTER TYPENAME='SCHEDULER'/><TYPEFILTER TYPENAME='WORKFLOW'/><TYPEFILTER TYPENAME='SCHEDULER'/><TYPEFILTER TYPENAME='WORKLET'/><!--RESOLVECONFLICT allows to specify resolution for conflicting objects during import. The combination of specified child nodes can be supplied --><RESOLVECONFLICT><!--TYPEOBJECT allows objects of certain type to apply replace/reuse upon conflict--><TYPEOBJECT OBJECTTYPENAME='WORKFLOW' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='WORKLET' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='SESSION' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='MAPPING' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='MAPPLET' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Source definition' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Target definition' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Expression' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Filter' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Aggregator' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Rank' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Normalizer' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Router' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Sequence' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Sorter' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='update strategy' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Custom Transformation' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Lookup Procedure' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Transaction control' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Stored Procedure' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='External Procedure' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Joiner' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='SessionConfig' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Email' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Command' RESOLUTION='REPLACE'/><TYPEOBJECT OBJECTTYPENAME='Scheduler' RESOLUTION='REPLACE'/></RESOLVECONFLICT></IMPORTPARAMS>";

			
	  		file.write(xmlText);
	  		file.flush();
	  		file.close();
	  		return 1;
	 }

}
