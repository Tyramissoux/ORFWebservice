package security;


public class SecuritySetup {
	
	public SecuritySetup(){
		
		System.setSecurityManager(new SecurityManager());
		
	}

}
