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
import apijson.framework.APIJSONObjectParser;
import apijson.framework.APIJSONParser;
import apijson.orm.SQLConfig;
import com.alibaba.fastjson.JSONObject;


/**请求解析器
 * @author Lemon
 */
public class MultiDemoParser extends APIJSONParser {

    public MultiDemoParser() {
        super();
    }
    public MultiDemoParser(RequestMethod method) {
        super(method);
    }
    public MultiDemoParser(RequestMethod method, boolean needVerify) {
        super(method, needVerify);
    }

    //	可重写来设置最大查询数量
    //	@Override
    //	public int getMaxQueryCount() {
    //		return 50;
    //	}


    @Override
    public APIJSONObjectParser createObjectParser(JSONObject request, String parentPath, SQLConfig arrayConfig
            , boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        return new MultiDemoObjectParser(getSession(), request, parentPath, arrayConfig
                , isSubquery, isTable, isArrayMainTable).setMethod(getMethod()).setParser(this);
    }
}
