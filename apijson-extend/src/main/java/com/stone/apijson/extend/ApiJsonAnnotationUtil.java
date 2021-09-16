package com.stone.apijson.extend;

import apijson.MethodAccess;
import apijson.orm.AbstractVerifier;
import com.alibaba.fastjson.JSONObject;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public class ApiJsonAnnotationUtil {
    /**
     * 动态扫描MethodAccess注解，添加到规则库，避免数据库操作
     *
     * @param clz
     */
    public static void fillAccessMap(Class clz) {
        if (AbstractVerifier.ACCESS_MAP.containsKey(clz.getSimpleName())) {
            // 以数据库优先
        } else {
            // 通过注解设置访问控制
            AbstractVerifier.ACCESS_MAP.put(clz.getSimpleName(), AbstractVerifier.getAccessMap((MethodAccess) clz.getAnnotation(MethodAccess.class)));
        }
    }

    /**
     * 动态扫描RequestStructure注解，添加到规则库，避免数据库操作
     *
     * @param clz
     */
    public static void fillRequestMap(Class clz) {
        RequestStructures rss = (RequestStructures) clz.getAnnotation(RequestStructures.class);
        if (rss != null) {
            RequestStructure[] requestStructures = rss.value();
            for (RequestStructure rs : requestStructures) {
                fillRequestMap(rs);
            }
        } else {
            RequestStructure rs = (RequestStructure) clz.getAnnotation(RequestStructure.class);
            if (rs != null) {
                fillRequestMap(rs);
            }
        }
    }

    public static void fillRequestMap(RequestStructure rs) {
        JSONObject item;
        item = new apijson.JSONObject();
        item.put("tag", rs.tag());
        item.put("detail", rs.detail());
        item.put("structure", rs.structure());
        item.put("method", rs.method().name());
        item.put("version", rs.version());
        String cacheKey = AbstractVerifier.getCacheKeyForRequest(rs.method().name(), rs.tag());
        if (!AbstractVerifier.REQUEST_MAP.containsKey(cacheKey)) {
            //数据库优先，数据库不存在了再考虑动态扫描
            SortedMap<Integer, JSONObject> versionedMap = AbstractVerifier.REQUEST_MAP.get(cacheKey);
            if (versionedMap == null) {
                versionedMap = new TreeMap<>(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 == null ? -1 : o2.compareTo(o1);  // 降序
                    }
                });
            }
            versionedMap.put(rs.version(), item);
            AbstractVerifier.REQUEST_MAP.put(cacheKey, versionedMap);
        }
    }


}

