package com.tkis.qedbot;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound = true, value = "classpath:incidencemgmtdb")
public class JpaConfig {
	@Autowired
	private FileEncryption encdec;
	
	@Value("${Url}")
    private String url;
	
	@Value("${Driver}")
    private String driverClassName;
	
	@Value("${DBUsername}")
    private String username;
	
	@Value("${DBPassword}")
    private String password;
	
	 @Bean
    public DataSource getDataSource() 
    {
		
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(encdec.Decrypt(password));
        return dataSourceBuilder.build();
    }
}