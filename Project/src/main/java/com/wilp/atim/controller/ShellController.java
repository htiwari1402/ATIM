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
    @ResponseBody
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
      	return S+migrationScript;
    }
    }
