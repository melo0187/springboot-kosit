# Beispiel für KoSIT Validator in Spring Boot
Die Dokumentation des [KoSIT Validators](https://github.com/itplr-kosit/validator) ist nicht auf dem neuesten Stand.
Daher gibt es keine einfache Anleitung der man folgen kann, um den Validator als lib in einer Spring Boot 3 Anwendung
mit Java 21 zu verwenden.

Dies soll ein Versuch den Validator unter oben genannten Rahmenbedingungen zu nutzen und die Dokumentationslücken auf 
dem Weg aufzuspüren.

## Dependencies
Die Initialisierung der KoSIT Validator Konfiguration kompilierte erst gar nicht ohne `net.sf.saxon:Saxon-HE:11.4`.

Neben der dokumentierten Abhängigkeit zu `org.glassfish.jaxb:jaxb-runtime:2.3.7` für Java 11+ musste ich zwingend noch
zwei weitere runtime dependencies hinzunehmen:
- `jakarta.xml.bind:jakarta.xml.bind-api:2.3.3`
- `org.apache.commons:commons-lang3:3.12.0`

Erst dann lässt sich die Applikation ohne Laufzeitfehler starten und der Validator dabei initialisieren.

## Ausführung als JAR
Das über `gradle bootJar` erzeugte JAR bringt dann einen neuen Laufzeitfehler zum Applikationsstart hervor.
```
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'kositValidationCheck' defined in class path resource [info/melo/springkosit/ValidatorConfigurati
on.class]: Failed to instantiate [de.kosit.validationtool.api.Check]: Factory method 'kositValidationCheck' threw exception with message: Can not load schema from sources jar:nested:/h
ome/melo/Code/work/springkosit/build/libs/springkosit-0.0.1-SNAPSHOT.jar/!BOOT-INF/classes/!/validator-configuration-xrechnung_3.0.2_2024-06-20/resources/ubl/2.1/xsd/maindoc/UBL-Invoic
e-2.1.xsd                                                                                                                                                                               
[...]
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [de.kosit.validationtool.api.Check]: Factory method 'kositValidationCheck' threw exception with m
essage: Can not load schema from sources jar:nested:/home/melo/Code/work/springkosit/build/libs/springkosit-0.0.1-SNAPSHOT.jar/!BOOT-INF/classes/!/validator-configuration-xrechnung_3.0
.2_2024-06-20/resources/ubl/2.1/xsd/maindoc/UBL-Invoice-2.1.xsd                                                                                                                         
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:178) ~[spring-beans-6.1.14.jar!/:6.1.14]                  
        at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:644) ~[spring-beans-6.1.14.jar!/:6.1.14]                                  
        ... 23 common frames omitted                                                                                                                                                    
Caused by: java.lang.IllegalArgumentException: Can not load schema from sources jar:nested:/home/melo/Code/work/springkosit/build/libs/springkosit-0.0.1-SNAPSHOT.jar/!BOOT-INF/classes/
!/validator-configuration-xrechnung_3.0.2_2024-06-20/resources/ubl/2.1/xsd/maindoc/UBL-Invoice-2.1.xsd                                                                                  
        at de.kosit.validationtool.impl.ContentRepository.createSchema(ContentRepository.java:114) ~[validationtool-1.5.0.jar!/:na]                                                     
        at de.kosit.validationtool.impl.ContentRepository.createSchema(ContentRepository.java:168) ~[validationtool-1.5.0.jar!/:na]                                                     
        at de.kosit.validationtool.impl.ContentRepository.createSchema(ContentRepository.java:181) ~[validationtool-1.5.0.jar!/:na]                                                     
        at de.kosit.validationtool.config.ConfigurationLoader.initialize(ConfigurationLoader.java:134) ~[validationtool-1.5.0.jar!/:na]                                                 
        at de.kosit.validationtool.config.ConfigurationLoader.lambda$initializeScenarios$0(ConfigurationLoader.java:128) ~[validationtool-1.5.0.jar!/:na]                               
        at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197) ~[na:na]                                                                                 
        at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1708) ~[na:na]                                                                            
        at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509) ~[na:na]                                                                                     
        at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499) ~[na:na]                                                                              
        at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921) ~[na:na]                                                                                
        at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234) ~[na:na]                                                                                     
        at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682) ~[na:na]                                                                                    
        at de.kosit.validationtool.config.ConfigurationLoader.initializeScenarios(ConfigurationLoader.java:128) ~[validationtool-1.5.0.jar!/:na]                                        
        at de.kosit.validationtool.config.ConfigurationLoader.build(ConfigurationLoader.java:164) ~[validationtool-1.5.0.jar!/:na]                                                      
        at info.melo.springkosit.ValidatorConfiguration.kositValidationCheck(SpringkositApplication.kt:40) ~[!/:0.0.1-SNAPSHOT]                                                         
        at info.melo.springkosit.ValidatorConfiguration$$SpringCGLIB$$0.CGLIB$kositValidationCheck$0(<generated>) ~[!/:0.0.1-SNAPSHOT]                                                  
        at info.melo.springkosit.ValidatorConfiguration$$SpringCGLIB$$FastClass$$1.invoke(<generated>) ~[!/:0.0.1-SNAPSHOT]                                                             
        at org.springframework.cglib.proxy.MethodProxy.invokeSuper(MethodProxy.java:258) ~[spring-core-6.1.14.jar!/:6.1.14]                                                        
        at org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:348) ~[spring-context-6.1.14.jar!/:6.1.14] 
        at info.melo.springkosit.ValidatorConfiguration$$SpringCGLIB$$0.kositValidationCheck(<generated>) ~[!/:0.0.1-SNAPSHOT]                                                          
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]                                                               
        at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]                                                                                                          
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:146) ~[spring-beans-6.1.14.jar!/:6.1.14]                  
        ... 24 common frames omitted                                                                                                                                                    
Caused by: org.xml.sax.SAXParseException: schema_reference: Failed to read schema document 'UBL-CommonAggregateComponents-2.1.xsd', because 'nested' access is not allowed due to restri
ction set by the accessExternalSchema property.
```