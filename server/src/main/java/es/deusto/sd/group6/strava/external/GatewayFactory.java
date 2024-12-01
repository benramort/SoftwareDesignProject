package es.deusto.sd.group6.strava.external;

import es.deusto.sd.group6.strava.entity.AccountType;

public class GatewayFactory {

	private static GatewayFactory instance = new GatewayFactory();
	
	public ILoginServiceGateway getGateway(AccountType accountType) {
        switch (accountType) {
        case FACEBOOK:
        	return new FacebookServiceGateway();
        case GOOGLE:
        	return new GoogleServiceGateway();
        default:
        	throw new IllegalArgumentException("Account type not supported");
        }
    }
	
	public static GatewayFactory getInstance() {
		return instance;
	}
	
	
}
