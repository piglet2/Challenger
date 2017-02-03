package com.envision.envservice.ldap;

import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.Code;
import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.MapConfigService;
import com.envision.envservice.service.exception.ServiceException;

@Service
public class UserAuthenticate {

	private static final Logger logger = EnvLog.getBusinessFailLogger();
	
	/**
	 * LADP超时时间(s)
	 */
	private static final int LADP_TIME_OUT = 20;
	
	@Autowired
	private MapConfigService mapConfigService;

	private static Hashtable<String, String> environment = new Hashtable<String, String>();

	@PostConstruct
	@SuppressWarnings("unused")
	private void onnectLDAP() throws NamingException {
		String ldap_url = mapConfigService.getValue("ldap_url");
		String ldap_basedn = mapConfigService.getValue("ladp_basedn");
		String ldap_factory = mapConfigService.getValue("ladp_factory");
		
		environment.put(Context.INITIAL_CONTEXT_FACTORY, ldap_factory);
		environment.put(Context.PROVIDER_URL, ldap_url + ldap_basedn);
	}

	public boolean authenricate(final String userId, final String password) throws Exception {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Boolean> future = executor.submit(new Callable<Boolean>() {
			
			@Override
			public Boolean call() throws Exception {
				return checkByLadp(userId, password);
			}
		});
		executor.shutdown();
		
		try {
			return future.get(LADP_TIME_OUT, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			throw new ServiceException(Code.REQUEST_TIMEOUT, "LDAP访问超时");
		}
	}
	
	private boolean checkByLadp(String userId, String password) {
		boolean valide = false;
		try {
			Control[] connCtls = null;
			LdapContext ctx = new InitialLdapContext(environment, null);
			
			ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userId);
			ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
			ctx.reconnect(connCtls);
			valide = true;
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}

		return valide;
	}
}