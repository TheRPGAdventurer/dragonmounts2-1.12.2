package com.TheRPGAdventurer.ROTD.util.debugging.testclasses;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by TGG on 2/06/2019.
 */
public class LoggerLimit  {

  public LoggerLimit(Logger i_baseLogger) {
    baseLogger = i_baseLogger;
  }

  public void warn_once( String message) {
    if (checkFirstTimeLogged(message)) {
      baseLogger.warn(message);
    }
  }

  public void error_once( String message) {
    if (checkFirstTimeLogged(message)) {
      baseLogger.error(message);
    }
  }

  private boolean checkFirstTimeLogged(String message)
  {
    Integer msghash = message.hashCode();  // chances of a collision are very small
    if (loggedMessages.contains(msghash)) return false;
    loggedMessages.add(msghash);
    return true;
  }

  private Logger baseLogger;
  Set<Integer> loggedMessages = new HashSet<>();
}
