@SuppressWarnings("rawtypes")
public class Person implements Comparable {
 
  private int id;
  private String lastname;
  private String firstname;
 
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getLastname() {
    return lastname;
  }
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
  public String getFirstname() {
    return firstname;
  }
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
  public String toString() {
    return id+": "+firstname+" "+lastname;
  }
  public int compareTo(Person with) {
    return this.getId() - with.getId();
  }
  public int compareTo(Object with) {
	    return this.getId();
	  }
}