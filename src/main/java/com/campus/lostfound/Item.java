package com.campus.lostfound;
import java.sql.Timestamp;
public class Item {
 private long id;
 private String title, description, category, itemStatus, locationFound, contact, imageName, createdByName;
 private Timestamp createdAt;
 public long getId(){return id;} public void setId(long v){id=v;}
 public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public String getItemStatus(){return itemStatus;} public void setItemStatus(String v){itemStatus=v;}
 public String getLocationFound(){return locationFound;} public void setLocationFound(String v){locationFound=v;}
 public String getContact(){return contact;} public void setContact(String v){contact=v;}
 public String getImageName(){return imageName;} public void setImageName(String v){imageName=v;}
 public String getCreatedByName(){return createdByName;} public void setCreatedByName(String v){createdByName=v;}
 public Timestamp getCreatedAt(){return createdAt;} public void setCreatedAt(Timestamp v){createdAt=v;}
}