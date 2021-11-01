package com.tkis.qedbot.ad;

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
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tkis.qedbot.repo.GroupMasterRepo;

@Component
public class ADGroupWiseAuthentication {
	
	private static LdapContext ldapctx = null;
	public static void main(String[] args) 
	{
		
		
		String serviceProviderUrl = "ldap://stmudc02.in.uhde.org";
		String serviceUserName = "USMUABOT", servicePassword = "SSzcr^?7d";
		String userId = "10672206", password = "New_Code102";
		//String userId = "10672205", password = "Happy@tkis@123";
		
		ADGroupWiseAuthentication ad = new ADGroupWiseAuthentication();
		String serviceSecurityPrincipal = "CN=" + serviceUserName + ",CN=Managed Service Accounts,DC=in,DC=uhde,DC=org";
		ad.makeConnection(serviceProviderUrl,serviceSecurityPrincipal, servicePassword);
		String userInfo = ad.findGroup(userId);
		//System.out.println("#### userInfo "+userInfo);
		ad.closeConnection();
		if(ad.checkNull(userInfo).length() > 0) {
			String userProviderUrl = "ldap://stmudc15.ext.in.uhde.org";
			String userInfoArray [] =  userInfo.split("~");
			String group = userInfoArray[0];
			String securityPrincipal = userInfoArray[1];
			System.out.println("#### group "+group);
			System.out.println("#### securityPrincipal "+securityPrincipal);
			ad.makeConnection(userProviderUrl,securityPrincipal, password);
			ad.validateUserDetails(securityPrincipal,userId, password, null);
			ad.closeConnection();
		}
		
		
		
	}
	
	@Autowired 
	private GroupMasterRepo groupMasterRepo;
	
