package org.example;

public class sProcess {
  public int cputime;
  public int ioblocking;
  public int cpudone;
  public int ionext;
  public int numblocked;
  public boolean isBlocked;
  public double time;
  public int executedTime;
  public int priority;

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

}
