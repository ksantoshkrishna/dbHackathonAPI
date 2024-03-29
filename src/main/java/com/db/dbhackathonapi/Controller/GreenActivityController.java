package com.db.dbhackathonapi.Controller;


import com.db.dbhackathonapi.Constants;
import com.db.dbhackathonapi.Repository.GreenActivityRepository;
import com.db.dbhackathonapi.Repository.TravelActivityRepository;
import com.db.dbhackathonapi.Tables.GreenActivity;
import com.db.dbhackathonapi.Tables.TravelActivity;
import com.db.dbhackathonapi.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.db.dbhackathonapi.StatusCodeEnum.*;


/*
either you can use
 */

@RestController    // This means that this class is a Controller
@RequestMapping(path="/activities/green-activity") // This means URL's start with /demo (after Application path)
public class GreenActivityController {

	@Autowired
	private GreenActivityRepository greenActivityRepository;

	@CrossOrigin
	@GetMapping(value = "/{userEmail}")
	public Response getGreenActivityData( @PathVariable String userEmail){
		try{
			List<GreenActivity>activities=greenActivityRepository.findAllByUserEmail(userEmail);
			return new Response(OK,"Green Activity data","Green activity Data has "+activities.size()+" rows",activities);
		}catch (Exception e){
			return new Response(ERROR, e.getMessage(), Arrays.toString(e.getStackTrace()), null);
		}
	}

	@CrossOrigin
	@GetMapping(value = "/types")
	public Response getGreenActivityTypes( ){
		try{
			List<String>activities= Constants.greenActivityList;
			return new Response(OK,"Green Activity Types","Types of Green activities could be configured here, total "+activities.size()+" rows",activities);
		}catch (Exception e){
			return new Response(ERROR, e.getMessage(), Arrays.toString(e.getStackTrace()), null);
		}
	}

	@CrossOrigin
	@PostMapping("/add")
	public Response addActivity(@RequestBody GreenActivity greenActivity) {

		try {
			GreenActivity activity = greenActivityRepository.save(greenActivity);
			return new Response(OK, "Added .", "Green Activity Added", activity);
		} catch (Exception e) {
			return new Response(ERROR, "Error", Arrays.toString(e.getStackTrace()), null);
		}
	}

	@CrossOrigin
	@PostMapping("/modify")
	public Response modifyActivity(@RequestBody GreenActivity greenActivity) {

		System.out.println(greenActivity);
		Optional<GreenActivity> u=greenActivityRepository.findById(greenActivity.getId());
		if(!u.isPresent()) {
			return new Response(WARNING,"Not Exists","Row Not Exists, Id:"+greenActivity.getId(),null);
		}

		try {
			greenActivityRepository.modifyUserInfoById(
					greenActivity.getType(),
					greenActivity.getGhgFootprint(),
					greenActivity.getId()
			);

			return new Response(OK, "Modified .", "Green Activity Modified", greenActivity);
		} catch (Exception e) {
			return new Response(ERROR, e.getMessage(), Arrays.toString(e.getStackTrace()), greenActivity);
		}
	}

	@CrossOrigin
	@PostMapping("/delete")
	public Response deleteActivity(@RequestBody GreenActivity greenActivity) {
		Optional<GreenActivity> u=greenActivityRepository.findById(greenActivity.getId());
		if(!u.isPresent()) {
			return new Response(WARNING,"Not Exists","Row Not Exists, Id:"+greenActivity.getId(),null);
		}
		try {
			greenActivityRepository.deleteById(greenActivity.getId());
			return new Response(OK, "Deleted", "Green Activity Deleted", greenActivity);
		} catch (Exception e) {
			return new Response(ERROR, e.getMessage(), Arrays.toString(e.getStackTrace()), greenActivity);
		}
	}
}