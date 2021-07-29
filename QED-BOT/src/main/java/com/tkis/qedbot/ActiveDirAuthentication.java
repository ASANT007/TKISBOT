package com.tkis.qedbot;



import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import java.net.ConnectException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class ActiveDirAuthentication{
	
    private static Hashtable env = null;
    private static Set set=null;
    private static Hashtable hashtable=null;
    private static LdapContext ldapctx = null;
    private static Context ctx = null;
    
    
    private static String sLDAPServerURL = "LDAP://STMUDC15/"; // ldap server url
    private static String HelpDeskAdminId="CN=10672206,OU=WFH,DC=ext,DC=in,DC=uhde,DC=org"; //OU=WFH, username 
    private static String HelpDeskPassWord="New_Code101";// user password
    
    private static DirContext dircontext=null;
    private static NamingEnumeration results=null;
    private static SearchControls sControls=null;
    
    public static void main(String args[]) throws NamingException
    {
       ActiveDirAuthentication ad=new ActiveDirAuthentication();
       ad.makeConnection();
       ad.validateUser("10672206", "New_Code101");
       ad.closeConnection();
       //ad.getFMEngInfoByLocation("Mumbai");
       //ad.getFMEngList();
    } 
    
    
   public boolean makeConnection()
   {       
           boolean sucess=false; 
           try
           {      
	           env = new Hashtable();
	           /*env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
	           env.put(Context.SECURITY_AUTHENTICATION,"simple");
	           env.put(Context.PROVIDER_URL,sLDAPServerURL); */
	           env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
	   			env.put(Context.SECURITY_AUTHENTICATION,"simple");
	   			env.put(Context.PROVIDER_URL,"ldap://stmudc15.ext.in.uhde.org");  
	           env.put(Context.SECURITY_PRINCIPAL,"CN=10672206,CN=Users,DC=ext,DC=in,DC=uhde,DC=org");
	           env.put(Context.SECURITY_CREDENTIALS, "New_Code101");
	           
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
   public String validateUser(String username,String password)
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
                String userDN = dnAttrib.get().toString();
                String location = lAttrib.get().toString();
                String dptName = userAttrDpt.get().toString();
                String DisName = userAttrDName.get().toString();
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
   
   public String getFMInfo(String user)
   {
     String userinfo="";
     try{      
       
           ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
           ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
           SearchControls sControls = new SearchControls();
           String[] attrIDs = {"cn","sAMAccountName","objectclass","l","givenName","sn"};
           sControls.setReturningAttributes(attrIDs);
           sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
           results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(sAMAccountName="+user+"))",sControls);
           while(results.hasMoreElements()) {
           Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
             Attribute userNameAttrib = attrib.get("givenName");
             Attribute lAttrib= attrib.get("l");
             Attribute surName= attrib.get("sn");
             String userName = userNameAttrib.get().toString();
             String userLocation = lAttrib.get().toString();
             String userSurName = surName.get().toString();
             System.out.println("username="+userName+"location="+userLocation+"surname="+userSurName);
             userinfo=userName+"/"+userSurName+"/"+userLocation;
           }       
           ldapctx.reconnect(null);
           }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getFMInfo :"+ae);
            userinfo="";
           }catch(NamingException ne){   
            System.out.println("NamingException");   
            System.out.println("Exception At getFMInfo :"+ne);
            userinfo="";
           }catch(NullPointerException npe){
            System.out.println("NullPointerException");      
            System.out.println("Exception At getFMInfo"+npe);   
            userinfo="";
            }catch(Exception e){
            System.out.println("Exception");      
            System.out.println("Exception At getFMInfo"+e);   
            userinfo="";
           }      
          return userinfo;
    }
   
 public boolean  validateHDeskAdmin(String password)
   {
     boolean validUser=false;
     try{
           ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,"CN=Helpdeskadmin,OU=WFH,DC=uhde"); 
           ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,password);
           ldapctx.reconnect(null);
           System.out.println("Valid Helpdeskadmin Password....");
           validUser=true;
           
           }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At validateHDeskAdmin :"+ae);
            validUser=false;
           }catch(NamingException ne){   
            System.out.println("NamingException");   
            System.out.println("Exception At validateHDeskAdmin :"+ne);
            validUser=false;
           }catch(NullPointerException npe){
            System.out.println("NullPointerException");      
            System.out.println("Exception At validateHDeskAdmin"+npe);   
            validUser=false;
            }catch(Exception e){
            System.out.println("Exception");      
            System.out.println("Exception At validateHDeskAdmin"+e);   
            validUser=false;
           }            
            
       return validUser;
   }
 
 
 public Hashtable getFMEngInfoByLocation(String location)
   {
       
       try{
                hashtable  = new  Hashtable();
                sControls = new SearchControls();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"cn","sAMAccountName","objectclass","l","givenName","displayName"};
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(l="+location+"))",sControls);
                while(results.hasMoreElements()) {
                    Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                    Attribute userNameAttrib = attrib.get("givenName");
                    Attribute samAttrib= attrib.get("sAMAccountName");
                    Attribute lAttrib= attrib.get("l");
                    Attribute cnAttrib= attrib.get("cn");
                    Attribute displayNameAttrib= attrib.get("displayName");
                    
                    String userName = userNameAttrib.get().toString();
                    String userLogin = samAttrib.get().toString();
                    String userLocation = lAttrib.get().toString();
                    String userCN = cnAttrib.get().toString();
                    String displayName = displayNameAttrib.get().toString();
                    
                    if( !(userLogin.equals("Helpdeskadmin")) && !(userLogin.equals("HAdmin"))  )
                    {
                            hashtable.put(userLogin,userName); 
                            
                    } 
             }        
                
                
       }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At Searching FM Eng."+ae);
            hashtable=null;
       }catch(NamingException ne){   
            System.out.println("NamingException");      
            System.out.println("Exception At Searching FM Eng."+ne);  
            hashtable=null;
       }catch(NullPointerException npe){
            System.out.println("NullPointerException");      
            System.out.println("Exception At Searching FM Eng."+npe);   
            hashtable=null;
       }catch(Exception e){
            System.out.println("Exception");      
            System.out.println("Exception At Searching FM Eng."+e);   
            hashtable=null;
       }      
       return hashtable;
   }
 
 
 public Hashtable getFMEngDispNameByLocation(String location)
   {
       
       try{
                hashtable  = new  Hashtable();
                sControls = new SearchControls();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"cn","sAMAccountName","objectclass","l","givenName","displayName"};
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(l="+location+"))",sControls);
                while(results.hasMoreElements()) {
                    Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                    Attribute userNameAttrib = attrib.get("givenName");
                    Attribute samAttrib= attrib.get("sAMAccountName");
                    Attribute lAttrib= attrib.get("l");
                    Attribute cnAttrib= attrib.get("cn");
                    Attribute displayNameAttrib= attrib.get("displayName");
                    
                    String userName = userNameAttrib.get().toString();
                    String userLogin = samAttrib.get().toString();
                    String userLocation = lAttrib.get().toString();
                    String userCN = cnAttrib.get().toString();
                    String displayName= displayNameAttrib.get().toString();
                    
                    if(!(userLogin.equals("Helpdeskadmin")) && !(userLogin.equals("HAdmin")) )
                    {
                            hashtable.put(userLogin,displayName); 
                            
                    } 
                    
             }        
                
       }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getFMEngDispNameByLocation"+ae);
            hashtable=null;
       }catch(NamingException ne){   
            System.out.println("NamingException");      
            System.out.println("Exception At getFMEngDispNameByLocation"+ne);  
            hashtable=null;
       }catch(NullPointerException npe){
            System.out.println("NullPointerException");      
            System.out.println("Exception getFMEngDispNameByLocation"+npe);   
            hashtable=null;
       }catch(Exception e){
            System.out.println("Exception");      
            System.out.println("Exception getFMEngDispNameByLocation"+e);   
            hashtable=null;
       }      
       return hashtable;
   }

 
 
 
 /*Get All FM List */
 public Hashtable getFMEngList()
   {
       
       try{
                hashtable  = new  Hashtable();
                sControls = new SearchControls();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"sAMAccountName","objectclass","givenName"};
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(sAMAccountName=*),(givenName=*))",sControls);
                if(results.hasMoreElements()) {
                       do {
                            Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                            Attribute userNameAttrib = attrib.get("givenName");
                            Attribute samAttrib= attrib.get("sAMAccountName");
                            String userName = userNameAttrib.get().toString();
                            String userLogin = samAttrib.get().toString();
                            if(!userLogin.equals("Helpdeskadmin") && !(userLogin.equals("HAdmin")) )
                            {
                               hashtable.put(userLogin,userName);  
                            }    
                            
                           
                        } while(results.hasMoreElements());       
                }else{
                    System.out.println("rs empty");
                    hashtable=null;
                }
                    
       }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getFMEngList"+ae);         
       }catch(NamingException ne){   
            System.out.println("Exception At getFMEngList :"+ne);
       }catch(NullPointerException npe){
            System.out.println("NullPointerException");      
            System.out.println("Exception At getFMEngList"+npe);   
       }catch(Exception e){
            System.out.println("Exception");      
            System.out.println("Exception At getFMEngList"+e);   
       }  
       
       
       return hashtable;
   }

  
 public Set getADDistinctLocation() throws NamingException
   {
       
       try{
                set = new HashSet(); 
                hashtable = new Hashtable();
                env = new Hashtable();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"cn","sAMAccountName","objectclass","l","givenName"};
                SearchControls sControls = new SearchControls();
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(l=*))",sControls);
                while(results.hasMoreElements()) {
                    Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                    Attribute lAttrib= attrib.get("l");
                    String userLocation = lAttrib.get().toString();
                    System.out.println("userLocation " +userLocation);
                    set.add(userLocation);
              }      
               
       }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getADDistinctLocation"+ae);       
       }catch(NullPointerException npe){   
         System.out.println("Exception At getADDistinctLocation :"+npe);
       }catch(NamingException ne){   
         System.out.println("Exception At getADDistinctLocation :"+ne);
       }catch(Exception e){   
         System.out.println("Exception At getADDistinctLocation :"+e);
       }   
       
 return set ;
 }
 
 
 /*List FM Companies */
 public Set getDistinctFMCompany() throws NamingException
   {
       
       try{
                set = new HashSet(); 
                hashtable = new Hashtable();
                env = new Hashtable();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"cn","sAMAccountName","objectclass","l","company"};
                SearchControls sControls = new SearchControls();
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(company=*))",sControls);
                while(results.hasMoreElements()) {
                    Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                    Attribute lAttrib= attrib.get("company");
                    String company = lAttrib.get().toString();
                    System.out.println("Company " +company);
                    set.add(company);
                 }      
       
        }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getDistinctFMCompany"+ae); 
             set=null;
       }catch(NullPointerException npe){   
         System.out.println("Exception At getDistinctFMCompany :"+npe);
          set=null;
       }catch(NamingException ne){   
         System.out.println("Exception At getDistinctFMCompany :"+ne);
          set=null;
       }catch(Exception e){   
         System.out.println("Exception At getDistinctFMCompany :"+e);
          set=null;
       }           
       return set ;
 }
 /*FM List By Company */
  public Set getFMEngByCompany(String company)
   {
       
       try{
                set = new HashSet(); 
                hashtable  = new  Hashtable();
                sControls = new SearchControls();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"sAMAccountName","objectclass","company"};
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(company="+company+"))",sControls);
                while(results.hasMoreElements()) {
                    Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                    Attribute samAttrib= attrib.get("sAMAccountName");
                    String userLogin = samAttrib.get().toString();
                    System.out.println("Company " +userLogin);
                    if(!(userLogin.equals("Helpdeskadmin")) && !(userLogin.equals("HAdmin")) )
                    {
                        set.add(userLogin);
                    }   
                    
                }         
                
       }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getFMEngByCompany"+ae);       
       }catch(NullPointerException npe){   
         System.out.println("Exception At getFMEngByCompany :"+npe);
       }catch(NamingException ne){   
         System.out.println("Exception At getFMEngByCompany :"+ne);
       }catch(Exception e){   
         System.out.println("Exception At getFMEngByCompany :"+e);
       }     
       return set;
   }
 
 public Set getFMEngByCompany(String company,String location)
   {
       
       try{
                set = new HashSet(); 
                hashtable  = new  Hashtable();
                sControls = new SearchControls();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"sAMAccountName","objectclass","company","l"};
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(company="+company+"),(l="+location+"))",sControls);
                while(results.hasMoreElements()) {
                    Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                    Attribute samAttrib= attrib.get("sAMAccountName");
                    String userLogin = samAttrib.get().toString();
                    System.out.println("Company " +userLogin);
                    if(!(userLogin.equals("Helpdeskadmin")) && !(userLogin.equals("HAdmin")) )
                    {
                    set.add(userLogin);
                    }   
                   
                    
                }         
                
      }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getFMEngByCompany"+ae);       
       }catch(NullPointerException npe){   
         System.out.println("Exception At getFMEngByCompany :"+npe);
       }catch(NamingException ne){   
         System.out.println("Exception At getFMEngByCompany :"+ne);
       }catch(Exception e){   
         System.out.println("Exception At getFMEngByCompany :"+e);
       }    
       return set;
   }
 
 /*
 public TechnicianInfo getTecnicianInfo(String techniId)
   {
    
       TechnicianInfo techni =null ;
     try{      
       
           ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
           ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
           SearchControls sControls = new SearchControls();
           String[] attrIDs = {"cn","sAMAccountName","objectclass","l","givenName","sn","company"};
           sControls.setReturningAttributes(attrIDs);
           sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
           results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(sAMAccountName="+techniId+"))",sControls);
           while(results.hasMoreElements()) {
           Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
             Attribute userNameAttrib = attrib.get("givenName");
             Attribute lAttrib= attrib.get("l");
             Attribute surNameAttrib= attrib.get("sn");
             Attribute companyAttrib= attrib.get("company");
             String userName = userNameAttrib.get().toString();
             String userLocation = lAttrib.get().toString();
             String userSurName = surNameAttrib.get().toString();
             String company = companyAttrib.get().toString();
             techni=new TechnicianInfo(userName,userLocation,userSurName,company); 
            
           }       
           ldapctx.reconnect(null);
           
           }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception "+ae);   
            System.out.println("Exception At getTecnicianInfo"+ae);
            techni=null;
           }catch(NamingException ne){   
            System.out.println("NamingException :");   
            System.out.println("Exception At getTecnicianInfo"+ne);
            techni=null;
           }catch(NullPointerException e){
            System.out.println("NullPointerException");      
            System.out.println("NullPointer Exception at getTecnicianInfo"+e);   
            techni=null;
           }catch(Exception e){
            System.out.println("General Exception");      
            System.out.println("NullPointer Exception at getTecnicianInfo"+e);   
            techni=null;
           }            
          return techni;
          
    }
 
*/
   
     
   public String getTechnicianDisplayName(String user)
   {
     String displayName="";
     try{      
       
           ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
           ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
           SearchControls sControls = new SearchControls();
           String[] attrIDs = {"sAMAccountName","objectclass","givenName","displayName"};
           sControls.setReturningAttributes(attrIDs);
           sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
           results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(sAMAccountName="+user+"))",sControls);
           while(results.hasMoreElements()) {
           Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
             Attribute displayNameAttrib = attrib.get("displayName");
             displayName= displayNameAttrib.get().toString();
             
           }       
           ldapctx.reconnect(null);
      }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getFMInfo :"+ae);
            displayName="N.S.";
      }catch(NamingException ne){   
            System.out.println("NamingException");   
            System.out.println("Exception At getFMInfo :"+ne);
            displayName="N.S.";
      }catch(NullPointerException npe){
            System.out.println("NullPointerException");      
            System.out.println("Exception At getFMInfo"+npe);   
            displayName="N.S.";
      }catch(Exception e){
            System.out.println("Exception");      
            System.out.println("Exception At getFMInfo"+e);   
            displayName="N.S.";
      }      
          return displayName;
          
    }
   
   
