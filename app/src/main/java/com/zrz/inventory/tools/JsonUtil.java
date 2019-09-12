package com.zrz.inventory.tools;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Json工具类
 * @author Jackwu
 *
 */
public class JsonUtil {

    /**
     * gson对象
     */
    private final static Gson gson = new GsonBuilder().serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * @param object
     *            待转换的对象
     * @return json格式的字符串
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * @param object
     *            待转换的对象
     * @param ignoreProperties
     *            需要忽略的属性list
     * @return json格式的字符串
     */
    public static String toJsonIgnore(Object object, final List<String> ignoreProperties) {
        if (ignoreProperties == null || ignoreProperties.size() == 0) {
            return toJson(object);
        }

        return new GsonBuilder().serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        String propName = null;
                        for (int i = 0, len = ignoreProperties.size(); i < len; i++) {
                            propName = ignoreProperties.get(i);
                            if (f.getName().equals(propName)) {
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create().toJson(object);
    }

    /**
     * @param object
     *            待转换的对象
     * @param includeProperties
     *            需要输出的属性list
     * @return json格式的字符串
     */
    public static String toJsonInclude(Object object, final List<String> includeProperties) {
        if (includeProperties == null || includeProperties.size() == 0) {
            return toJson(object);
        }

        return new GsonBuilder().serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        String propName = null;
                        for (int i = 0, len = includeProperties.size(); i < len; i++) {
                            propName = includeProperties.get(i);
                            if (f.getName().equals(propName)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create().toJson(object);
    }

    /**
     * @param jsonStr
     *            json格式的字符串
     * @param clz
     *            目标对象的Class对象
     * @return 目标对象
     */
    public static <T> T fromJson(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, clz);
    }

	///**
	// * JSON字符串转换成对象
	// * 
	// * @param jsonString
	// *            需要转换的字符串
	// * @param type
	// *            需要转换的对象类型
	// * @return 对象
	// */
	//@SuppressWarnings("unchecked")
	//public static <T> T fromJson(String jsonString, Class<T> type) {
	//	JSONObject jsonObject = JSONObject.fromObject(jsonString);
	//	return (T) JSONObject.toBean(jsonObject, type);
	//}

	/*
	 * 将JSONArray对象转换成list集合
	 * 
	 * @param jsonArr
	 * @return
	 */
	/*public static List<Object> jsonToList(JSONArray jsonArr) {
		List<Object> list = new ArrayList<Object>();
		for (Object obj : jsonArr) {
			if (obj instanceof JSONArray) {
				list.add(jsonToList((JSONArray) obj));
			} else if (obj instanceof JSONObject) {
				list.add(jsonToMap((JSONObject) obj));
			} else {
				list.add(obj);
			}
		}
		return list;
	}*/

	/*
	 * 将json字符串转换成map对象
	 * 
	 * @param json
	 * @return
	 */
	/*public static Map<String, Object> jsonToMap(String json) {
		JSONObject obj = JSONObject.fromObject(json);
		return jsonToMap(obj);
	}*/

	/*
	 * 将JSONObject转换成map对象
	 * 
	 * @param obj
	 * @return
	 */
	/*public static Map<String, Object> jsonToMap(JSONObject obj) {
		Set<?> set = obj.keySet();
		Map<String, Object> map = new HashMap<String, Object>(set.size());
		for (Object key : obj.keySet()) {
			Object value = obj.get(key);
			if (value instanceof JSONArray) {
				map.put(key.toString(), jsonToList((JSONArray) value));
			} else if (value instanceof JSONObject) {
				map.put(key.toString(), jsonToMap((JSONObject) value));
			} else {
				map.put(key.toString(), obj.get(key));
			}

		}
		return map;
	}*/
}
