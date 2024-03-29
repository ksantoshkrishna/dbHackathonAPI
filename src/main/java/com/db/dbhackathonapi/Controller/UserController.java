package com.db.dbhackathonapi.Controller;


import com.db.dbhackathonapi.response.Response;
import com.db.dbhackathonapi.Tables.User;
import com.db.dbhackathonapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

import static com.db.dbhackathonapi.StatusCodeEnum.*;


/*
either you can use
 */

@RestController    // This means that this class is a Controller
@RequestMapping(path="/users") // This means URL's start with /demo (after Application path)
public class UserController {

	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	@CrossOrigin
	@PostMapping("/login")
	public Response login(@RequestBody User user){

		Optional<User>u=userRepository.findById(user.getEmail());

		if(u.isPresent()){
			if(u.get().getPassword().equals(user.getPassword()))
		        return new Response(OK,"Logged In.","You have successfully Signed In",u);
			else
				return new Response(WARNING,"Wrong Password","Please re-enter password",null);
		}
		else
			return new Response(ERROR,"User Not Found","Please enter again",null);
	}

	@CrossOrigin
	@PostMapping("/signup")
	public Response signUp(@RequestBody User user){

		try{
			Optional<User>u=userRepository.findById(user.getEmail());
			if(u.isPresent()) {
				return new Response(WARNING,"Already Signed Up","User already exists, please login",null);
			}
			User userOutput = userRepository.save(user);
			return new Response(OK, "Signed Up.", "You have successfully Signed Up", userOutput);
		}
		catch(Exception e){
			return new Response(ERROR, "Error", Arrays.toString(e.getStackTrace()), null);
		}
}

}