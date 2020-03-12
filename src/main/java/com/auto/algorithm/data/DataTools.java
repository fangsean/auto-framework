package com.auto.algorithm.data;

import com.auto.entity.User;
import com.auto.util.JsonUtils;
import com.google.common.base.Splitter;
import io.vavr.Tuple3;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */
public class DataTools {

    public static final String SEPARATOR_FIELD = "#";
    public static final String CONNECTOR_KV = "=";
    public static final String SEPARATOR_REG = ",";
    public static final String CONTENT_NULL = "null";
    // 子数据key
    public static final String CONTENT_CHILDREN = "content";

    /**
     * 运用PropertyUtils取得bean的值，并根据keyName归类
     *
     * @param list    List beans
     * @param keyName 需要归类的bean的属性名称
     * @return LinkedHashMap<String, List>,有顺序的map，map的key为需要归类的bean的属性名+"="+对应的属性值：eg："key=value"，value为List<bean>
     */

    public static <T> LinkedHashMap<String, List<T>> groupClassify(List<T> list, String keyName) {
        LinkedHashMap<String, List<T>> target = new LinkedHashMap();
        List<String> keyNames = Splitter.on(SEPARATOR_REG)
                .trimResults()
                .omitEmptyStrings()
                .splitToList(keyName);

        for (T obj : list) {
            // 取得bean需要归类的属性（keyName）的值，不做类型转换
            StringBuilder keyValueBf = new StringBuilder();
            keyNames.stream().forEach(key -> {
                Object value = null;
                try {
                    value = Optional.ofNullable(PropertyUtils.getProperty(obj, key)).orElse("");
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                keyValueBf
                        .append(key)
                        .append(CONNECTOR_KV)
                        .append(value)
                        .append(SEPARATOR_FIELD);
            });

            String keyValue = keyValueBf.deleteCharAt(keyValueBf.length() - 1).toString();
            if (!target.containsKey(keyValue)) {
                // 如果map中没有归类key值，则添加key值和相应的list
                List keyList = new ArrayList();
                keyList.add(obj);
                target.put(keyValue, keyList);
            } else {
                // 如果有归类key值，则在相应的list中添加这个bean
                ArrayList keyList = (ArrayList) target.get(keyValue);
                keyList.add(obj);
            }
        }
        return target;
    }

    /**
     * 将归类的Map<String, List>按照 keyName归类，并用index控制递归。
     *
     * @param mocl     map of classified list，也就是运用方法 LinkedHashMap<String, List> groupClassify(List list, String keyName)，将list归类成的map
     * @param index    用条件 index < keyNames.length控制递归
     * @param keyNames 需要归类的bean的属性名称
     * @return
     */
    private static <T> LinkedHashMap<String, Map> groupClassify(Map<String, List<T>> mocl, int index, String... keyNames) {
        // 单步理解：target是函数参数Map<String, List> mocl再次归类成的LinkedHashMap<String,Map>
        // 递归到最后这个是最终归类好的map
        LinkedHashMap<String, Map> target = new LinkedHashMap();
        // 控制递归条件，起始的index应该总是1。
        if (index < keyNames.length) {
            // swap用来保存参数index的值，这是最容易出错的一个地方
            // 用它保证：在参数Map<String, List> mocl层面循环时用相同的index参数值。
            int swap = index;
            for (Map.Entry<String, List<T>> entry : mocl.entrySet()) {
                String moclKey = entry.getKey();
                List<T> moclList = entry.getValue();
                // 将List<bean>再次归类
                LinkedHashMap<String, List<T>> mocl$ = groupClassify(moclList, keyNames[index]);
                // 如果index达到了数组的最后一个，一定是List<bean>转map，递归结束
                if (index == keyNames.length - 1) {
                    target.put(moclKey, mocl$);
                } else {
                    // 将List<bean>转map得到的_mocl，再次归类
                    // mocm 为map of classified map的简称
                    LinkedHashMap<String, Map> mocm = groupClassify(mocl$, ++index, keyNames);
                    target.put(moclKey, mocm);
                }
                index = swap;
            }
        }
        return target;
    }

    /**
     * 将Map<String, List> map按照bean需要归类的属性名keyName归类
     *
     * @param map     map of classified list list归类成的map
     * @param keyName bean需要归类的属性名
     * @return
     */
    public static <T> LinkedHashMap<String, Map> groupClassifyMap(Map<String, List<T>> map, String keyName) {
        LinkedHashMap<String, Map> target = new LinkedHashMap();
        for (Map.Entry<String, List<T>> entry : map.entrySet()) {
            List mapList = entry.getValue();
            String mapKey = entry.getKey();
            LinkedHashMap<String, List<T>> keyMap = groupClassify(mapList, keyName);
            target.put(mapKey, keyMap);
        }
        return target;
    }

    /**
     * 将List<bean> 按照指定的bean的属性进行归类,keyNames的先后顺序会影响归类结果。
     * eg:一个学生列表，按照班级和性别归类<br>
     * Map map = CollectionUtils.groupClassifyList(studentList, "classId","sex");
     *
     * @param list     List beans
     * @param keyNames 数组包含需要归类的bean的属性名称
     * @return 归类的有顺序的树状map, eg："key1#key2"
     * map的值为List或者map
     */
    public static LinkedHashMap groupClassifyList(List list, String... keyNames) {
        if (keyNames == null || keyNames.length == 0) {
            return null;
        }
        if (keyNames.length == 1) {
            return groupClassify(list, keyNames[0]);
        } else {
            return groupClassify(groupClassify(list, keyNames[0]), 1, keyNames);
        }
    }


    /**
     * key值转换
     *
     * @param value
     * @return
     */
    public static Map str2Map(String value) {
        HashMap map = new HashMap();
        String[] k_vs = value.split(SEPARATOR_FIELD);
        for (int i = 0; i < k_vs.length; i++) {
            String[] k_v = k_vs[i].split(CONNECTOR_KV);
            if (StringUtils.isBlank(k_v[0])) {
                // key为空时，是否存入
                continue;
            } else if (k_v.length == 1 || CONTENT_NULL.equals(k_v[1])) {
                // value为空时，json格式是否显示
                map.put(k_v[0], null);
            } else {
                map.put(k_v[0], k_v[1]);
            }
        }
        return map;
    }


    /**
     * 所有节点遍历转换成List
     * 通过递归方式整理数据
     *
     * @param mocl
     * @param index
     * @param <T>
     * @return
     */
    public static <T> LinkedList<Object> transformList(List mocl, int index) {
        // 控制递归条件，起始的index应该总是1。
        LinkedList<Object> content = new LinkedList<>();
        if (index <= 0) {
            return content;
        }
        index--;
        for (Object obj : mocl) {
            if (obj instanceof List) {
                LinkedList<Object> maps = transformList((List) obj, index);
                content.addAll(maps);
            } else if (obj instanceof Map) {
                LinkedList<Map> maps = transformMap((Map<String, Object>) obj, index);
                content.addAll(maps);
            } else {
                content.add(obj);
            }
        }
        return content;
    }


    /**
     * 所有节点遍历转换成map
     * 通过递归方式整理数据
     *
     * @param mocl
     * @param index
     * @param <T>
     * @return
     */
    public static <T> LinkedList<Map> transformMap(Map<String, Object> mocl, int index) {
        // 控制递归条件，起始的index应该总是1。
        LinkedList<Map> objects = new LinkedList<>();
        if (index <= 0) {
            return objects;
        }
        // swap用来保存参数index的值，这是最容易出错的一个地方
        // 用它保证：在参数Map<String, List> mocl层面循环时用相同的index参数值。
        index--;
        for (String moclKey : mocl.keySet()) {
            Map<Object, Object> objectMap = str2Map(moclKey);
            // 将List<bean>再次归类
            Object object = mocl.get(moclKey);
            if (object instanceof List) {
                LinkedList<Object> maps = transformList((List) object, index);
                objectMap.put(CONTENT_CHILDREN, maps);
                objects.add(objectMap);
            } else if (object instanceof Map) {
                LinkedList<Map> maps = transformMap((Map<String, Object>) mocl.get(moclKey), index);
                objectMap.put(CONTENT_CHILDREN, maps);
                objects.add(objectMap);
            }
        }
        return objects;
    }


    /**
     * 所有节点遍历转换成map
     *
     * @param mocl
     * @param index
     * @param <T>
     * @return
     */
    public static <T> LinkedList<Map> transform(Map<String, Object> mocl, int index) {
        LinkedList<Map> root = new LinkedList<>();
        for (String moclKey : mocl.keySet()) {
            LinkedList<Map> objects = new LinkedList<>();
            Map<Object, Object> objectMap = str2Map(moclKey);
            Object obj = mocl.get(moclKey);
            if (obj instanceof String) {
                Map map = str2Map((String) obj);
                objects.add(map);
            } else if (obj instanceof List) {
                LinkedList<Object> maps = transformList((List) obj, --index);
                objectMap.put(CONTENT_CHILDREN, maps);
                objects.add(objectMap);
            } else if (obj instanceof Map) {
                LinkedList<Map> maps = transformMap((Map<String, Object>) obj, --index);
                objects.addAll(maps);
            }
            objectMap.put(CONTENT_CHILDREN, objects);
            root.add(objectMap);
        }
        return root;
    }


    /**
     * 统一方法入口
     *
     * @param mocl     处理集合
     * @param index    层级
     * @param keyNames 字段
     * @param <T>
     * @return
     */
    public static <T> LinkedList<Map> group(List mocl, int index, String... keyNames) {
        return transform(groupClassifyList(mocl, keyNames), index);
    }


    /**
     * eg:
     *
     * @param args
     */
    public static void main(String[] args) {
        // new LinkedListIntChecklistImpl()
        // 待处理信息
        List<User> objects = new LinkedList<>();
        objects.add(User.builder().id(1L).tenantId(2L).age(1).name("什么").phone("1351234567890").role(12L).testDate(new Date()).testType(1).build());
        objects.add(User.builder().id(1L).tenantId(2L).age(1).name("什么").phone("1351234567890").role(12L).testDate(new Date()).testType(1).build());
        objects.add(User.builder().id(1L).tenantId(2L).age(2).name("撒子").phone("1351234567890").role(12L).testDate(new Date()).testType(1).build());
        objects.add(User.builder().id(1L).tenantId(2L).age(2).name("撒子").phone("1351234567891").role(12L).testDate(new Date()).testType(1).build());
        objects.add(User.builder().id(1L).tenantId(2L).age(2).name("撒子").phone("1351234567891").role(12L).testDate(new Date()).testType(2).build());
        objects.add(User.builder().id(1L).tenantId(2L).age(3).name("撒子").phone("1351234567892").role(12L).testDate(new Date()).testType(2).build());
        objects.add(User.builder().id(1L).tenantId(2L).age(3).name("什么").phone("1351234567892").role(12L).testDate(new Date()).testType(2).build());
        objects.add(User.builder().id(1L).tenantId(2L).age(3).name("什么").phone("1351234567890").role(12L).testDate(new Date()).testType(2).build());

        // 最终分解的信息
        LinkedList<Map> role = group(objects, 2, "id,tenantId,age,name,phone", "role", "testType,testDate");
        System.out.println(JsonUtils.toJson(role));

        System.out.println("======================================");

        Map<Integer, Map<String, List<User>>> collect = Optional.ofNullable(objects)
                .orElse(Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(User::getAge).reversed())
                .collect(Collectors.groupingBy(User::getTestType,
                        Collectors.groupingBy(User::getName)));
        System.out.println(JsonUtils.toJson(collect));

        System.out.println("======================================");

        Map<Integer, Map<String, List<Tuple3>>> collect1 =
                Optional.ofNullable(objects)
                        .orElse(Collections.emptyList())
                        .stream()
                        .sorted(Comparator.comparing(User::getAge).reversed())
                        .collect(Collectors.groupingBy(User::getTestType,
                                Collectors.groupingBy(User::getName,
                                        Collectors.collectingAndThen(Collectors.toList(),
                                                lists -> lists.stream().map(history ->
                                                        new Tuple3(
                                                                history.getName(),
                                                                history.getPhone(),
                                                                history.getTestDate())
                                                ).collect(Collectors.toList())))));
        System.out.println(JsonUtils.toJson(collect1));

    }


}
