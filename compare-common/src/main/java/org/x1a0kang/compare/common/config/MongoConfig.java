//package org.x1a0kang.compare.common.config;
//
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.NoSuchBeanDefinitionException;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
//import org.springframework.data.mongodb.core.convert.*;
//import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
//
///**
// * @author dongruijun
// * @version V1.0
// * @date 2021-06-09 16:57:53
// * @project app-robo-advisor
// * @description
// **/
//@Configuration
//public class MongoConfig {
//
//    @Value("${spring.data.mongodb.uri}")
//    private String defaultUrl;
//
//    @Bean
//    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
//        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
//        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
//        try {
//            mappingConverter.setCustomConversions(beanFactory.getBean(MongoCustomConversions.class));
//        } catch (NoSuchBeanDefinitionException ignore) {
//        }
//
//        // Don't save _class to mongo
//        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
//
//        return mappingConverter;
//    }
//
//    @Primary
//    @Bean(name = "mongoTemplate")
//    public MongoTemplate getMongoTemplate(@Qualifier("mappingMongoConverter") MappingMongoConverter mappingMongoConverter) {
//        MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(defaultUrl);
//        return new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
//    }
//}