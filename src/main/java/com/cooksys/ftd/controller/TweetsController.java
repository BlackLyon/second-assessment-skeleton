package com.cooksys.ftd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.entity.Credentials;
import com.cooksys.ftd.entity.Tweets;
import com.cooksys.ftd.entity.Users;
import com.cooksys.ftd.projections.TweetsProjection;
import com.cooksys.ftd.service.TweetsService;
import com.cooksys.ftd.service.UsersService;
import com.cooksys.ftd.service.ValidateService;

import misc.TweetPost;

@RestController
@RequestMapping("tweets")
public class TweetsController {
	
	TweetsService tweetsService;
	UsersService usersService;
	ValidateService validateService;
	
	public TweetsController(TweetsService service, UsersService uService, ValidateService vService) {
		tweetsService = service;
		usersService = uService;
		validateService = vService;
	}
	
	public List<String> captureHashTag(String content) {
		
		Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
		
		Matcher mat = MY_PATTERN.matcher(content);
		
		List<String> strs = new ArrayList<String>();
		
		while (mat.find()) {
		  //System.out.println(mat.group(1));
		  strs.add(mat.group(1));
		}
		return strs;
	}
	
	public void captureMention(String content) {

		Pattern MY_PATTERN = Pattern.compile("@(\\S+)");

		Matcher mat = MY_PATTERN.matcher(content);

		List<String> strs = new ArrayList<String>();

		while (mat.find()) {
			// System.out.println(mat.group(1));
			strs.add(mat.group(1));
		}
	}

	//Retrieves all (non-deleted) tweets. The tweets should appear in reverse-chronological order.
	//return an array of tweets
	//working at a basic level
	@GetMapping()
	public List<TweetsProjection> getTweets() {
		return tweetsService.getTweets();
	}
	
	//Retrieves a tweet with a given id. If no such tweet exists, or the given tweet is deleted, an error should be sent in lieu of a response.
	//return a tweets instance
	//done
	@GetMapping("/{id}")
	public TweetsProjection getTweetsTag(@PathVariable Long id) throws Exception {
		
		if(validateService.checkTweet(id)) {
			return tweetsService.findByid(id);
		}
		else
		{
			throw new Exception("No such tweet exists!");
		}
		
	}
	
	//Retrieves the tags associated with the tweet with the given id. If that tweet is deleted or otherwise doesn't exist, 
	//an error should be sent in lieu of a response.
	//IMPORTANT Remember that tags and mentions must be parsed by the server!
	//return an array of hashtags
	@GetMapping("/{id}/tags")
	public String getTags(@PathVariable String id) {
		return "I am working";
	}
	
	//Retrieves the active users who have liked the tweet with the given id. If that tweet is deleted or otherwise doesn't exist, 
	//an error should be sent in lieu of a response.
	//Deleted users should be excluded from the response.
	//return an array of users
	@GetMapping("tweets/{id}/likes")
	public String getLikes(@PathVariable String id) {
		return "I am working";
	}
	
	//Retrieves the context of the tweet with the given id. If that tweet is deleted or otherwise doesn't exist, an error should be sent in lieu of a response.
	//IMPORTANT: While deleted tweets should not be included in the before and after properties of the result, transitive replies should. 
	//What that means is that if a reply to the target of the context is deleted, but there's another reply to the deleted reply, 
	//the deleted reply should be excluded but the other reply should remain.
	@GetMapping("tweets/{id}/context")
	public String getContext(@PathVariable String id) {
		return "I am working";
	}
	
	//Retrieves the direct replies to the tweet with the given id. If that tweet is deleted or otherwise doesn't exist, 
	//an error should be sent in lieu of a response.
	//Deleted replies to the tweet should be excluded from the response.
	//return an array of tweets
	//done
	@GetMapping("/{id}/replies")
	public List<TweetsProjection> getReplies(@PathVariable Long id) throws Exception {
		
		if(validateService.checkTweet(id)) {
			return tweetsService.getReplies(id);
		}
		else
		{
			throw new Exception("Either the tweet does not exist or there are no replies to the tweet!");
		}
	
	}
	
	//Retrieves the direct reposts of the tweet with the given id. If that tweet is deleted or otherwise doesn't exist, 
	//an error should be sent in lieu of a response.
	//Deleted reposts of the tweet should be excluded from the response.
	//done
	@GetMapping("tweets/{id}/reposts")
	public List<TweetsProjection> getReposts(@PathVariable Long id) throws Exception {
		
		if(validateService.checkTweet(id)) {
			return tweetsService.getReposts(id);
		}
		else
		{
			throw new Exception("Either the tweet does not exist or there are no reposts of the tweet!");
		}
	}
	
	//Retrieves the users mentioned in the tweet with the given id. If that tweet is deleted or otherwise doesn't exist, 
	//an error should be sent in lieu of a response.
	//Deleted users should be excluded from the response.
	//IMPORTANT Remember that tags and mentions must be parsed by the server!
	//return an array of users
	@GetMapping("tweets/{id}/mentions")
	public String getMentions(@PathVariable String id) {
		return "I am working";
	}
	
