package com.wilp.atim.domain;

import java.util.HashMap;
import java.util.List;

public class MigrationParams {
	
	public String adminUserName;
	public String adminPassword;
	public String sourceRepo;
	public String targetRepo;
	public String sourceDomain;
	public String targetDomain;
	public String dgName;
	public List<HashMap<String,String>> sourceTargetMap;
	public String controlFileName;
	
	public String getAdminUserName() {
		return adminUserName;
	}
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getSourceRepo() {
		return sourceRepo;
	}
	public void setSourceRepo(String sourceRepo) {
		this.sourceRepo = sourceRepo;
	}
	public String getTargetRepo() {
		return targetRepo;
	}
	public void setTargetRepo(String targetRepo) {
		this.targetRepo = targetRepo;
	}
	public String getSourceDomain() {
		return sourceDomain;
	}
	public void setSourceDomain(String sourceDomain) {
		this.sourceDomain = sourceDomain;
	}
	public String getTargetDomain() {
		return targetDomain;
	}
	public void setTargetDomain(String targetDomain) {
		this.targetDomain = targetDomain;
	}
	public String getDgName() {
		return dgName;
	}
	public void setDgName(String dgName) {
		this.dgName = dgName;
	}
	public List<HashMap<String, String>> getSourceTargetMap() {
		return sourceTargetMap;
	}
	public void setSourceTargetMap(List<HashMap<String, String>> sourceTargetMap) {
		this.sourceTargetMap = sourceTargetMap;
	}
	public String getControlFileName() {
		return controlFileName;
	}
	public void setControlFileName(String controlFileName) {
		this.controlFileName = controlFileName;
	}
	
}
