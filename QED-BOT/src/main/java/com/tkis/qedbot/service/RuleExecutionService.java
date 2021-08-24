package com.tkis.qedbot.service;

import org.springframework.stereotype.Service;

@Service
public interface RuleExecutionService 
{
	
	public int executeRule(String ruleDesc) throws Exception;

}
