package webfr.flashcards.shared;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries({
  @NamedQuery(
      name="findUserByName",
      query = "SELECT u FROM User u WHERE u.name = :username")
})
@Table(name="USERS")
public class User {
  @SequenceGenerator(name="SEQUENCE_GENERATOR", sequenceName="seq", initialValue=1, allocationSize=1)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_GENERATOR")
  @Column(name="Id")
  private int id;
  @Column(name="Name")
  private String name ="";
  @Column(name="PasswordToken")
  private int token;
  @Transient
  private List<Bookmark> bookmarks = new LinkedList<Bookmark>();
  
  public List<Bookmark> getBookmarks() {
    return bookmarks;
  }

  public void setName(String name) {
    if (name != null) {
      this.name = name;
    }
  }
  
  public void setPassword(String pwd) {
    // this can be done better
    if (pwd != null) {
      token = pwd.hashCode();
    }
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getToken() {
    return this.token;
  }
  
}
