package org.zkoss.tutorial2012.chapter5.mvvm;

import java.io.Serializable;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.tutorial2012.chapter5.AuthenticationServiceChapter5Impl;
import org.zkoss.tutorial2012.chapter5.UserInfoServiceChapter5Impl;
import org.zkoss.tutorial2012.entity.User;
import org.zkoss.tutorial2012.services.AuthenticationService;
import org.zkoss.tutorial2012.services.CommonInfoService;
import org.zkoss.tutorial2012.services.UserCredential;
import org.zkoss.tutorial2012.services.UserInfoService;
import org.zkoss.zk.ui.util.Clients;

public class ProfileViewModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	User profiledUser;
	
	AuthenticationService authService = new AuthenticationServiceChapter5Impl();
	UserInfoService userInfoService = new UserInfoServiceChapter5Impl();
	
	public User getProfiledUser(){
		return profiledUser;
	}
	
	public List<String> getCountryList(){
		return CommonInfoService.getCountryList();
	}
	
	@Init
	public void init(){
		UserCredential cre = authService.getUserCredential();
		profiledUser = userInfoService.findUser(cre.getAccount());
	}

	@Command
	public void save(){
		userInfoService.updateUser(profiledUser);
		
		Clients.showNotification("Your profile is updated");
	}

	@Command @NotifyChange("profiledUser")
	public void reload(){
		UserCredential cre = authService.getUserCredential();
		profiledUser = userInfoService.findUser(cre.getAccount());
	}
}