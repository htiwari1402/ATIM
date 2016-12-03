package com.wilp.atim.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wilp.atim.domain.MigrationParams;

@Controller
public class ShellController {

    @RequestMapping("/execShell")
    @ResponseBody
    public String execShell(/*@RequestBody MigrationParams mp*/)
            throws Exception {
    	JSONParser parser = new JSONParser();


    		Object obj = parser.parse(new FileReader("/etc/atim/atim.json"));
    		JSONObject jsonObject = (JSONObject) obj;
    		String migrationScript = (String) jsonObject.get("migration_script");
    		
      
      final String[] execCommand = { "/opt/data/apache-tomcat-8.5.6/webapps/myscript.sh", "key", "ls -t | tail -n 1" };
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
      
      	return S+migrationScript;
    }
    }
