package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class UserSession extends Model{
	
	//@OneToOne
	//SessionRole sessionRole;
	@ManyToOne
	public User user;
	@OneToOne
	public Branch branch;
	public String role; 
	
	public UserSession(User user, Branch branch, String role) {
		this.user = user;
		this.branch = branch;
		this.role = role;
	}
	
	public static UserSession findByUserAndBranch(long userId,  long branchId) {
		String queryString = "select us " +
				"from UserSession us " +
				"where us.user.id = ? " +
				"and us.branch.id = ?";
		return UserSession.find(queryString, userId, branchId).first();
	}
}