	//Creates a new simple tweet, with the author set to the user identified by the credentials in the request body. 
	//If the given credentials do not match an active user in the database, an error should be sent in lieu of a response.
	//The response should contain the newly-created tweet.
	//Because this always creates a simple tweet, it must have a content property and may not have inReplyTo or repostOf properties.
	//IMPORTANT: when a tweet with content is created, the server must process the tweet's content for @{username} mentions and #{hashtag} tags. 
	//There is no way to create hashtags or create mentions from the API, so this must be handled automatically!
	//return a tweet instance
	//working at a basic level
	@PostMapping()
	public TweetsProjection postTweets(@RequestBody TweetPost tweetPost) throws Exception {
		Tweets tweet = new Tweets();
		
		if(validateService.checkName(tweetPost.getCredentials().getUsername()) && validateService.checkPassword(tweetPost.getCredentials().getPassword()))
		{
			Users user = usersService.findByusername(tweetPost.getCredentials().getUsername());
			tweet.setAuthor(user);
			tweet.setContent(tweetPost.getContent());
			tweetsService.postSimpleTweet(tweet);
			return tweetsService.findByid(tweet.getId());
		}
		else
		{
			throw new Exception("Could not post tweet!");
		}
	}
	
	//Creates a "like" relationship between the tweet with the given id and the user whose credentials are provided by the request body. 
	//If the tweet is deleted or otherwise doesn't exist, or if the given credentials do not match an active user in the database, an error should be sent. 
	//Following successful completion of the operation, no response body is sent.
	//no return
	@PostMapping("tweets/{id}/like")
	public String like(@PathVariable String id) {
		return "I am working";
	}
	
	//Creates a reply tweet to the tweet with the given id. The author of the newly-created tweet should match the credentials provided by the request body. 
	//If the given tweet is deleted or otherwise doesn't exist, or if the given credentials do not match an active user in the database, 
	//an error should be sent in lieu of a response.
	//Because this creates a reply tweet, content is not optional. Additionally, notice that the inReplyTo property is not provided by the request. 
	//The server must create that relationship.
	//The response should contain the newly-created tweet.
	//IMPORTANT: when a tweet with content is created, the server must process the tweet's content for @{username} mentions and #{hashtag} tags. 
	//There is no way to create hashtags or create mentions from the API, so this must be handled automatically!
	//return a tweets instance
	//mostly done
	@PostMapping("/{id}/reply")
	public TweetsProjection reply(@PathVariable Long id, @RequestBody TweetPost post) throws Exception {
		
		if(validateService.checkPassword(post.getCredentials().getPassword())) {
			Tweets tweet = tweetsService.getId(id);
			Tweets addedTweet = new Tweets();
			
			if(validateService.checkTweet(id) && !tweet.isDeleted())
			{
				addedTweet.setContent(post.getContent());
				addedTweet.setInReplyTo(tweet);
				tweetsService.postSimpleTweet(addedTweet);
				return tweetsService.findByid(addedTweet.getId());
			}
			else
			{
				throw new Exception("Tweet does not exist");
			}
		}
		else
		{
			throw new Exception("Incorrect password");
		}
	}
	
	//Creates a repost of the tweet with the given id. The author of the repost should match the credentials provided in the request body. 
	//If the given tweet is deleted or otherwise doesn't exist, or the given credentials do not match an active user in the database, 
	//an error should be sent in lieu of a response.
	//Because this creates a repost tweet, content is not allowed. Additionally, notice that the repostOf property is not provided by the request. 
	//The server must create that relationship.
	//The response should contain the newly-created tweet.
	//return a tweets instance
	//mostly done
	@PostMapping("/{id}/repost")
	public TweetsProjection repost(@PathVariable Long id, @RequestBody Credentials credentials) throws Exception {
		
		if(validateService.checkPassword(credentials.getPassword())) {
			Tweets tweet = tweetsService.getId(id);
			Tweets repost = new Tweets();
			
			if(validateService.checkTweet(id) && !tweet.isDeleted())
			{
				repost.setRepostOf(tweet);
				tweetsService.postSimpleTweet(repost);
				return tweetsService.findByid(repost.getId());
			}
			else
			{
				throw new Exception("Tweet does not exist");
			}
		}
		else
		{
			throw new Exception("Incorrect password");
		}
	}
	//"Deletes" the tweet with the given id. If no such tweet exists or the provided credentials do not match author of the tweet, 
	//an error should be sent in lieu of a response. If a tweet is successfully "deleted", the response should contain the tweet data prior to deletion.
	//IMPORTANT: This action should not actually drop any records from the database! Instead, develop a way to keep track of "deleted" tweets so that even 
	//if a tweet is deleted, data with relationships to it (like replies and reposts) are still intact.
	//return a tweets instance
	//done
	@DeleteMapping("/{id}")
	public TweetsProjection deleteTweet(@PathVariable Long id, @RequestBody Credentials credentials) throws Exception {
		Tweets tweet = tweetsService.getId(id);
		
		if(validateService.checkTweet(tweet.getId()) && validateService.checkPassword(credentials.getPassword())) {
			tweetsService.ToggleStatus(tweet, true);
			return tweetsService.findByid(tweet.getId());
		}
		else 
		{
			throw new Exception("Tweet could not be deleted");
		}
	}
	
	
}
