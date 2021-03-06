package controllers;

import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

/**
 * 
 * @author Nermin Graca & Nedzad Hamzic & Neldin Dzekovic
 *
 */
public class Application extends Controller {
	
	static Form<User> newUser = new Form<User>(User.class);
	
	/**
	 * Either directs to the index.html with the session name already logged in
	 * or directs to the index.html page with "Unknown" as username;
	 * @return
	 */
    public static Result index() {
    	String usernameSes = session("username");
    	if (usernameSes == null) {
    		usernameSes = "Unknown";
    		return ok(index.render( "Your new application is ready.", usernameSes));
    	} else {
    		return ok(index.render( "Your new application is ready.", usernameSes));
    	}
    }
    
    /**
     * Renders the registration.html page;
     * @return
     */
    public static Result registration() {
    	return ok(registration.render());
    }
    
    /**
     * Renders the login.html page;
     * @return
     */
    public static Result login() {
    	return ok(login.render());
    }
    
    /**
     * 1. Gets the username and password from the form from the Registration.html page;
     * 2. Creates the User using the User.create() method;
     * 3. And redirects to the index.html page;
     * @return
     */
    public static Result addUser() {
    	String username = newUser.bindFromRequest().get().username;
    	String password = newUser.bindFromRequest().get().password;
    	User.create(username, password);
    	return redirect("/");
    	
    }
    
    /**
     * 1. Gets the username and password from the form from the Login.html page;
     * 2. The finder() method finds the User with the entered username; assigns to the User u;
     * 3. If no User has been found, - redirecting to Failed.html page;
     * 4. If the User has been found - Checks whether the password is correct;
     * 5. If the password is wrong - redirecting to Failed.html page;
     * 6. If the password is correct - redirecting to Success.html page;
     * Note* Store the username in sesssion varibale if the Login is successfull;
     * @return
     */
    public static Result findUser() {
    	String username = newUser.bindFromRequest().get().username;
    	String password = newUser.bindFromRequest().get().password;
    	User u = User.finder(username);
    	if (u == null) {
    		return redirect("/failed");
    	} else {
    		if (u.password.equals(password)) {
    			// the username put in the session variable unders the key "username";
    			session("username", username);
    			return redirect("/success");
    		} else {
    			return redirect("/failed");
    		}
    		
    	}
    }
    
    /**
     * Redirects to the Success.html page 
     * with the session variables sent as parameters;
     * @return
     */
    public static Result success() {
    	String usernameSes = session("username");
    	return ok(success.render(usernameSes));
    }
    
    /**
     * Redirects to the Failed.html page;
     * @return
     */
    public static Result failed() {
    	return ok(failed.render());
    }

}
