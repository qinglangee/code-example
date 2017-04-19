# annotation

包 java.lang.annotation 中包含所有定义自定义注解所需用到的原注解和接口。如接口 java.lang.annotation.Annotation 是所有注解继承的接口,并且是自动继承，不需要定义时指定，类似于所有类都自动继承Object。

该包同时定义了四个元注解，Documented,Inherited,Target(作用范围，方法，属性，构造方法等),Retention(生命范围，源代码，class,runtime)。

## 四个元注解
四个元注解分别是：@Target,@Retention,@Documented,@Inherited ，再次强调下元注解是java API提供，是专门用来定义注解的注解，其作用分别如下。
  
@Target 表示该注解用于什么地方，可能的值在枚举类 ElemenetType 中，包括：
  
	  ElemenetType.CONSTRUCTOR 构造器声明
	  ElemenetType.FIELD 域声明（包括 enum 实例）
	
	  ElemenetType.LOCAL_VARIABLE 局部变量声明
	  ElemenetType.ANNOTATION_TYPE 作用于注解量声明
	
	  ElemenetType.METHOD 方法声明
	  ElemenetType.PACKAGE 包声明
	  ElemenetType.PARAMETER 参数声明
	  ElemenetType.TYPE 类，接口（包括注解类型）或enum声明
 @Retention 表示在什么级别保存该注解信息。可选的参数值在枚举类型 RetentionPolicy 中，包括：
      
      RetentionPolicy.SOURCE 注解将被编译器丢弃
      RetentionPolicy.CLASS 注解在class文件中可用，但会被VM丢弃
      RetentionPolicy.RUNTIME VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息。
 @Documented 将此注解包含在 javadoc 中 ，它代表着此注解会被javadoc工具提取成文档。在doc文档中的内容会因为此注解的信息内容不同而不同。相当于@see,@param 等。
  
 @Inherited 允许子类继承父类中的注解。

## 注解的属性

开始之前将下定义属性的规则：
@interface用来声明一个注解，其中的每一个方法实际上是声明了一个配置参数。方法的名称就是参数的名称，返回值类型就是参数的类型（返回值类型只能是基本类型、Class、String、enum）。可以通过default来声明参数的默认值。 

## 读取　annotation
jdk1.5 既然增加了注解，肯定就增加了相关读取的api

在java.lang.reflect包中新增了AnnotatedElement接口，JDK源码如下：

	public interface AnnotatedElement {
	    boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);
	    
	    <T extends Annotation> T getAnnotation(Class<T> annotationClass);
	    
	    Annotation[] getAnnotations();
	    
	    Annotation[] getDeclaredAnnotations();
	}


* isAnnotationPresent：判断是否标注了指定注解
* getAnnotation：获取指定注解，没有则返回null
* getAnnotations：获取所有注解，包括继承自基类的，没有则返回长度为0的数组
* getDeclaredAnnotations：获取自身显式标明的所有注解，没有则返回长度为0的数组




[java 注解的几大作用及使用方法详解](http://tmser.com/?post=34)  