public Hashtable getFMEngListDispName()
   {
    
       try{
                hashtable  = new  Hashtable();
                sControls = new SearchControls();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"sAMAccountName","objectclass","givenName","displayName"};
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(sAMAccountName=*),(givenName=*))",sControls);
                if(results.hasMoreElements()) {
                       do {
                            Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                            Attribute userNameAttrib = attrib.get("givenName");
                            Attribute samAttrib= attrib.get("sAMAccountName");
			    Attribute displayNameAttrib= attrib.get("displayName");
	
                            String userName = userNameAttrib.get().toString();
                            String userLogin = samAttrib.get().toString();
			    String displayName= displayNameAttrib.get().toString();	
			    	
                            if( !(userLogin.equals("Helpdeskadmin")) && !(userLogin.equals("HAdmin")) )   {
                                
                               hashtable.put(userLogin,displayName);  
                               
                            }    
                            
                        }while(results.hasMoreElements());       
                       
                }else{
                                   System.out.println("rs empty");
                                   hashtable=null;
                }
                    
    
       }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getFMEngListDispName"+ae);         
       }catch(NamingException ne){   
             System.out.println("NamingException");      
            System.out.println("Exception At getFMEngListDispName"+ne);
       }catch(NullPointerException npe){
            System.out.println("NullPointerException");      
            System.out.println("Exception At getFMEngListDispName"+npe);   
       }catch(Exception e){
            System.out.println("Exception");      
            System.out.println("Exception At getFMEngListDispName"+e);   
       }  
       return hashtable;
   }


