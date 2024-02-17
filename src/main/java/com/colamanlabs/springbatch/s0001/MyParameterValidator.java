package com.colamanlabs.springbatch.s0001;

import java.util.Iterator;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

import lombok.extern.slf4j.Slf4j;



/*
 * https://javadoc.io/static/org.springframework.batch/spring-batch-core/3.0.3.RELEASE/org/springframework/batch/core/JobParametersValidator.html
 */

@Slf4j
public class MyParameterValidator implements JobParametersValidator
{

    
    
    /*
     * https://docs.spring.io/spring-batch/docs/current/api/org/springframework/batch/core/JobParameters.html
     */
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException
    {
        // TODO Auto-generated method stub
        
        log.info(String.format("[MyParameterValidator/validate] BEGIN"));
        
        /*
         * https://javadoc.io/static/org.springframework.batch/spring-batch-core/4.3.5/org/springframework/batch/core/JobParameter.html
         */
        Map<String, JobParameter> mapParameters = parameters.getParameters();
        log.info(String.format("[MyParameterValidator/validate] mapParameters.size():[%d]", mapParameters.size()));
        
        Iterator<String> iterKey = mapParameters.keySet().iterator();
        
        while (iterKey.hasNext() == true)
        {
            String strKey = iterKey.next();
            JobParameter jobParameter = mapParameters.get(strKey);
            log.info(String.format("[MyParameterValidator/validate] strKey:[%s]\tjobParameter:[%s]", strKey, jobParameter));
        }
        
        checkJobParameterRequestDate(parameters);
        
        log.info(String.format("[MyParameterValidator/validate] END"));
        
    }
    
   
    public void checkJobParameterRequestDate(JobParameters parameters) throws JobParametersInvalidException
    {
        log.info(String.format("[MyParameterValidator/checkJobParameterRequestDate] BEGIN"));
        
        /*
         * requestDate 라는 파라미터가 존재하고, 해당 파라미터의 length 가 8 이 아니면 예외를 던지는 것으로 가정한다.
         */
        
        String requestDate = parameters.getString("requestDate");
        log.info(String.format("[MyParameterValidator/checkJobParameterRequestDate] requestDate:[%s]", requestDate));
        if (requestDate == null)
        {
            JobParametersInvalidException exception0 = new JobParametersInvalidException("invalid jobParameter 'requestDate' is null.");
            throw exception0;
        }
        else
        {
            requestDate = requestDate.trim();
            if (requestDate.length() != 8)
            {
                JobParametersInvalidException exception1 = new JobParametersInvalidException(String.format("invalid jobParameter 'requestDate' length:[%d]", requestDate.length()));
                throw exception1;
            }
        }
        log.info(String.format("[MyParameterValidator/checkJobParameterRequestDate] jobParameter requestDate:[%s] is valid.", requestDate));
        log.info(String.format("[MyParameterValidator/checkJobParameterRequestDate] END"));
    }
    
}
