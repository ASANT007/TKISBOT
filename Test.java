package com.tkis.qedbot;



import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.stereotype.Component;



@Component
public class Test {
	
	private static LdapContext ldapctx = null;
	public static void main(String[] args) 
	{
		
		
		String serviceProviderUrl = "ldap://stmudc02.in.uhde.org";
		String serviceUserName = "USMUABOT", servicePassword = "SSzcr^?7d";
		//String userId = "10672206", password = "New_Code102";
		String userId = "10672205", password = "Happy@tkis@123";
		
		Test ad = new Test();
		String serviceSecurityPrincipal = "CN=" + serviceUserName + ",CN=Managed Service Accounts,DC=in,DC=uhde,DC=org";
		ad.makeConnection(serviceProviderUrl,serviceSecurityPrincipal, servicePassword);
		String userInfo = ad.findGroup(userId);
		//System.out.println("#### userInfo "+userInfo);
		ad.closeConnection();
		/*
		if(ad.checkNull(userInfo).length() > 0) {
			String userProviderUrl = "ldap://stmudc15.ext.in.uhde.org";
			String userInfoArray [] =  userInfo.split("~");
			String group = userInfoArray[0];
			String securityPrincipal = userInfoArray[1];
			System.out.println("#### group "+group);
			System.out.println("#### securityPrincipal "+securityPrincipal);
			ad.makeConnection(userProviderUrl,securityPrincipal, password);
			ad.validateUserDetails(securityPrincipal,userId, password);
			ad.closeConnection();
		}
		*/
		
		
		
	}
	
	
	public String findGroup(String userId) 
	{
		String userInfo = "", userSecurityPrincipal = "";		
		try 
		{			
			SearchControls sControls = new SearchControls();
			String[] attrIDs = { "Name","member","DisplayName" };			
			sControls.setReturningAttributes(attrIDs);
			sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);	
			
				
				NamingEnumeration groupResults = ldapctx.search("OU=WFH,DC=in,DC=uhde,DC=org",	"(&(sAMAccountName=10397281))", sControls);
				System.out.println("#### Above results");
				while (groupResults.hasMoreElements()) {
					System.out.println("#### Inside results");
					Attributes attrib = ( (SearchResult) groupResults.nextElement() ).getAttributes();
					
					Attribute userAttrib = attrib.get("Name");
					String userName = userAttrib.get().toString();
					
					Attribute userAttrDName = attrib.get("DisplayName");
					if(userAttrDName != null) {
						
						String DisName = userAttrDName.get().toString();
						System.out.println("#### "+DisName);
					}
					
					
					System.out.println("#### Service Name "+userName);
					//System.out.println("#### Service DisName "+DisName);
					
					NamingEnumeration<String> memberAttr = (NamingEnumeration<String>) attrib.get("member").getAll();
					
					 while(memberAttr.hasMore()) 
					 {
					  
						 userSecurityPrincipal = memberAttr.next();
						 System.out.println("#### 130 userSecurityPrincipal "+userSecurityPrincipal);
						 String dataArray [] = userSecurityPrincipal.split(",");
						 mainLoop:
						 for(int i=0;i<dataArray.length;i++) {
							 
							 String data[] = dataArray[i].split("=");
							 for(int j = 0; j<data.length;j++) 
							 {
								 //System.out.println("#### 112 "+data[j]+" ");
								 if(data[j].equalsIgnoreCase("CN") && j < data.length-1) {
									 
										/*
										 * String serviceProviderUrl = "ldap://stmudc02.in.uhde.org"; String
										 * serviceUserName = "USMUABOT", servicePassword = "SSzcr^?7d"; String
										 * serviceSecurityPrincipal = "CN=" + serviceUserName +
										 * ",CN=Managed Service Accounts,DC=in,DC=uhde,DC=org";
										 * makeConnection(serviceProviderUrl,serviceSecurityPrincipal, servicePassword);
										 */
									 validateUserDetails(userSecurityPrincipal, data[j+1], null);
										/*
										 * if(data[j+1].equalsIgnoreCase(userId) ) { userInfo = "User" + "~" +
										 * userSecurityPrincipal; //break mainLoop; }
										 */
								 }
							 }
						 }
					  
					 }
					//Attribute userAttrDName = attrib.get("DisplayName");
					//String DisName = userAttrDName.get().toString();
					//System.out.println("#### DisName"+DisName);
				}
			
			/*
			
			if(checkNull(userInfo).length() == 0) {
				
				NamingEnumeration mgmtResults = ldapctx.search("CN=Users,DC=in,DC=uhde,DC=org",	"(&(sAMAccountName=UGMUQEDBOT-AP-MGMT))", sControls);
				
				System.out.println("#### Above results");
				while (mgmtResults.hasMoreElements()) {
					System.out.println("#### Inside results");
					Attributes attrib = ( (SearchResult) mgmtResults.nextElement() ).getAttributes();
					NamingEnumeration<String> memberAttr = (NamingEnumeration<String>) attrib.get("member").getAll();
					 while(memberAttr.hasMore()) 
					 {
						 userSecurityPrincipal = memberAttr.next();
						 String dataArray [] = userSecurityPrincipal.split(",");
						 mainLoop:
						 for(int i=0;i<dataArray.length;i++) {
							 
							 String data[] = dataArray[i].split("=");
							 for(int j = 0; j<data.length;j++) 
							 {
								 if(data[j].equalsIgnoreCase("CN") && j < data.length-1) {
									 if(data[j+1].equalsIgnoreCase(userId) ) {
										 userInfo = "Management User" + "~" + userSecurityPrincipal;
										 break mainLoop;
									 }
								 }
							 }
						 }
					  
					 }
					
				}	
			}
			
			*/
			

		} catch (AuthenticationException ae) {
			System.out.println("Authentication Exception");
			System.out.println("Exception At AD Validating User :" + ae);
			
		} catch (CommunicationException ce) {
			System.out.println("Exception At AD Validating User :" + ce);
			
		} catch (NamingException ne) {
			System.out.println("Exception At AD Validating User :" + ne);
			
		} catch (NullPointerException nue) {
			System.out.println("Exception At Establishing AD Connection " + nue);
			
		} catch (Exception ex) {
			//System.out.println("Exception At Establishing AD Connection " + ex);
			ex.printStackTrace();
			
		}
		System.out.println("#### userInfo : "+userInfo);
		//System.out.println("#### userSecurityPrincipal : "+userSecurityPrincipal);
		return userInfo;
	}
	
	public boolean validateUserDetails(String userSecurityPrincipal,String userId, String password) {
		System.out.println("#### userId"+userId);
		
		boolean isValidDetails = false;
		String userinfo = "";
		
		SearchControls sControls = new SearchControls();
		
		
		  String[] attrIDs =  {"sAMAccountName","objectclass","Name","DisplayName"};
		  
		  sControls.setReturningAttributes(attrIDs);
          
          sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
          
          NamingEnumeration results = null;
          try 
          {
        	  //Below line will change
        	  System.out.println("234");
        	  
			   //results = ldapctx.search("CN=10672206,CN=Users,DC=ext,DC=in,DC=uhde,DC=org","(&(objectclass=*),(sAMAccountName="+userId+"))",sControls);
        	  results = ldapctx.search(userSecurityPrincipal,"(&(objectclass=user),(sAMAccountName="+userId+"))",sControls);
			   
			   System.out.println("236");
			   
			   while(results.hasMoreElements()) 
			   {
	        	    
				   System.out.println("240");
				   
	                Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
	                Attribute userAttrib = attrib.get("Name");//
	                Attribute userAttrDName = attrib.get("DisplayName");
	                
	                
	                String userName = userAttrib.get().toString();	                
	                
	                String DisName = userAttrDName.get().toString();
	                
	                
	                isValidDetails = true;
	               
	                System.out.println("userName ["+userName);
	                System.out.println("DisName ["+DisName);
	                
					/*
					 * ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,userDN);
					 * ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,password);
					 * userinfo=userDN+"/"+location;
					 */
	                ldapctx.reconnect(null);
	           }
			   
          }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At AD Validating User :"+ae);
            userinfo = "";
          }catch(CommunicationException ce){   
        	  System.out.println("Exception At AD Validating User :"+ce);
        	  userinfo = "";
       
          }catch(NamingException ne){   
        	  System.out.println("Exception At AD Validating User :"+ne);
        	  userinfo = "";
       
          }catch(NullPointerException nue){   
        	  System.out.println("Exception At Establishing AD Connection "+nue);
        	  userinfo = "";
       
          }catch(Exception ex){   
        	  System.out.println("Exception At Establishing AD Connection "+ex);
        	  userinfo = "";
       
          }	
          return isValidDetails;
		//return userinfo;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public boolean makeConnection(String providerURL, String securityPrincipal, String password) {
		boolean sucess = false;
		try {
			

			@SuppressWarnings("rawtypes")
			
			Hashtable env  = new Hashtable();

			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.PROVIDER_URL, providerURL);
			env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
			env.put(Context.SECURITY_CREDENTIALS, password);

			ldapctx = new InitialLdapContext(env, null);
			
			if (ldapctx != null) 
			{
				System.out.println("Established AD Connection");
				sucess = true;
			} else 
			{
				System.out.println("AD Connection Fail");
				sucess = false;
			}

		} catch (CommunicationException ce) {
			System.out.println("CommunicationException At Establishing AD Connection :" + ce);
			return sucess = false;
		} catch (NamingException ne) {
			System.out.println("NamingException At Establishing AD Connection " + ne);
			return sucess = false;
		} catch (NullPointerException nue) {
			System.out.println("NamingException At Establishing AD Connection " + nue);
			return sucess = false;
		} catch (Exception ex) {
			System.out.println("Exception At Establishing AD Connection " + ex);
			return sucess = false;
		}
		System.out.println("makeConnection [" + sucess + "]");
		return sucess;
	}
	
	public boolean closeConnection()
	{
	       boolean sucess=false;     
	       try{      
	           ldapctx.close();
	           sucess=true;
	           System.out.println("Closed AD Connection");
	           }catch(CommunicationException ce){   
	           System.out.println("Exception At Closing AD Connection :"+ce);
	           sucess=false;
	           }catch(NamingException ne){   
	           System.out.println("Exception At Closing AD Connection "+ne);
	           sucess=false;
	          }catch(Exception ex){   
	           System.out.println("Exception At Closing AD Connection "+ex);
	           sucess=false;
	          } 
	       System.out.println("closeConnection ["+sucess+"]");
	        return sucess;
	}
	public String checkNull(String input)
    {
	    if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input)) {
	    	
	    	input = "";
	    }
        
        return input.trim();    
    }

	/*
	 * public HashMap<String,String> getADUsers() throws NamingException {
	 * 
	 * HashMap<String,String> userHashMap = new HashMap<>();
	 * 
	 * String userSecurityPrincipal=""; SearchControls sControls = new
	 * SearchControls(); String[] attrIDs = { "member" };
	 * 
	 * sControls.setReturningAttributes(attrIDs);
	 * 
	 * sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	 * 
	 * NamingEnumeration groupResults =
	 * ldapctx.search("CN=Users,DC=in,DC=uhde,DC=org",
	 * "(&(sAMAccountName=UGMUQEDBOT-AP-USERS))", sControls);
	 * System.out.println("#### Above results"); while
	 * (groupResults.hasMoreElements()) { System.out.println("#### Inside results");
	 * Attributes attrib = ( (SearchResult) groupResults.nextElement()
	 * ).getAttributes(); NamingEnumeration<String> memberAttr =
	 * (NamingEnumeration<String>) attrib.get("member").getAll();
	 * while(memberAttr.hasMore()) {
	 * 
	 * userSecurityPrincipal = memberAttr.next(); String dataArray [] =
	 * userSecurityPrincipal.split(",");
	 * 
	 * for(int i=0;i<dataArray.length;i++) {
	 * 
	 * String data[] = dataArray[i].split("="); for(int j = 0; j<data.length;j++) {
	 * if(data[j].equalsIgnoreCase("CN") && j < data.length-1) {
	 * if(data[j+1].equalsIgnoreCase("") ) {
	 * 
	 * } } } }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * 
	 * return userHashMap; }
	 */
}

