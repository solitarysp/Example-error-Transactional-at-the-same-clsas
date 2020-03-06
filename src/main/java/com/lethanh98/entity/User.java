package com.lethanh98.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static javax.persistence.GenerationType.IDENTITY;
@Data
@Table(name = "user")
@Entity
public class User {

  public User() {
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private int id;

  private String firstName;

  private String lastName;

}
