package DesignPattern.Decorator;

/**
 * @ClassName School
 * @Author nhx
 * @Date 2020/10/30 20:12
 **/
public class School implements Student{
    Student student;
    public School(Student student){
        this.student = student;
    }
    @Override
    public void readBook() {
        System.out.println("在学校读书");
        student.readBook();
    }
}
