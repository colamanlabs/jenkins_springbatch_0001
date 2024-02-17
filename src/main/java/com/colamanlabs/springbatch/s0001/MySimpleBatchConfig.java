package com.colamanlabs.springbatch.s0001;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MySimpleBatchConfig
{
    /*
     * 1. Step 을 만들고
     * 
     * 2. Job 을 만든다.
     * 
     * Step 은 StepBuilder 로 생성하고, StepBuilder 는 StepBuilderFactory 로 얻는다. StepBuilderFactory 는 @Autowired 로 주입한다.
     * 
     * Job 은 JobBuilder 로 생성하고, JobBuilder 는 JobBuilderFactory 로 얻는다. JobBuilderFactory 는 @Autowired 로 주입한다.
     * 
     * 
     * Step 은 Tasklet 형태와 청크 처리 형태로 구분 할 수 있다.
     * 
     * Job 과 Step 은 @Bean 으로 선언해야 한다.
     */
    
    @Autowired
    private final StepBuilderFactory stepBuilderFactory = null;
    
    @Autowired
    private final JobBuilderFactory jobBuilderFactory = null;
    
    
    /*
     * page 106)
     * 스프링배치에 포함된 커스텀 스텝 스코프와 잡 스코프를 사용하면 늦은 바인딩 기능을 쉽게 사용할 수 있다.
     * 이 스코프 각각의 기능은 스탭의 실행 범위(스텝 스코프)나 잡의 실행범위(잡 스코프)에 들어갈 때 까지 빈 생성을 지연 시키는 것이다.
     * 이렇게 함으로서 명령행 또는 다른 소스에서 받아들인 잡 파라미터를 빈 생성 시점에 주입할 수 있다.
     * 
     * 
     * 
     * 
스탭 스코프 선언 안하면 
2024-02-16 00:10:32.985  INFO 2300 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] retryCount:[null]
2024-02-16 00:10:32.985  INFO 2300 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] foo:[null]
2024-02-16 00:10:32.985  INFO 2300 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] name:[null]
2024-02-16 00:10:32.985  INFO 2300 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] requestDate:[null]


스캡 스코프 선언하면
2024-02-16 00:13:21.337  INFO 15024 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] retryCount:[11]
2024-02-16 00:13:21.337  INFO 15024 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] foo:[bar]
2024-02-16 00:13:21.337  INFO 15024 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] name:[basicJob]
2024-02-16 00:13:21.337  INFO 15024 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] requestDate:[20240215]
     */
    
    @StepScope
    @Bean
    public Tasklet createTasklet0001()
    {
        MySimpleTasklet0001 tasklet0001 = new MySimpleTasklet0001();
        return tasklet0001;        
    }
    
    public static final String STEP_TASKLET_0001 = "STEP_TASKLET_0001";
    
    @Bean
    public Step createStep0001()
    {
        Tasklet tasklet0001 = createTasklet0001();
        StepBuilder stepBuilder = stepBuilderFactory.get(STEP_TASKLET_0001);
        Step step = stepBuilder.tasklet(tasklet0001).build();
        return step;
    }
    
    public static final String STEP_TASKLET_0002 = "STEP_TASKLET_0002";
    
    @Bean
    public Step createStep0002()
    {
        MySimpleTasklet0002 tasklet0002 = new MySimpleTasklet0002();
        StepBuilder stepBuilder = stepBuilderFactory.get(STEP_TASKLET_0002);
        Step step = stepBuilder.tasklet(tasklet0002).build();
        return step;
    }
    
    public static final String STEP_TASKLET_0003 = "STEP_TASKLET_0003";
    
    @Bean
    public Step createStep0003()
    {
        MySimpleTasklet0003 tasklet0003 = new MySimpleTasklet0003();
        StepBuilder stepBuilder = stepBuilderFactory.get(STEP_TASKLET_0003);
        Step step = stepBuilder.tasklet(tasklet0003).build();
        return step;
    }
    
//    public static final String JOB_0001 = "JOB_0001";
//    
//    @Bean
//    public Job createJob0001()
//    {
//        Step step0001 = createStep0001();
//        JobBuilder jobBuilder = jobBuilderFactory.get(JOB_0001);
//        Job job = jobBuilder.start(step0001).build();
//        return job;
//    }
    
//    public static final String JOB_0002 = "JOB_0002";
//    
//    @Bean
//    public Job createJob0002()
//    {
//        Step step0001 = createStep0001();
//        Step step0002 = createStep0002();
//        Step step0003 = createStep0003();
//        JobBuilder jobBuilder = jobBuilderFactory.get(JOB_0002);
//        Job job = jobBuilder.start(step0001).next(step0002).next(step0003).build();
//        return job;
//    }
    
    
    public static final String JOB_0001 = "JOB_0001";
    
    @Bean
    public Job createJob0001()
    {
        JobParametersValidator jobParamValidator = createValidator0001();        
        Step step0001 = createStep0001();
        JobBuilder jobBuilder = jobBuilderFactory.get(JOB_0001);
        Job job = jobBuilder.start(step0001).validator(jobParamValidator).build();
        return job;
    }
    
    /*
     * JobBuilder 에서는 하나의 JobParameterValidator 지정만 가능하지만,
     * CompositeJobParameterValidator 를 통해 복수개의 유효성 검증기 사용이 가능하다.
     * 
     * https://javadoc.io/static/org.springframework.batch/spring-batch-core/2.1.9.RELEASE/org/springframework/batch/core/job/CompositeJobParametersValidator.html
     */
    
    @Bean
    public CompositeJobParametersValidator createValidator0001()
    {
        /*
         * JobParametersValidator 리스트를 만들고, CompositeJobParametersValidator 를 생성해서, setValidators 에 밸리데이터 리스트를 넣는다.
         */
        List<JobParametersValidator> listValidator = new ArrayList<JobParametersValidator>();
        MyParameterValidator validator0 = new MyParameterValidator();
        listValidator.add(validator0);
        
        CompositeJobParametersValidator compositeValidator = new CompositeJobParametersValidator();
        compositeValidator.setValidators(listValidator);
        return compositeValidator;
    }

    
}
