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

package com.stone.apijson.demo.multidatasource.apijson;

import apijson.RequestMethod;
import apijson.framework.APIJSONSQLConfig;
import com.stone.apijson.demo.multidatasource.model.VipUser;


/**
 * SQL 配置
 * TiDB 用法和 MySQL 一致
 *
 * @author Lemon
 */
public class MultiDemoSQLConfig extends APIJSONSQLConfig {

    public MultiDemoSQLConfig() {
        super();
    }

    public MultiDemoSQLConfig(RequestMethod method, String table) {
        super(method, table);
    }

    static {
        DEFAULT_DATABASE = DATABASE_MYSQL;  //TODO 默认数据库类型，改成你自己的。TiDB, MariaDB, OceanBase 这类兼容 MySQL 的可当做 MySQL 使用
        DEFAULT_SCHEMA = "sys_apijson";  //TODO 默认模式名，改成你自己的，默认情况是 MySQL: sys, PostgreSQL: public, SQL Server: dbo, Oracle:

        // 表别名设置
        TABLE_KEY_MAP.put(VipUser.class.getSimpleName(), "vip_user");

    }

    //取消注释后，默认的 APIJSON 配置表会由业务表所在 数据库类型 database 和 数据库模式 schema 改为自定义的
    @Override
    public String getConfigDatabase() {
        return DATABASE_MYSQL;
    }

    @Override
    public String getDatabase() {
        return DATABASE_MYSQL;
    }

    @Override
    public String getDatasource() {
        //         datasource是由前端传入的，如果没有传入，则认为是读取apijson系统库
        if (super.getDatasource() == null || super.getDatasource().length() == 0) {
            return "ds-apijson";
        }
        return super.getDatasource();
    }

    @Override
    public String getSQLSchema() {
        // 业务库
        if ("dsx".equals(getDatasource())) {
            // 业务库为bizx
            return "m_bizx_db";
        } else if ("dsy".equals(getDatasource())) {
            // 业务库为bizy
            return "m_bizy_db";
        }else if ("ds-apijson".equals(getDatasource())) {
            // 业务库为bizy
            return "sys_m_apijson_db";
        }else {
            throw  new IllegalArgumentException("unknown ds: "+getDatasource());
        }
    }

    @Override
    public String getConfigSchema() {
        //系统配置库为sys_m_apijson
        return "sys_m_apijson_db";
    }

    // 如果 DemoSQLExecutor.getConnection 能拿到连接池的有效 Connection，则这里不需要配置 dbVersion, dbUri, dbAccount, dbPassword

    @Override
    public String getDBVersion() {
        if (isMySQL()) {
            return "5.7.22"; //"8.0.11"; //TODO 改成你自己的 MySQL 或 PostgreSQL 数据库版本号 //MYSQL 8 和 7 使用的 JDBC 配置不一样
        }
        if (isPostgreSQL()) {
            return "9.6.15"; //TODO 改成你自己的
        }
        if (isSQLServer()) {
            return "2016"; //TODO 改成你自己的
        }
        if (isOracle()) {
            return "18c"; //TODO 改成你自己的
        }
        if (isDb2()) {
            return "11.5"; //TODO 改成你自己的
        }
        return null;
    }


    //如果确定只用一种数据库，可以重写方法，这种数据库直接 return true，其它数据库直接 return false，来减少判断，提高性能
    @Override
    public boolean isMySQL() {
        return true;
    }

    @Override
    public boolean isPostgreSQL() {
        return false;
    }

    @Override
    public boolean isSQLServer() {
        return false;
    }

    @Override
    public boolean isOracle() {
        return false;
    }

    @Override
    public boolean isDb2() {
        return false;
    }


}
