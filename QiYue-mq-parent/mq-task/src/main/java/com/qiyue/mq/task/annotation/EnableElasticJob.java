package com.qiyue.mq.task.annotation;

import com.qiyue.mq.task.autoconfigura.JobParserAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(JobParserAutoConfiguration.class)
public @interface EnableElasticJob {
}
