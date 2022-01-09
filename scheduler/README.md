# Scheduler

[Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks/#scratch)

## @Scheduled

### fixedRate

method 사이의 호출 간격을 지정.

![Untitled](Scheduler%2025344a59e9fc4ffaaf48b5ccf6d2aad4/Untitled.png)

< method에 sleep(6000)을 줌 >

![Untitled](Scheduler%2025344a59e9fc4ffaaf48b5ccf6d2aad4/Untitled%201.png)

### fixedDelay

method 완료 - 호출 사이의 간격을 지정.

![Untitled](Scheduler%2025344a59e9fc4ffaaf48b5ccf6d2aad4/Untitled%202.png)

< method에 sleep(6000)을 줌 >

![Untitled](Scheduler%2025344a59e9fc4ffaaf48b5ccf6d2aad4/Untitled%203.png)

### cron=”...”

## `@EnableScheduling`

백그라운드 작업 실행기가 생성되도록 함.

```java
@SpringBootApplication
@EnableScheduling
public class SchedulingTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulingTasksApplication.class);
	}
}
```

`@SpringBootApplication` → 

fixedDelay & fixedRate가 5000인데, 하나의 method가 5000ms 이상 걸린다면??

- fixedRate는 함수가 끝나자마자 바로 다음 method 실행
    
    ![Untitled](Scheduler%2025344a59e9fc4ffaaf48b5ccf6d2aad4/Untitled%201.png)
    
- fixedDelay는 함수가 끝난 후 5000ms 를 기다린 후 실행.
    
    ![Untitled](Scheduler%2025344a59e9fc4ffaaf48b5ccf6d2aad4/Untitled%204.png)
    

⇒ 기본적으로 Scheduler가 Thread pool size 가 1인 상태에서 동작하기 때문에 싱글스레드로 동작함.

# 멀티스레드로 바꾸기

fixedRate와 

fixedDelay를 같이 돌렸을 때.

싱글스레드에 밀린 작업이 있다면 한참이 걸릴 수 있음.

```java
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    private final static int POOL_SIZE = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("paul");
        threadPoolTaskScheduler.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
```

# 비동기로 바꾸기

```java
@SpringBootApplication
@EnableScheduling
**@EnableAsync**
public class SchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerApplication.class, args);
	}

}
```

```java
@Scheduled(fixedDelay = 1000)
@Async
public void reportCurrentTime() throws InterruptedException {
	...
}
```

![Untitled](Scheduler%2025344a59e9fc4ffaaf48b5ccf6d2aad4/Untitled%205.png)

각각 다른 thread가 자기의 작업을 처리함.