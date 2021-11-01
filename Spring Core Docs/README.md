# Spring Core Docs 톺아보기

# Keyword

### IoC

- 객체 생성/생명주기 관리 등 제어권이 역전된 것.
- 즉, 개발자가 아니라 프레임워크가 대신해줌.
- 생성, 초기화, 서비스, 소멸

→ DI (Dependency Injection)

1. Setter Injection
2. **Constructor Injection**
3. Method Injection

### AOP (Aspect-Oriented Programming): 관점 지향 프로그래밍

- 핵심적인 / 부가적인 관점으로 나누어 모듈화

### Bean

application의 backbone을 형성하고 관리되는 객체.

IoC Container에 의해 instantiated, assembled, managed 되는 객체.

< 비교 >

- XML에서
    
    <bean>  ... [configuration] ... <bean/>
    
- Java
    
    @Configuration안에서 @Bean
    

```java
<bean id="..." class="...">
	... ...
<bean/>
// id: 각각의 bean 정의를 구별할 수 있는 식별자.
// class: bean의 타입/유형
```

### packages

- org.springframework.beans
- org.springframework.context

### Interface

- **BeanFactory**
- **ApplicationContext**
    
    BeanFactory의 sub-interface.
    
    enterprise에 더 적합함.
    
    1. Easier integration with Spring's AOP features.
    2. Message resource handling (국제화)
    3. Event publication
    4. Application-layer specific contexts → web applications ⇒ **WebApplicationContext**

# 1. The IoC Container

Spring Framework implementation of the Inversion of Control (IoC) Principle.

**injects those dependencies when it creates the bean.
→ Inversion**

org.springframework.context.ApplicationContext Interface:

- Bean의
    - instantiating
    - configuring
    - assembling

## **Configuration metadata**

**IoC Container에게 application이 어떻게 instantiate, configure, assemble할 지 알려주는 과정.**

![Untitled](Spring%20Core%20Docs%20%E1%84%90%E1%85%A9%E1%87%81%E1%84%8B%E1%85%A1%E1%84%87%E1%85%A9%E1%84%80%E1%85%B5%20bb4ecda2078e47f09bf4132caf4182c7/Untitled.png)

- **XML**
    
    **< Stand-alone application >**
    
    - ClassPathXmlApplicationContext
    - FileSystemXmlApplicationContext
    
    **< Web application >**
    
    - web.xml
    (easy)
- **Java annotations**
    
    spring 2.5 <
    
- **Java Code**
    
    spring 3.0 <
    
    @Configuration, @Bean, @Import, @DependsOn, ...
    
    ⇒ 
    
    [Core Technologies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java)
    

## Instantiating a Container

**❗가능은 하지만, 상대경로보다 절대경로 사용.
→ 상대경로는 Runtime에 "nearlest" classpath를 참조하기 때문에 incorrect할 수 있음.**

### 외부 resource를 가저오는 방법.

```java
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
```

services.xml을 보면 daos.xml을 ref하여 연결하고 있다.

services.xml ← daos.xml

```java
<bean id="petStore" class="org.springframework.samples.jpetstore.services.PetStoreServiceImpl">
    <property name="accountDao" ref="accountDao"/>
    <property name="itemDao" ref="itemDao"/>
    <!-- additional collaborators and configuration for this bean go here -->
</bean>
```

```java
<bean id="accountDao"
    class="org.springframework.samples.jpetstore.dao.jpa.JpaAccountDao">
    <!-- additional collaborators and configuration for this bean go here -->
</bean>

<bean id="itemDao" class="org.springframework.samples.jpetstore.dao.jpa.JpaItemDao">
    <!-- additional collaborators and configuration for this bean go here -->
</bean>
```

### **다양한 resource를 가져오는 방법.**

```java
<beans>
    <import resource="services.xml"/>
    <import resource="resources/messageSource.xml"/>
    <import resource="/resources/themeSource.xml"/>

    <bean id="bean1" class="..."/>
    <bean id="bean2" class="..."/>
</beans>
```

## Using the Container

Bean 인스턴스 검색: `T getBean(String name, Class<T> requiredType)`

```java
// create and configure beans
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");

// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);

// use configured instance
List<String> userList = service.getUsernameList();
```