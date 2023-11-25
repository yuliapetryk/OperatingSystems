package org.example;

// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results Run(int runtime, Vector processVector, Results result) {
    int i = 0;
    int comptime = 0;
    int currentProcess = 0;
    int previousProcess = 0;
    int size = processVector.size();
    int completed = 0;

    String resultsFile = "Summary-Processes";

    Collections.sort(setPriority(processVector), Comparator.comparingInt(sProcess::getPriority));

    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      sProcess process = (sProcess) processVector.elementAt(currentProcess);

      PriorityQueue<sProcess> processQueue = new PriorityQueue<>(Comparator.comparingDouble((sProcess proc) -> proc.getTime()));
      out.println("Process: " + currentProcess + " registered... (" + process.getCputime() + " " + process.getIoblocking() + " " + process.getCpudone() + " " + process.getCpudone() + ")");

      while (comptime < runtime) {
        for (i = 0; i < size; i++) {
          sProcess actualProcess = (sProcess) processVector.elementAt(i);
          if (actualProcess.getIsBlocked() && comptime - actualProcess.getExecutedTime() == actualProcess.getIoblocking()) {
            processQueue.add(actualProcess);
            actualProcess.setIsBlocked( false);
          }
        }

        if (process.getCpudone() == process.getCputime()) {
          completed++;
          out.println("Process: " + currentProcess + " completed... (" + process.getCputime() + " " + process.getIoblocking() + " " + process.getCpudone() + " " + process.getCpudone() + ")");
          if (completed == size) {
            result.compuTime = comptime;
            out.close();
            return result;
          }
          if (processQueue.size() > 0)
            process = processQueue.poll();
            out.println("Process: " + currentProcess + " registered... (" + process.toString() + ")");
        }
        if (process.getIoblocking() == process.getIonext()) {
          out.println("Process: " + currentProcess + " I/O blocked... (" + process.toString() + ")");
          process.setNumblocked(process.getNumblocked() + 1);
          process.setIonext(0);
          process.setExecutedTime(comptime);
          previousProcess = currentProcess;
          for (i = size - 1; i >= 0; i--) {
            process = (sProcess) processVector.elementAt(i);
            if (process.getCpudone() < process.getCputime() && previousProcess != i) {
              currentProcess = i;
            }
          }
          process = (sProcess) processVector.elementAt(currentProcess);
          out.println("Process: " + currentProcess + " completed... (" + process.getCputime() + " " + process.getIoblocking() + " " + process.getCpudone() + " " + process.getCpudone() + ")");
        }
        process.setCpudone(process.getCpudone()+1);
        if (process.getIoblocking() > 0 && !process.getIsBlocked()) {
          process.setIonext(process.getIonext() + 1);
        }
        comptime++;
      }
      out.close();
    } catch (IOException e) {}
    result.compuTime = comptime;
    return result;
  }

  private static Vector<sProcess> setPriority(Vector<sProcess> processes) {
    int priority= 0;
    int prevTime = 0;
    processes.sort(Comparator.comparing(sProcess::getCputime).thenComparing(sProcess::getIoblocking));

    for (sProcess process : processes) {
      if(prevTime < process.getCputime()) {
        priority += 1;
      }
      prevTime = process.getCputime();
      process.setPriority(priority);
    }
    return processes;
  }
}
