package com.tkis.qedbot;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;

public class ADConnect {
	public static void main(String [] args) {
		ADConnect adConnect = new ADConnect();
		adConnect.makeConnection();
	}
	
	public void makeConnection() {
		//CN=10672206
		boolean sucess=false;
		
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION,"simple");
		env.put(Context.PROVIDER_URL,"LDAP://STMUDC15/");  
        env.put(Context.SECURITY_PRINCIPAL,"CN=10672206,CN=Users,DC=ext,DC=in,DC=uhde,DC=org");
        env.put(Context.SECURITY_CREDENTIALS, "New_Code101");
         
        
       DirContext ldapctx = null;
       
	try
	{
		ldapctx = new InitialLdapContext(env, null);
		//ldapctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, "none");
		
	} catch (NamingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        System.out.println("Established AD Connection"+ldapctx);
        if (ldapctx!= null){
            sucess= true;
        }else{
            sucess=false;
        }
        
        System.out.println("sucess ["+sucess+"");
	}
	
	
	
	
}
