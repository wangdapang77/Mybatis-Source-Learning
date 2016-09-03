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
package org.apache.ibatis.reflection.property;

import org.apache.ibatis.reflection.ReflectionException;

import java.util.Locale;

/**
 * @author Clinton Begin
 * 属性命名器
 */
public final class PropertyNamer {

  private PropertyNamer() {
    // Prevent Instantiation of Static Class
  }

  // 方法转为属性
  public static String methodToProperty(String name) {
    // 去掉get、set、is
    if (name.startsWith("is")) {
      name = name.substring(2);
    } else if (name.startsWith("get") || name.startsWith("set")) {
      name = name.substring(3);
    } else {
      throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
    }

    // 如果只有一个字母、大于一个字母且第二个字母不是大写，都转为小写
    if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
      name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
    }

    return name;
  }

  // 判断是否为属性
  public static boolean isProperty(String name) {
    return name.startsWith("get") || name.startsWith("set") || name.startsWith("is");
  }

  //判断是否为getter
  public static boolean isGetter(String name) {
    return name.startsWith("get") || name.startsWith("is");
  }

  // 判断是否为setter
  public static boolean isSetter(String name) {
    return name.startsWith("set");
  }

}
