/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.stone.apijson.demo.multidatasource.boot;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 数据源配置，对应 application.yml 的数据库连接池 datasource 配置
 *
 * @author Lemon
 */
@Configuration
public class MultiDemoDataSourceConfig {

    @Bean(name = "ds-x")
    @ConfigurationProperties(prefix = "spring.datasource.ds-x")
    public DruidDataSource dsa() {
        return new DruidDataSource();
    }

    @Bean(name = "ds-apijson")
    @ConfigurationProperties(prefix = "spring.datasource.ds-apijson")
    public DruidDataSource dsApiJson() {
        return new DruidDataSource();
    }


    @Bean(name = "ds-y")
    @ConfigurationProperties(prefix = "spring.datasource.ds-y")
    public DruidDataSource dsb() {
        return new DruidDataSource();
    }

}