package com.ideasync.ideasyncbackend.user.dto;

import java.sql.Timestamp;

public class PassCodeResponse {
  private int passcode;
  private Timestamp expiryTime;

  public PassCodeResponse(int passcode, Timestamp expiryTime) {
    this.passcode = passcode;
    this.expiryTime = expiryTime;
  }

  public int getPasscode() {
    return passcode;
  }

  public void setPasscode(int passcode) {
    this.passcode = passcode;
  }

  public Timestamp getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(Timestamp expiryTime) {
    this.expiryTime = expiryTime;
  }
}
