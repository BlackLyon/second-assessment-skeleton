package misc;

import javax.persistence.Entity;

import com.cooksys.ftd.entity.Credentials;

@Entity
public class TweetPost {

	private String content;
	private Credentials credentials;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
