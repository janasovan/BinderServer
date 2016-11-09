package com.niit.binder.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.binder.dao.FriendDAO;
import com.niit.binder.model.Friend;
import com.niit.binder.model.Users;

@RestController
public class FriendController {

	Logger log = Logger.getLogger(FriendController.class);
	
	@Autowired
	Friend friend;
	
	@Autowired
	FriendDAO friendDAO;
	
	/**
	 * http://localhost:8081/Binder/myFriends			//working
	 * @param session
	 * @return
	 */
	@GetMapping(value = "/myFriends")
	public ResponseEntity<List<Friend>> myFriends(HttpSession session) {
		log.debug("**********Starting of myFriends() method");
		String userId = (String) session.getAttribute("loggedInUserID");
		List<Friend> myFriends = friendDAO.getMyFriends(userId);
		log.debug("**********End of myFriends() method");
		return new ResponseEntity<List<Friend>> (myFriends, HttpStatus.OK);
	}
	
	/**
	 * http://localhost:8081/Binder/addFriend/{friendId}			//working
	 * @param friendId
	 * @param session
	 * @return
	 */
	@PostMapping(value = "/addFriend/{friendId}")			
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
		log.debug("**********Starting of sendFriendRequest() method");
		String userId = (String) session.getAttribute("loggedInUserID");
		
		friend.setUserId(userId);
		friend.setFriendId(friendId);
		friend.setStatus("N");	// N = New, A = Accepted, R = Rejected, U = Unfriend 
		friendDAO.sendFriendRequest(friend);
		
		log.debug("**********End of sendFriendRequest() method");
		
		return new ResponseEntity<Friend> (friend, HttpStatus.OK);
	}
	
	/**
	 * http://localhost:8081/Binder/unFriend/{friendId}	
	 * @param friendId
	 * @param session
	 * @return
	 */
	@PutMapping(value = "/unFriend/{friendId}")			
	public ResponseEntity<Friend> unFriend(@PathVariable("friendId") String friendId, HttpSession session) {
		log.debug("**********Starting of unFriend() method");
		String userId = (String) session.getAttribute("loggedInUserID");
		friend.setUserId(userId);
		friend.setFriendId(friendId);
		friend.setStatus("U");	// N = New, A = Accepted, R = Rejected, U = Unfriend 
		friendDAO.update(friend);
		log.debug("**********End of unFriend() method");
		return new ResponseEntity<Friend> (friend, HttpStatus.OK);
	}
	
	/**
	 * http://localhost:8081/Binder/rejectFriend/{friendId}
	 * @param friendId
	 * @param session
	 * @return
	 */
	@PutMapping(value = "/rejectFriend/{friendId}")				
	public ResponseEntity<Friend> rejectFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
		log.debug("**********Starting of rejectFriendRequest() method");
		String userId = (String) session.getAttribute("loggedInUserID");
		friend.setUserId(userId);
		friend.setFriendId(friendId);
		friend.setStatus("R");	// N = New, A = Accepted, R = Rejected, U = Unfriend  
		friendDAO.update(friend);
		log.debug("**********End of rejectFriendRequest() method");
		return new ResponseEntity<Friend> (friend, HttpStatus.OK);
	}
	
	/**
	 * http://localhost:8081/Binder/acceptFriend/{friendId}			//working
	 * @param friendId
	 * @param session
	 * @return
	 */
	@PutMapping(value = "/acceptFriend/{friendId}")			
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
		log.debug("**********Starting of acceptFriendRequest() method");
		String userId = (String) session.getAttribute("loggedInUserID");
		friend.setUserId(userId);
		friend.setFriendId(friendId);
		friend.setStatus("A");	// N = New, A = Accepted, R = Rejected, U = Unfriend 
		friendDAO.update(friend);
		log.debug("**********End of acceptFriendRequest() method");
		return new ResponseEntity<Friend> (friend, HttpStatus.OK);
	}
	
	/**
	 * http://localhost:8081/Binder/newFriendRequests
	 * @param session
	 * @return
	 */
	@GetMapping(value = "/newFriendRequests")			
	public ResponseEntity<Friend> newFriendRequests(HttpSession session) {
		log.debug("**********Starting of newFriendRequests() method");
		String userId = (String) session.getAttribute("loggedInUserID");
		friendDAO.getNewFriendRequests(userId);
		log.debug("**********End of newFriendRequests() method");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}	
}
