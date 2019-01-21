# HornetQ UID Interceptor 

HornetQ interceptor which adds a generated UID as the value of the JMS Property `_AMQ_DUPL_ID`. Used by duplication-
detection within ActiveMQ Artemis. Can provide "exactly once" semantics without XA. Just "at least once" and de-dups
are used.

## Environment
* `$PROJECT_HOME`: root directory of the cloned repository
* `$EAP_HOME`: root directory of Wildfly or JBoss EAP installation

## Build
```
cd $project_home
mvn clean package
```

## Installation
1. `cp $PROJECT_HOME/target/hornetq-interceptor.jar $JBOSS_EAP_HOME/modules/system/layers/base/org/jboss/as/messaging/main`
1. Registration of interceptor in $JBOSS_EAP_HOME/modules/system/layers/base/org/jboss/as/messaging/main/module.xml: `<resource-root path="hornetq-interceptor.jar"/>`
1. Enabling interceptor: `/subsystem=messaging/hornetq-server=default:write-attribute(name=remoting-incoming-interceptors,value=["ch.puzzle.messaging.AddUniqueIdInterceptor"])`


```
<hornetq-server>
    ...
    <remoting-incoming-interceptors>
        <class-name>ch.puzzle.messaging.AddUniqueIdInterceptor</class-name>
    </remoting-incoming-interceptors>
    ...
</hornetq-server>
```


## Logger configuration
```
<logger category="ch.puzzle.messaging.AddUniqueIdInterceptor">
    <level name="FINER"/>
</logger>
```

## References
* https://access.redhat.com/solutions/908093
