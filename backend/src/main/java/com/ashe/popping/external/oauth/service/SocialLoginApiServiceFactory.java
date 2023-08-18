package com.ashe.popping.external.oauth.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ashe.popping.domain.member.constant.MemberType;

@Service
public class SocialLoginApiServiceFactory {
	private static Map<String, SocialLoginApiService> socialLoginApiServices;

	public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiServices){
		this.socialLoginApiServices = socialLoginApiServices;
	}

	public static SocialLoginApiService getSocialLoginApiService(MemberType memberType){
		String socialLoginApiServiceBeanName = "";
		if(MemberType.KAKAO.equals(memberType)){
			socialLoginApiServiceBeanName = "kakaoLoginApiServiceImpl";
		}
		if(MemberType.GOOGLE.equals(memberType)){
			socialLoginApiServiceBeanName = "googleLoginApiServiceImpl";
		}

		return socialLoginApiServices.get(socialLoginApiServiceBeanName);

	}

}
