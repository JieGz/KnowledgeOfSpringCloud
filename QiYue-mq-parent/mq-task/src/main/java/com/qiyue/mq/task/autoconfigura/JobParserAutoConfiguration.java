package com.qiyue.mq.task.autoconfigura;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.qiyue.mq.task.parser.ElasticJobConfParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JobZookeeperProperties.class)
@ConditionalOnProperty(prefix = "elastic.job.zk", name = {"serverLists", "namespace"}, matchIfMissing = false)
public class JobParserAutoConfiguration {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter(JobZookeeperProperties jobZookeeperProperties) {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(jobZookeeperProperties.getServerLists(), jobZookeeperProperties.getNamespace());
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(jobZookeeperProperties.getConnectionTimeoutMilliseconds());
        zookeeperConfiguration.setSessionTimeoutMilliseconds(jobZookeeperProperties.getSessionTimeoutMilliseconds());
        zookeeperConfiguration.setMaxRetries(jobZookeeperProperties.getMaxRetries());
        zookeeperConfiguration.setBaseSleepTimeMilliseconds(jobZookeeperProperties.getBaseSleepTimeMilliseconds());
        zookeeperConfiguration.setMaxSleepTimeMilliseconds(jobZookeeperProperties.getMaxSleepTimeMilliseconds());
        zookeeperConfiguration.setDigest(jobZookeeperProperties.getDigest());
        System.out.println("8888888899999999997777777777777");
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }

    @Bean
    public ElasticJobConfParser elasticJobConfParser(JobZookeeperProperties jobZookeeperProperties, ZookeeperRegistryCenter zookeeperRegistryCenter) {
        return new ElasticJobConfParser(jobZookeeperProperties, zookeeperRegistryCenter);
    }
}
