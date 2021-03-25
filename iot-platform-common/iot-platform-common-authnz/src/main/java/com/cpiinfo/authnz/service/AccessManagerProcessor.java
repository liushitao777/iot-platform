package com.cpiinfo.authnz.service;

import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;

public class AccessManagerProcessor implements ObjectPostProcessor<AbstractAccessDecisionManager> {

	@Override
	public <O extends AbstractAccessDecisionManager> O postProcess(O object) {
		object.getDecisionVoters().add(0, new AdminAccessVoter());
		object.getDecisionVoters().add(1, new RoleVoter());
		return object;
	}

}
