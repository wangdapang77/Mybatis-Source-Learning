/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.reflection.factory;

import org.apache.ibatis.reflection.ReflectionException;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author Clinton Begin
 * 默认的对象工厂，对象工厂的方法实现，对象都是通过工厂生产
 */
public class DefaultObjectFactory implements ObjectFactory, Serializable {

  private static final long serialVersionUID = -8855120656740914948L;

  @Override
  public <T> T create(Class<T> type) {
    return create(type, null, null);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
    // 根据接口创建具体的类
    Class<?> classToCreate = resolveInterface(type);
    // we know types are assignable
    // 实例化这个创建的类
    return (T) instantiateClass(classToCreate, constructorArgTypes, constructorArgs);
  }

  @Override
  public void setProperties(Properties properties) {
    // no props for default
    // 没有默认的属性
  }

  // 实例化类具体的方法实现
  <T> T instantiateClass(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
    try {
      Constructor<T> constructor;
      // 如果传入的constructor为null
      if (constructorArgTypes == null || constructorArgs == null) {
        constructor = type.getDeclaredConstructor();
        if (!constructor.isAccessible()) {
          constructor.setAccessible(true);
        }
        // 实例化的关键步骤
        return constructor.newInstance();
      }
      // 当传入constructor，调用传入的构造函数，
      constructor = type.getDeclaredConstructor(constructorArgTypes.toArray(new Class[constructorArgTypes.size()]));
      if (!constructor.isAccessible()) {
        constructor.setAccessible(true);
      }
      // 调用传入构造函数实例化的关键步骤
      return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
    } catch (Exception e) {
      // 出错后对调整异常，抛出反射异常
      StringBuilder argTypes = new StringBuilder();
      if (constructorArgTypes != null && !constructorArgTypes.isEmpty()) {
        for (Class<?> argType : constructorArgTypes) {
          argTypes.append(argType.getSimpleName());
          argTypes.append(",");
        }
        argTypes.deleteCharAt(argTypes.length() - 1); // remove trailing ,
      }
      StringBuilder argValues = new StringBuilder();
      if (constructorArgs != null && !constructorArgs.isEmpty()) {
        for (Object argValue : constructorArgs) {
          argValues.append(String.valueOf(argValue));
          argValues.append(",");
        }
        argValues.deleteCharAt(argValues.length() - 1); // remove trailing ,
      }
      throw new ReflectionException("Error instantiating " + type + " with invalid types (" + argTypes + ") or values (" + argValues + "). Cause: " + e, e);
    }
  }

  // 解析接口，将interface转为实际class
  protected Class<?> resolveInterface(Class<?> type) {
    Class<?> classToCreate;
    if (type == List.class || type == Collection.class || type == Iterable.class) {
      //List|Collection|Iterable转ArrayList
      classToCreate = ArrayList.class;
    } else if (type == Map.class) {
      //Map转HashMap
      classToCreate = HashMap.class;
    } else if (type == SortedSet.class) { // issue #510 Collections Support
      // SortedSet转TreeSet
      classToCreate = TreeSet.class;
    } else if (type == Set.class) {
      // Set转HashSet
      classToCreate = HashSet.class;
    } else {
      // 其他转传入类型
      classToCreate = type;
    }
    return classToCreate;
  }

  @Override
  public <T> boolean isCollection(Class<T> type) {
    // 判断是否为Collection中的子类
    return Collection.class.isAssignableFrom(type);
  }

}
