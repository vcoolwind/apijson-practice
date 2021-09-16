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

import apijson.Log;
import apijson.framework.APIJSONSQLExecutor;
import apijson.orm.SQLConfig;
import com.alibaba.druid.pool.DruidDataSource;
import com.stone.apijson.demo.multidatasource.boot.MultiDemoApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;


/**
 * SQL 执行
 *
 * @author Lemon
 */
public class MultiDemoSQLExecutor extends APIJSONSQLExecutor {
    public static final String TAG = "DemoSQLExecutor";

    // 适配连接池，如果这里能拿到连接池的有效 Connection，则 SQLConfig 不需要配置 dbVersion, dbUri, dbAccount, dbPassword
    @Override
    public Connection getConnection(SQLConfig config) throws Exception {
        String datasource = config.getDatasource();
        Log.d(TAG, "getConnection  config.getDatasource() = " + datasource);

        String key = datasource + "-" + config.getDatabase();
        Connection c = connectionMap.get(key);
        if (datasource != null && (c == null || c.isClosed())) {
            try {
                Map<String, DruidDataSource> dsMap = MultiDemoApplication.getApplicationContext().getBeansOfType(DruidDataSource.class);
                DataSource ds=null;
                // 另一种方式是 DemoDataSourceConfig 初始化获取到 DataSource 后给静态变量 DATA_SOURCE_DRUID 赋值： ds = DemoDataSourceConfig.DATA_SOURCE_DRUID.getConnection();
                switch (datasource) {
                    case "dsx":
                        ds = dsMap.get("ds-x");
                        break;
                    case "dsy":
                        ds = dsMap.get("ds-y");
                        break;
                    default:
                        ds=dsMap.get("ds-apijson");
                }
                connectionMap.put(key, ds == null ? null : ds.getConnection());
            } catch (Exception e) {
                Log.e(TAG, "getConnection   try { "
                        + "DataSource ds = DemoApplication.getApplicationContext().getBean(DataSource.class); .."
                        + "} catch (Exception e) = " + e.getMessage());
            }
        }

        // 必须最后执行 super 方法，因为里面还有事务相关处理。
        // 如果这里是 return c，则会导致 增删改 多个对象时只有第一个会 commit，即只有第一个对象成功插入数据库表
        return super.getConnection(config);
    }


}
