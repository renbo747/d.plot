package com.dplot.common;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ShedlockConfig {
//    @Bean
//    public LockProvider lockProvider(@Qualifier("dataSource-mysql") DataSource dataSource){
//        System.out.println(dataSource);
//        return new JdbcTemplateLockProvider(dataSource, "shedlock");
//    }
}