	public String findGroup(String userId) 
	{
		String userInfo = "";
		String userSecurityPrincipal = "";
		//String group = "";
		try {

			
			SearchControls sControls = new SearchControls();
			String[] attrIDs = { "member" };
			
			sControls.setReturningAttributes(attrIDs);

			sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			//NamingEnumeration results = ldapctx.search("DC=ext,DC=in,DC=uhde,DC=org","(&(objectclass=*),(sAMAccountName="+username+"))",sControls);
			
			//Changed on 08-09-2021 START
			List<Object[]> grpDetails = groupMasterRepo.getGroupDetails();
			
			mainLoop:
			for(Object[] group : grpDetails) {
				
				String groupName = (String) group[0];
				String userRole = (String) group[1];
				System.out.println("#### groupName "+groupName);
				System.out.println("#### userRole "+userRole);
				
				NamingEnumeration adminResults = ldapctx.search("CN=Users,DC=in,DC=uhde,DC=org",	"(&(sAMAccountName="+checkNull(groupName)+"))", sControls);
				 System.out.println("#### 94 ");
				
				while (adminResults.hasMoreElements()) {
					
					Attributes attrib = ( (SearchResult) adminResults.nextElement() ).getAttributes();
					NamingEnumeration<String> memberAttr = (NamingEnumeration<String>) attrib.get("member").getAll();
					System.out.println("#### 100 ");
					 while(memberAttr.hasMore()) 
					 {
					  
						 System.out.println("#### 102 ");
						 userSecurityPrincipal = memberAttr.next();
						 String dataArray [] = userSecurityPrincipal.split(",");
						 System.out.println("#### 105 ");
						 for(int i=0;i<dataArray.length;i++) {
							 
							 String data[] = dataArray[i].split("=");
							 System.out.println("#### 109 ");
							 for(int j = 0; j<data.length;j++) 
							 {
								 System.out.println("#### 112 "+data[j]+" ");
								 if(data[j].equalsIgnoreCase("CN") && j < data.length-1) {
									 if(data[j+1].equalsIgnoreCase(userId) ) {
										 
										 userInfo = checkNull(userRole) + "~" + userSecurityPrincipal;
										 break mainLoop;
										 
									 }
								 }
							 }
						 }
						
					  
					 }
				
				
			}
			}
				
			//Changed on 08-09-2021 END
			/*
			NamingEnumeration adminResults = ldapctx.search("CN=Users,DC=in,DC=uhde,DC=org",	"(&(sAMAccountName=UGMUQEDBOT-AP-ADMINS))", sControls);
			
			System.out.println("#### Above results");
			while (adminResults.hasMoreElements()) {
				System.out.println("#### Inside results");
				Attributes attrib = ( (SearchResult) adminResults.nextElement() ).getAttributes();
				NamingEnumeration<String> memberAttr = (NamingEnumeration<String>) attrib.get("member").getAll();
				 while(memberAttr.hasMore()) 
				 {
				  
				  //System.out.println("***memberOfAttr "+memberAttr.next());
					 userSecurityPrincipal = memberAttr.next();
					 String dataArray [] = userSecurityPrincipal.split(",");
					 mainLoop:
					 for(int i=0;i<dataArray.length;i++) {
						 
						 String data[] = dataArray[i].split("=");
						 for(int j = 0; j<data.length;j++) 
						 {
							 if(data[j].equalsIgnoreCase("CN") && j < data.length-1) {
								 if(data[j+1].equalsIgnoreCase(userId) ) {
									 userInfo = "Functional Admin" + "~" + userSecurityPrincipal;
									 break mainLoop;
								 }
							 }
						 }
					 }
					
				  
				 }
				
			}
			if(checkNull(userInfo).length() == 0) {
				NamingEnumeration groupResults = ldapctx.search("CN=Users,DC=in,DC=uhde,DC=org",	"(&(sAMAccountName=UGMUQEDBOT-AP-USERS))", sControls);
				System.out.println("#### Above results");
				while (groupResults.hasMoreElements()) {
					System.out.println("#### Inside results");
					Attributes attrib = ( (SearchResult) groupResults.nextElement() ).getAttributes();
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
										 userInfo = "User" + "~" + userSecurityPrincipal;
										 break mainLoop;
									 }
								 }
							 }
						 }
					  
					 }
					
				}
			}
			
			
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
	
	public boolean validateUserDetails(String userSecurityPrincipal,String userId, String password, HttpSession session) {
		
		boolean isValidDetails = false;
		String userinfo = "";
		
		SearchControls sControls = new SearchControls();
		
		
		  String[] attrIDs =
				  
		  {"sAMAccountName","objectclass","Name","DisplayName","Department",
		  "distinguishedName","l","description","userPrincipalName","wWWHomePage","SN",
		  "title","mobile","CN","mail","givenName","objectCategory","uniqueMember",
		  "manager","memberOf"};
		  
		  sControls.setReturningAttributes(attrIDs);
          
          sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
          
          NamingEnumeration results = null;
          try 
          {
        	  //Below line will change
        	  System.out.println("234");
        	  
			   //results = ldapctx.search("CN=10672206,CN=Users,DC=ext,DC=in,DC=uhde,DC=org","(&(objectclass=*),(sAMAccountName="+userId+"))",sControls);
        	  results = ldapctx.search(userSecurityPrincipal,"(&(objectclass=*),(sAMAccountName="+userId+"))",sControls);
			   
			   System.out.println("236"); 
			   while(results.hasMoreElements()) 
			   {
	        	    
				   System.out.println("240");
	                Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
	                Attribute userAttrib = attrib.get("Name");//
	                Attribute userAttrDName = attrib.get("DisplayName");
	                Attribute userAttrDpt = attrib.get("Department");
	                Attribute dnAttrib= attrib.get("distinguishedName");
	                Attribute lAttrib= attrib.get("l");
	                Attribute descrAttr= attrib.get("description");	                
	                Attribute princNameAttr= attrib.get("userPrincipalName");	                
	                Attribute snAttr= attrib.get("SN");
	                Attribute cnAttr= attrib.get("CN");
	                Attribute givenNameAttr= attrib.get("givenName");
	                Attribute objectCategoryAttr= attrib.get("objectCategory");
	                Attribute objectClassAttr= attrib.get("objectClass");
	                
	                
	                String userName = userAttrib.get().toString();	                
	                String userDN = dnAttrib.get().toString();
	                String location = lAttrib.get().toString();
	                String dptName = userAttrDpt.get().toString();
	                String DisName = userAttrDName.get().toString();
	                String descr = descrAttr.get().toString();
	                String princName = princNameAttr.get().toString();
	                String sn = snAttr.get().toString();
	                String cn = cnAttr.get().toString();
	                String givenName = givenNameAttr.get().toString();
	                String objectCat = objectCategoryAttr.get().toString();
	                String objectClass = objectClassAttr.get().toString();
	                
	                
	                
	                //session.setAttribute("userName", userName);
	                session.setAttribute("DisplayName", DisName);
	                isValidDetails = true;
	               
	                System.out.println("userName ["+userName);
	                System.out.println("userDN ["+userDN);
	                System.out.println("location ["+location);
	                System.out.println("dptName ["+dptName);
	                System.out.println("DisName ["+DisName);
	                System.out.println("descr ["+descr);	                
	                System.out.println("princName ["+princName);	                
	                System.out.println("sn ["+sn);
	                System.out.println("CN ["+cn);
	                System.out.println("givenName ["+givenName);
	                System.out.println("objectCat ["+objectCat);
	                System.out.println("objectClass ["+objectClass);	                
	                
	                
	                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,userDN); 
	                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,password);
	                userinfo=userDN+"/"+location;
	                
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
	
	public String findUserGroupUsers(String term, String userGroupName, String ldapUrl, String serviceSecurityPrincipal, String servicePassword) 
	{
		String response = "", userSecurityPrincipal = "";	
		String userId = "", userName = "";
		try 
		{	
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObjectValue = null;
			
			SearchControls sControls = new SearchControls();			
			String[] attrIDs = { "Name","member","DisplayName" };			
			sControls.setReturningAttributes(attrIDs);			
			sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);						
			makeConnection(ldapUrl,serviceSecurityPrincipal, servicePassword);			
			NamingEnumeration groupResults = ldapctx.search("CN=Users,DC=in,DC=uhde,DC=org",	"(&(objectclass=*),(sAMAccountName="+userGroupName+"))", sControls);
						
			while (groupResults.hasMoreElements()) {
				
				Attributes attrib = ( (SearchResult) groupResults.nextElement() ).getAttributes();
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
								   
								 userId = data[j+1];
								  
								 if(!userId.equals("Users")) {
									 
									 
									 
									 if(userSecurityPrincipal.contains("ext")) {										 
										 makeConnection("ldap://stmudc15.ext.in.uhde.org",serviceSecurityPrincipal, servicePassword);
									 }else {										 
										 makeConnection(ldapUrl,serviceSecurityPrincipal, servicePassword);										 
									 }
									 
									 userName = getUserDetails(userSecurityPrincipal,userId );
									 closeConnection();
									 
									 System.out.println("#### userName "+userName);
									 System.out.println("#### term "+term);
									 System.out.println("#### START "+userName.startsWith(term));
									 
									 if(userName.toLowerCase().contains(term.toLowerCase())) {
										 jsonObjectValue = new JSONObject();
										 jsonObjectValue.put("value", userId);
										 jsonObjectValue.put("label", userName);
										 jsonArray.add(jsonObjectValue);
									 }
									 
								 }
								 										 
								 
									
							 }
						 }
					 }
				  
				 }
				
			}	
				
			response = jsonArray.toString();
			
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
		System.out.println("#### findUserGroupUsers :: response : "+response);
		
		return response;
	}
	
	public String getUserDetails(String userSecurityPrincipal, String userId) 
	{
		String displayName = "";	
		System.out.println("#### userSecurityPrincipal "+userSecurityPrincipal);
		System.out.println("#### userId "+userId);
		try 
		{			
			SearchControls sControls = new SearchControls();			
			String[] attrIDs = { "Name","member","DisplayName" };			
			sControls.setReturningAttributes(attrIDs);			
			sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				
			NamingEnumeration userResults = ldapctx.search(userSecurityPrincipal,"(&(objectclass=*),(sAMAccountName="+userId+"))", sControls);
			
			while (userResults.hasMoreElements()) {
				
				System.out.println("####  158");
				
				Attributes attrib = ( (SearchResult) userResults.nextElement() ).getAttributes();					
				Attribute userAttrib = attrib.get("Name");
				String userName = userAttrib.get().toString();					
				System.out.println("#### Name "+userName);
				
				Attribute userAttrDName = attrib.get("DisplayName");					
				if(userAttrDName != null) {
					
					//String DisName = userAttrDName.get().toString();
					displayName = userAttrDName.get().toString();
					if(displayName.contains("(ext.)")) {
						displayName = displayName.replace("(ext.)", "");
					}
					//System.out.println("#### Display Name : "+displayName);
				}			
				
			}
			

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
		System.out.println("#### displayName : "+displayName);
		
		return displayName;
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
