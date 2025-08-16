package com.ms.ws.constants;

public class ApiConstants {

  public enum DifficultyLevel {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    EXPERT("Expert");

    private String difficultyLevel;

    DifficultyLevel(String status) {
      this.difficultyLevel = status;
    }

    public String getValue() {
      return difficultyLevel;
    }
  }

  public enum UserRole {
    STUDENT("Student"),
    ADMIN("Admin");

    private String userRole;

    UserRole(String status) {
      this.userRole = status;
    }

    public String getValue() {
      return userRole;
    }
  }

}
