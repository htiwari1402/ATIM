package com.wilp.atim.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wilp.atim.domain.MigrationParams;

@Controller
public class ShellController {

    @RequestMapping("/execShell")
    public String execShell(
    							@RequestParam("tkt_no") int tkt_no,
    							@RequestParam("src_domain") String src_domain,
    							@RequestParam("tgt_domain") String tgt_domain,
    							@RequestParam("src_repo") String src_repo,
    							@RequestParam("tgt_repo") String tgt_repo,
    							@RequestParam("dev_group") String dev_group,
    							@RequestParam("email") String email,
    							@RequestParam("emailGroup") String emailGroup
    						)
            throws Exception {
    	JSONParser parser = new JSONParser();


    		Object obj = parser.parse(new FileReader("/etc/atim/atim.json"));
    		JSONObject jsonObject = (JSONObject) obj;
    		String migrationScript = (String) jsonObject.get("migration_script");
    		
      
      final String[] execCommand = { migrationScript, src_repo, tgt_repo, src_domain,tgt_domain,dev_group,emailGroup,""+tkt_no };
      String S = "";

      final ProcessBuilder builder = new ProcessBuilder(execCommand);
      builder.redirectErrorStream(false);
      final Process process = builder.start();

      try (BufferedOutputStream stdin = new BufferedOutputStream(process.getOutputStream())) {
      }

      try (
    		  	BufferedInputStream bufferedInputStream = new BufferedInputStream(process.getInputStream());
    		  	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	  ) 
    {
        int bytesRead = 0;
        final byte[] buffer = new byte[1024];
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
           S = new String(buffer);
        }

        process.waitFor();
     }
      try {
    	JSONObject tktObj = new JSONObject();
    	tktObj.put("tkt_no", tkt_no+1);
  		FileWriter file = new FileWriter("/etc/atim/atim_tkt.json");
  		file.write(tktObj.toJSONString());
  		file.flush();
  		file.close();

  			} 
      catch (IOException e) {
  		e.printStackTrace();
  	}
        Object obj3 = parser.parse(new FileReader("/etc/atim/atim_signatures.json"));
		JSONObject jsonObject3 = (JSONObject) obj3;
		String sign1 = (String) jsonObject3.get("sign1");
		String sign2 = (String) jsonObject3.get("sign2");
		String sign3 = (String) jsonObject3.get("sign3");
      	if(S.contains(sign1) || S.contains(sign2) || S.contains(sign3))
      	{
      		return "mig_suc";
      	}
      	else
      	{
      		return "mig_error";
      	}
    }
    
    
    
    @RequestMapping("/execFileShell")
    public String execFileShell(
    							@RequestParam("file_tkt_no") int tkt_no,
    							@RequestParam("dev_group") String dev_group,
    							@RequestParam("emailGroup") String emailGroup
    						)
            throws Exception {
    	JSONParser parser = new JSONParser();


    		Object obj = parser.parse(new FileReader("/etc/atim/atim.json"));
    		JSONObject jsonObject = (JSONObject) obj;
    		String migrationScript = (String) jsonObject.get("unix_migration_script");
    		
      
      final String[] execCommand = {migrationScript, dev_group, emailGroup, ""+tkt_no };
      String S = "";

      final ProcessBuilder builder = new ProcessBuilder(execCommand);
      builder.redirectErrorStream(false);
      final Process process = builder.start();

      try (BufferedOutputStream stdin = new BufferedOutputStream(process.getOutputStream())) {
      }

      try (
    		  	BufferedInputStream bufferedInputStream = new BufferedInputStream(process.getInputStream());
    		  	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	  ) 
    {
        int bytesRead = 0;
        final byte[] buffer = new byte[1024];
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
           S = new String(buffer);
        }

        process.waitFor();
     }
      try {
    	JSONObject tktObj = new JSONObject();
    	tktObj.put("tkt_no", tkt_no+1);
  		FileWriter file = new FileWriter("/etc/atim/atim_tkt.json");
  		file.write(tktObj.toJSONString());
  		file.flush();
  		file.close();

  			} 
      catch (IOException e) {
  		e.printStackTrace();
  	}
        Object obj3 = parser.parse(new FileReader("/etc/atim/atim_signatures.json"));
		JSONObject jsonObject3 = (JSONObject) obj3;
		String sign1 = (String) jsonObject3.get("sign1");
		String sign2 = (String) jsonObject3.get("sign2");
		String sign3 = (String) jsonObject3.get("sign3");
		//System.out.println(S);
		
    	if(S.contains(sign1) || S.contains(sign2) || S.contains(sign3))
    	{
    		return "mig_suc";
    	}
    	else
    	{
    		return "mig_error";
    	}
    }
    }
