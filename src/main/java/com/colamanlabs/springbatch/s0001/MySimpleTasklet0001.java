package com.colamanlabs.springbatch.s0001;

import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySimpleTasklet0001 implements Tasklet
{
    
    
    @Value("#{jobParameters['retryCount']}")
    private String retryCount;
    
    @Value("#{jobParameters['foo']}")
    private String foo;
    
    @Value("#{jobParameters['name']}")
    private String name;
    
    @Value("#{jobParameters['requestDate']}")
    private String requestDate;
    
    
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception
    {
        log.info(String.format("[MySimpleTasklet0001/execute] BEGIN"));
        log.info(String.format("[MySimpleTasklet0001/execute] contribution:[%s]", contribution));
        log.info(String.format("[MySimpleTasklet0001/execute] chunkContext:[%s]", chunkContext));
        // TODO Auto-generated method stub

        
        /*
C:\WORKS\WORKS_STS4_GITHUB\WORKS_STS4_GITHUB_SPRINGBOOT_STUDY\springboot_study_0007\target>java -jar ./springboot_study_0007-0.0.1-SNAPSHOT.jar foo=bar name=basicJob requestDate=20240215 retryCount=2
...
2024-02-15 23:49:45.137  INFO 13172 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] BEGIN
2024-02-15 23:49:45.137  INFO 13172 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] contribution:[[StepContribution: read=0, written=0, filtered=0, readSkips=0, writeSkips=0, processSkips=0, exitStatus=EXECUTING]]
2024-02-15 23:49:45.138  INFO 13172 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] chunkContext:[ChunkContext: attributes=[], complete=false, stepContext=SynchronizedAttributeAccessor: [], stepExecutionContext={batch.taskletType=com.colamanlabs.springbootstudy.s0007.MySimpleTasklet0001, batch.stepType=org.springframework.batch.core.step.tasklet.TaskletStep}, jobExecutionContext={}, jobParameters={retryCount=2, foo=bar, name=basicJob, requestDate=20240215}]
2024-02-15 23:49:45.138  INFO 13172 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] mapJobParameters:[{retryCount=2, foo=bar, name=basicJob, requestDate=20240215}]
2024-02-15 23:49:45.138  INFO 13172 --- [           main] c.c.s.s0007.MySimpleTasklet0001          : [MySimpleTasklet0001/execute] END
...
C:\WORKS\WORKS_STS4_GITHUB\WORKS_STS4_GITHUB_SPRINGBOOT_STUDY\springboot_study_0007\target>


방안 1)
JobParameter 에 접근하는 방법은 ChunkContext 를 통해 StepContext 를 얻고, StepContext 를 통해 JobParameter 에 접근한다.


방안 2)
스프링 구성을 통해 주입하는 것이다. JobParameters 는 변경할 수 없으므로, 부트스트랩시에 바인딩한다.
         */
        Map<String, Object> mapJobParameters = chunkContext.getStepContext().getJobParameters();        
        log.info(String.format("[MySimpleTasklet0001/execute] mapJobParameters:[%s]", mapJobParameters));        
        
        
        /*
         * 방안 2) 주입을 통한 잡 파라미터 접근 테스트
         */
        log.info(String.format("[MySimpleTasklet0001/execute] retryCount:[%s]", retryCount));
        log.info(String.format("[MySimpleTasklet0001/execute] foo:[%s]", foo));
        log.info(String.format("[MySimpleTasklet0001/execute] name:[%s]", name));
        log.info(String.format("[MySimpleTasklet0001/execute] requestDate:[%s]", requestDate));
        
        log.info(String.format("[MySimpleTasklet0001/execute] END"));
        
        return RepeatStatus.FINISHED;
    }
    
    
    
    
}
