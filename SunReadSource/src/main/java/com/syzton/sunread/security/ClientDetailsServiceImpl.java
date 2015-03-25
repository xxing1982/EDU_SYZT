package com.syzton.sunread.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

	@Override
	public ClientDetails loadClientByClientId(String clientId)
			throws ClientRegistrationException {
		if(clientId.equals("client1")){
			List<String> authorizedGrantTypes = new ArrayList<String>();
			authorizedGrantTypes.add("password");
			authorizedGrantTypes.add("refresh_token");
			authorizedGrantTypes.add("client_credentials");
			
			BaseClientDetails clientDetails = new BaseClientDetails();
			clientDetails.setClientId("client1");
			clientDetails.setClientSecret("client1");
			clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
		}else{
			throw new NoSuchClientException("No client with requested id:");
		}
		return null;
	}

}