public Hashtable getFMEngListForEscalation(String technicain,String alpha)
   {
    
       try{
                hashtable  = new  Hashtable();
                sControls = new SearchControls();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"sAMAccountName","objectclass","givenName","displayName"};
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(sAMAccountName=*),(givenName=*))",sControls);
                if(results.hasMoreElements()) {
                       do {
                            Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                            Attribute userNameAttrib = attrib.get("givenName");
                            Attribute samAttrib= attrib.get("sAMAccountName");
			    Attribute displayNameAttrib= attrib.get("displayName");
	
                            String userName = userNameAttrib.get().toString();
                            String userLogin = samAttrib.get().toString();
			    String displayName= displayNameAttrib.get().toString();	
			    	
                            if( !(userLogin.equals("Helpdeskadmin")) && !(userLogin.equals("HAdmin")) && !(userLogin.equals(technicain)) &&( alpha.length()!=0 ) && (alpha.length()<= displayName.length())  )   {
                                        int len=0;
                                        String tempStr="";
                                        len = alpha.length();
                                        if( (tempStr=displayName.substring(0,len)).equalsIgnoreCase(alpha) ){
                                            hashtable.put(userLogin,displayName);  
                                       }
                                        
                                        
                            }    
                            
                        }while(results.hasMoreElements());       
                       
                }else{
                      hashtable=null;
                }
                    
    
       }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getFMEngListForEscalation"+ae);  
            hashtable=null;
       }catch(NamingException ne){   
             System.out.println("NamingException");      
             System.out.println("Exception At getFMEngListForEscalation"+ne);
             hashtable=null;
       }catch(NullPointerException npe){
             System.out.println("NullPointerException");      
             System.out.println("Exception At getFMEngListForEscalation"+npe);   
             hashtable=null;
       }catch(Exception e){
             System.out.println("Exception");      
             System.out.println("Exception At getFMEngListForEscalation"+e);   
             hashtable=null;
       }  
       
       return hashtable;
   }



