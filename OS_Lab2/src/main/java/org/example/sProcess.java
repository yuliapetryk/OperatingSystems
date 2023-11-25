package org.example;

public class sProcess {
  private int cputime;
  private int ioblocking;
  private int cpudone;
  private int ionext;
  private int numblocked;
  private boolean isBlocked;
  private double time;
  private int executedTime;
  private int priority;

  public sProcess (int cputime, int ioblocking, int cpudone, int ionext, int numblocked) {
    this.cputime = cputime;
    this.ioblocking = ioblocking;
    this.cpudone = cpudone;
    this.ionext = ionext;
    this.numblocked = numblocked;
  }

  public int getCputime() {
    return this.cputime;
  }

  public int getIoblocking() {
    return this.ioblocking;
  }

  public int getPriority() {
    return this.priority;
  }

  public void setPriority(int priority) { this.priority = priority; }

  public int getCpudone() {
    return this.cpudone;
  }

  public void setCpudone(int cpudone) {
    this.cpudone = cpudone;
  }

  public int getIonext() {
    return this.ionext;
  }

  public void setIonext(int ionext) {
    this.ionext = ionext;
  }

  public int getNumblocked() {
    return this.numblocked;
  }

  public  void setNumblocked(int numblocked) {
    this.numblocked = numblocked;
  }

  public int getExecutedTime() { return this.executedTime; }

  public void setExecutedTime(int executedTime) { this.executedTime = executedTime; }

  public double getTime() { return this.time; }

  public boolean getIsBlocked() { return this.isBlocked;}

  public void setIsBlocked(boolean isBlocked) { this.isBlocked = isBlocked;}
}
