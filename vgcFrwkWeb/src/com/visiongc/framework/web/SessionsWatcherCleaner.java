package com.visiongc.framework.web;

import java.util.TimerTask;

public class SessionsWatcherCleaner extends TimerTask
{
  private SessionsWatcher sessionsWatcher;
  
  public SessionsWatcherCleaner(SessionsWatcher sessionsWatcher)
  {
    this.sessionsWatcher = sessionsWatcher;
  }
  
  public void run() {
    sessionsWatcher.clean();
  }
}
