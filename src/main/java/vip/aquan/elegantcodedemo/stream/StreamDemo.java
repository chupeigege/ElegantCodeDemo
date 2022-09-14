package vip.aquan.elegantcodedemo.stream;

import vip.aquan.elegantcodedemo.entity.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void main(String[] args) {
        mapOperation();
    }

    private static void mapOperation() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(Student.builder().name("Mike").age(10).build());
        studentList.add(Student.builder().name("Jack").age(13).build());
        studentList.add(Student.builder().name("Lucy").age(13).build());

        //map操作映射出学生的年龄
        List<Integer> ageList = studentList.stream().map(Student::getAge).collect(Collectors.toList());
        System.out.println("ageList:" + ageList);

        //按学生的年龄分组
        Map<Integer, List<Student>> ageGroup = studentList.stream().collect(Collectors.groupingBy(Student::getAge));
        System.out.println("ageGroup:" + ageGroup);

        //过滤年龄小于等于10岁的学生
        List<Student> ageFilter = studentList.stream().filter(s -> s.getAge() <= 10).collect(Collectors.toList());
        System.out.println("ageFilter:" + ageFilter);

        //跳过List的第一个学生
        List<Student> studentSkipList = studentList.stream().skip(1).collect(Collectors.toList());
        System.out.println("studentSkipList:" + studentSkipList);

        //对学生的年龄求和，reduce(BigDecimal::add)
        Integer sumAge = studentList.stream().map(Student::getAge).reduce(Integer::sum).get();
        System.out.println("sumAge:" + sumAge);

        //Optional操作,常用于null值判断
        Optional<Student> firstStudent = studentList.stream().findFirst();
        firstStudent.ifPresent(student -> System.out.println("firstStudent:" + student));

        if (Optional.ofNullable(studentList.stream().findFirst()).isPresent()) {
            System.out.println("value is not null");
        }

    }

    //mapToXXX
    //包括三个方法：mapToInt、mapToDouble、mapToLong
    //是将Stream中的元素转为XXX的类型
    private static void mapToXXX() {
        List<String> list = Arrays.asList("ab", "abc", "abcd", "abcde", "abcdef");
        int[] newList = list.stream().mapToInt(r -> r.length()).toArray();
        System.out.println(Arrays.toString(newList));
    }


}
