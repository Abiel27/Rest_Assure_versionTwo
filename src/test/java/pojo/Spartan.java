package pojo;

// This class is meant to be blueprint for Creating Spartan pojo
// to represent json data with 3 fields name, gender, phone
// POJO : Plain old java object , used to represent data
  // Required part of POJO
      // Encapsulated fields (private fields public getters and setters )
      // No Arg Constructor
  // OPTIONALLY
  // we will add all arg constructor for creating object in one shot
  // toString method to view the printed result

public class Spartan {

    private String name;
    private String gender;
    private long phone ;

    public Spartan(){

    }

    public Spartan(String name, String gender, long phone) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }
}