public boolean validateTechnician(String technicain)
   {
       boolean validTechnician=false;
       
       try{
                
                hashtable  = new  Hashtable();
                sControls = new SearchControls();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"sAMAccountName","objectclass","givenName","displayName"};
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(sAMAccountName=*),(givenName=*))",sControls);
                if(results.hasMoreElements()) {
                       do {
                            Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                            Attribute userNameAttrib = attrib.get("givenName");
                            Attribute samAttrib= attrib.get("sAMAccountName");
			    Attribute displayNameAttrib= attrib.get("displayName");
	
                            String userName = userNameAttrib.get().toString();
                            String userLogin = samAttrib.get().toString();
			    String displayName= displayNameAttrib.get().toString();	
			    	
                            if( !(userLogin.equals("Helpdeskadmin")) && !(userLogin.equals("HAdmin")) && (displayName.equals(technicain))  )   {
                                    validTechnician=true;  
                                     break;
                             
                            }    
                            
                        }while(results.hasMoreElements());       
                       
                }else{
                             validTechnician=false;            
                }
                    
    
       }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At validateTechnician"+ae);  
            validTechnician=false;
       }catch(NamingException ne){   
             System.out.println("NamingException");      
             System.out.println("Exception At validateTechnician"+ne);
             validTechnician=false;
       }catch(NullPointerException npe){
             System.out.println("NullPointerException");      
             System.out.println("Exception At validateTechnician"+npe);   
             validTechnician=false;
       }catch(Exception e){
             System.out.println("Exception");      
             System.out.println("Exception At getFMEngListDispName"+e);   
             validTechnician=false;
       }  
       
       return validTechnician;
   }


