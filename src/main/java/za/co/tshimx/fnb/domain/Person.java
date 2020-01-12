package za.co.tshimx.fnb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Person", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true, length=11)
	private int id;
	 @Column(name="TITLE", length=10, nullable=false)
	private String title;
         
	@Column(name="FIRSTNAME", length=50, nullable=false)
	private String firstName;
	
	@Column(name="SURNAME", length=50, nullable=false)
	private String surname;
        
        @Column(name="EMAIL", length=50, nullable=false)
	private String email;
        
        @Column(name="MOBILE_NUMBER", length=20, nullable=true)
	private String mobileNumber;
        
        @Column(name="IDNUMBER", length=13, nullable=false)
	private String idNumber;
        
        @Column(name="RSA_CITIZEN", length=10, nullable=false)
	private String rsa_citizen;
        
        @Column(name="USERNAME", length=50, nullable=false)
	private String username;
        
        @Column(name="PASSWORD", length=50, nullable=false)
	private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emael) {
        this.email = emael;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    

    public String getRsa_citizen() {
        return rsa_citizen;
    }

    public void setRsa_citizen(String rsa_citizen) {
        this.rsa_citizen = rsa_citizen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "Person{" + "title=" + title + ", firstName=" + firstName + ", surname=" + surname + ", email=" + email + ", mobileNumber=" + mobileNumber + ", idNumber=" + idNumber + ", rsa_citizen=" + rsa_citizen + ", username=" + username + ", password=" + password + '}';
    }
        
        
	
}
