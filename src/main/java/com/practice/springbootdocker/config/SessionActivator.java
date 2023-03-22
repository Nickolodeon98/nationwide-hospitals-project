package com.practice.springbootdocker.config;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class SessionActivator extends AbstractHttpSessionApplicationInitializer {
  public SessionActivator() {
    super(SessionConfig.class);
  }
}