public String  getTechnicianByDispName(String techDispName)
   {
       String technicianId="--";
       
       try{
                
                hashtable  = new  Hashtable();
                sControls = new SearchControls();
                ldapctx.addToEnvironment(Context.SECURITY_PRINCIPAL,HelpDeskAdminId); 
                ldapctx.addToEnvironment(Context.SECURITY_CREDENTIALS,HelpDeskPassWord);    
                String[] attrIDs = {"sAMAccountName","objectclass","displayName"};
                sControls.setReturningAttributes(attrIDs);
                sControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                results =ldapctx.search("OU=WFH,DC=uhde","(&(objectclass=*),(sAMAccountName=*),(displayName="+techDispName+"))",sControls);
                if(results.hasMoreElements()) {
                     
                            Attributes attrib = ( (SearchResult)results.nextElement() ).getAttributes();
                            Attribute samAttrib= attrib.get("sAMAccountName");
			    Attribute displayNameAttrib= attrib.get("displayName");
	                    String userLogin = samAttrib.get().toString();
			    String displayName= displayNameAttrib.get().toString();	
                   	    technicianId=userLogin;
                }else{
                             technicianId="--";            
                }
                    
    
       }catch(AuthenticationException  ae){
            System.out.println("Authentication Exception");   
            System.out.println("Exception At getTechnicianByDispName"+ae);  
            technicianId="--";
       }catch(NamingException ne){   
             System.out.println("NamingException");      
             System.out.println("Exception At getTechnicianByDispName"+ne);
            technicianId="--";
       }catch(NullPointerException npe){
             System.out.println("NullPointerException");      
             System.out.println("Exception At getTechnicianByDispName"+npe);   
            technicianId="--";
       }catch(Exception e){
             System.out.println("Exception");      
             System.out.println("Exception At getTechnicianByDispName"+e);   
             technicianId="--";
       }  
       
       return technicianId;
   }



     
      
   
}

