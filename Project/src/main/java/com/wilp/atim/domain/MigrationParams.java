package com.wilp.atim.domain;

import java.util.HashMap;
import java.util.List;

public class MigrationParams {
	
	public int mig_opt;
	public String src_domain;
	public String src_repo;
	public String tgt_domain;
	public String tgt_repo;
	public String dev_group;
	public String emailGroup;
	
	
	
	public MigrationParams(int mig_opt, String src_domain, String src_repo,
			String tgt_domain, String tgt_repo, String dev_group,
			String emailGroup) {
		
		this.mig_opt = mig_opt;
		this.src_domain = src_domain;
		this.src_repo = src_repo;
		this.tgt_domain = tgt_domain;
		this.tgt_repo = tgt_repo;
		this.dev_group = dev_group;
		this.emailGroup = emailGroup;
	}
	
	public MigrationParams()
	{
		
	}
	public int getMig_opt() {
		return mig_opt;
	}
	public void setMig_opt(int mig_opt) {
		this.mig_opt = mig_opt;
	}
	public String getSrc_domain() {
		return src_domain;
	}
	public void setSrc_domain(String src_domain) {
		this.src_domain = src_domain;
	}
	public String getSrc_repo() {
		return src_repo;
	}
	public void setSrc_repo(String src_repo) {
		this.src_repo = src_repo;
	}
	public String getTgt_domain() {
		return tgt_domain;
	}
	public void setTgt_domain(String tgt_domain) {
		this.tgt_domain = tgt_domain;
	}
	public String getTgt_repo() {
		return tgt_repo;
	}
	public void setTgt_repo(String tgt_repo) {
		this.tgt_repo = tgt_repo;
	}
	public String getDev_group() {
		return dev_group;
	}
	public void setDev_group(String dev_group) {
		this.dev_group = dev_group;
	}
	public String getEmailGroup() {
		return emailGroup;
	}
	public void setEmailGroup(String emailGroup) {
		this.emailGroup = emailGroup;
	}
	
	
	
}
