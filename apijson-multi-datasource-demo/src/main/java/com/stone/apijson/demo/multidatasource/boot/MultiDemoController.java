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

import apijson.RequestMethod;
import apijson.framework.APIJSONController;
import apijson.orm.Parser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


/**提供入口，转交给 APIJSON 的 Parser 来处理
 * @author Lemon
 */
@RestController
@RequestMapping("")
public class MultiDemoController extends APIJSONController {
	
	@Override
	public Parser<Long> newParser(HttpSession session, RequestMethod method) {
		return super.newParser(session, method).setNeedVerify(true).setNeedVerifyLogin(false); //TODO 这里关闭校验，方便新手快速测试，实际线上项目建议开启
	}



	/**获取
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#GET}
	 */
	@PostMapping(value = "get")
	@Override
	public String get(@RequestBody String request, HttpSession session) {
		return super.get(request, session);
	}

	/**计数
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#HEAD}
	 */
	@PostMapping("head")
	@Override
	public String head(@RequestBody String request, HttpSession session) {
		return super.head(request, session);
	}

	/**限制性GET，request和response都非明文，浏览器看不到，用于对安全性要求高的GET请求
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#GETS}
	 */
	@PostMapping("gets")
	@Override
	public String gets(@RequestBody String request, HttpSession session) {
		return super.gets(request, session);
	}

	/**限制性HEAD，request和response都非明文，浏览器看不到，用于对安全性要求高的HEAD请求
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#HEADS}
	 */
	@PostMapping("heads")
	@Override
	public String heads(@RequestBody String request, HttpSession session) {
		return super.heads(request, session);
	}

	/**新增
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#POST}
	 */
	@PostMapping("post")
	@Override
	public String post(@RequestBody String request, HttpSession session) {
		return super.post(request, session);
	}

	/**修改
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#PUT}
	 */
	@PostMapping("put")
	@Override
	public String put(@RequestBody String request, HttpSession session) {
		return super.put(request, session);
	}

	/**删除
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#DELETE}
	 */
	@PostMapping("delete")
	@Override
	public String delete(@RequestBody String request, HttpSession session) {
		return super.delete(request, session);
	}



	//通用接口，非事务型操作 和 简单事务型操作 都可通过这些接口自动化实现>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}