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
		if(clientId.equals("353b302c44574f565045687e534e7d6a")){
			 
			List<String> authorizedGrantTypes = new ArrayList<String>();
			authorizedGrantTypes.add("password");
			authorizedGrantTypes.add("refresh_token");
			
			BaseClientDetails clientDetails = new BaseClientDetails();
			clientDetails.setClientId("353b302c44574f565045687e534e7d6a");
			clientDetails.setClientSecret("286924697e615a672a646a493545646c");
			clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
			List<String> list = new ArrayList<String>();
			list.add("read");
			list.add("write");
			clientDetails.setScope(list);
			return clientDetails;
		}else{
			throw new NoSuchClientException("No client with requested id:");
		}
	 
	}

}
