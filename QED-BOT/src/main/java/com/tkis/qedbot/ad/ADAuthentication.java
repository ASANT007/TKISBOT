package com.tkis.qedbot.ad;

import java.util.Hashtable;
import java.util.Set;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class ADAuthentication {

	private static Hashtable env = null;
    private static Set set=null;
    private static Hashtable hashtable=null;
    private static LdapContext ldapctx = null;
    private static Context ctx = null;
    
    
    private static String sLDAPServerURL = ""; // LDAP://STMUDC15  /ldap server url
    private static String HelpDeskAdminId=""; //CN=10672206,OU=WFH,DC=ext,DC=in,DC=uhde,DC=org //username 
    private static String HelpDeskPassWord="";//New_Code101 //user password
    
    private static DirContext dircontext=null;
    private static NamingEnumeration results=null;
    private static SearchControls sControls=null;
    
	public static void main(String [] args) {
		
		String providerUrl = "ldap://stmudc15.ext.in.uhde.org", username = "10672206",  password = "New_Code101",  domain = "";
		
		ADAuthentication authentication = new ADAuthentication();
		authentication.makeConnection(providerUrl, username, password);
		authentication.validateUser(username, password, null);
		authentication.closeConnection();
	}
	
	
	
	public boolean makeConnection(String providerUrl,String username, String password)
	   {       
	           boolean sucess = false; 
	           try
	           {   
	        	   String securityPrincipal = "CN="+username+",CN=Users,DC=ext,DC=in,DC=uhde,DC=org";
	        	    System.out.println("### providerUrl "+providerUrl);
	       			System.out.println("### securityPrincipal "+securityPrincipal);
	       			System.out.println("### password "+password);
	       			
	        	   
	        	   
	        	   
		           env = new Hashtable();
		           
		           env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		   			env.put(Context.SECURITY_AUTHENTICATION,"simple");
		   			env.put(Context.PROVIDER_URL,providerUrl);  
		           env.put(Context.SECURITY_PRINCIPAL,securityPrincipal);
		           env.put(Context.SECURITY_CREDENTIALS, password);
		           
		           ldapctx = new InitialLdapContext(env,null);
		           System.out.println("Established AD Connection");
		           if (ldapctx!= null){
		               sucess= true;
		           }else{
		               sucess=false;
		           }
	           
	           }catch(CommunicationException ce){   
	           System.out.println("CommunicationException At Establishing AD Connection :"+ce);
	              return sucess=false;
	           }catch(NamingException ne){   
	           System.out.println("NamingException At Establishing AD Connection "+ne);
	           return sucess=false;
	           }catch(NullPointerException nue){   
	           System.out.println("NamingException At Establishing AD Connection "+nue);
	           return sucess=false;
	           }catch(Exception ex){   
	           System.out.println("Exception At Establishing AD Connection "+ex);
	           return sucess=false;
	           }   
	           System.out.println("makeConnection ["+sucess+"]");
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
	   public String validateUser(String username,String password, HttpSession session)
	   {
	       String userinfo="";
	       try{      
	           
	           //ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
	           //ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord); 
	           sControls = new SearchControls();
	           String[] attrIDs = {"sAMAccountName","objectclass","Name","DisplayName","Department",
	        		   "distinguishedName","l","description","userPrincipalName","wWWHomePage","SN",
	        		   "title","mobile","CN","mail","givenName","objectCategory","uniqueMember","manager"};
	           //System.out.println(attrIDs);
	           sControls.setReturningAttributes(attrIDs);
	           sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	           results = ldapctx.search("DC=ext,DC=in,DC=uhde,DC=org","(&(objectclass=*),(sAMAccountName="+username+"))",sControls);
	           while(results.hasMoreElements()) {
	                Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
	                Attribute userAttrib = attrib.get("Name");//
	                Attribute userAttrDName = attrib.get("DisplayName");
	                Attribute userAttrDpt = attrib.get("Department");
	                Attribute dnAttrib= attrib.get("distinguishedName");
	                Attribute lAttrib= attrib.get("l");
	                Attribute descrAttr= attrib.get("description");
	                //"userPrincipalName","wWWHomePage","SN","title","mobile"
	                Attribute princNameAttr= attrib.get("userPrincipalName");
	                //Attribute homePageAttr= attrib.get("wWWHomePage");
	                Attribute snAttr= attrib.get("SN");
	                Attribute cnAttr= attrib.get("CN");
	                Attribute givenNameAttr= attrib.get("givenName");
	                Attribute objectCategoryAttr= attrib.get("objectCategory");
	                Attribute objectClassAttr= attrib.get("objectClass");
	                //Attribute uniqueMemberAttr= attrib.get("uniqueMember");
	                //Attribute managerAttr= attrib.get("manager");
	                //Attribute mailAttr= attrib.get("mail");
	                //Attribute titleAttr= attrib.get("title");
	                //Attribute mobileAttr= attrib.get("mobile");
	                
	                String userName = userAttrib.get().toString();
	                session.setAttribute("userName", userName);
	                String userDN = dnAttrib.get().toString();
	                String location = lAttrib.get().toString();
	                String dptName = userAttrDpt.get().toString();
	                String DisName = userAttrDName.get().toString();
	                session.setAttribute("DisplayName", DisName);
	                String descr = descrAttr.get().toString();
	                String princName = princNameAttr.get().toString();
	                //String homePage = homePageAttr.get().toString();
	                String sn = snAttr.get().toString();
	                String cn = cnAttr.get().toString();
	                String givenName = givenNameAttr.get().toString();
	                String objectCat = objectCategoryAttr.get().toString();
	                String objectClass = objectClassAttr.get().toString();
	               // String uniqueMember = uniqueMemberAttr.get().toString();
	                //String manager = managerAttr.get().toString();
	                //String mail = mailAttr.get().toString();
	                //String title = titleAttr.get().toString();
	                //String mobile = mobileAttr.get().toString();
	               
	                System.out.println("userName ["+userName);
	                System.out.println("userDN ["+userDN);
	                System.out.println("location ["+location);
	                System.out.println("dptName ["+dptName);
	                System.out.println("DisName ["+DisName);
	                System.out.println("descr ["+descr);
	                
	                System.out.println("princName ["+princName);
	                //System.out.println("homePage ["+homePage);
	                System.out.println("sn ["+sn);
	                System.out.println("CN ["+cn);
	                System.out.println("givenName ["+givenName);
	                System.out.println("objectCat ["+objectCat);
	                System.out.println("objectClass ["+objectClass);
	                //System.out.println("uniqueMember ["+uniqueMember);
	                //System.out.println("manager ["+manager);
	               // System.out.println("mail ["+mail);
	                
	                //System.out.println("title ["+title);
	                //System.out.println("mobile ["+mobile);
	                
	                
	                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,userDN); 
	                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,password);
	                userinfo=userDN+"/"+location;
	                
	                ldapctx.reconnect(null);
	           }
	           
	           }catch(AuthenticationException  ae){
	            System.out.println("Authentication Exception");   
	            System.out.println("Exception At AD Validating User :"+ae);
	            userinfo="";
	           }catch(CommunicationException ce){   
	           System.out.println("Exception At AD Validating User :"+ce);
	           userinfo="";
	           }catch(NamingException ne){   
	           System.out.println("Exception At AD Validating User :"+ne);
	           userinfo="";
	           }catch(NullPointerException nue){   
	           System.out.println("Exception At Establishing AD Connection "+nue);
	           userinfo="";
	           }catch(Exception ex){   
	           System.out.println("Exception At Establishing AD Connection "+ex);
	           userinfo="";
	           }
	       
	     return userinfo;
	    }
}
