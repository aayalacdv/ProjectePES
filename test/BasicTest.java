import org.codehaus.groovy.ast.builder.AstSpecificationCompiler;
import org.junit.*;
import org.junit.validator.PublicClassValidator;

import java.util.*;
import play.test.*;
import models.*;
import net.sf.ehcache.search.expression.EqualTo;

public class BasicTest extends UnitTest {

    @Before
    public void setup(){
        Fixtures.deleteDatabase();
    }

    @Test
    public void CreateandRetrieveUser(){
        //Creamos un usuario nuevo 
        new User ("Axel","pito").save(); 
        User axel = User.find("byName", "Axel").first(); 



        //miramos si funciona 
        assertNotNull(axel);

        assertEquals("Axel", axel.name);
        assertEquals("pito", axel.password);
        assertNotNull(User.exists("Axel", "pito"));
        assertNull(User.exists("Axel", "pingo"));
        assertEquals(0, User.deleteUser(axel));
    }

    @Test
    public void CoursesAndExams (){
        User axel = new User ("Axel", "cubo").save(); 
        Course pes = new Course ("PES",axel).save(); 
        Exam mq = new Exam("MQ", pes, 25).save(); 

    
        pes.addExam(mq); 
        axel.addCourse(pes); 

        assertNotNull(axel);
        assertNotNull(pes);
        assertNotNull(mq);

        assertEquals(1, pes.exams.size());
        assertEquals(1, axel.courses.size()); 

    
       mq.addScore(10); 

       assertEquals(75,pes.percentageLeft); 
       assertEquals(10,pes.exams.get(0).score,0.00001); 
       assertEquals(2.5,pes.currentGrade,0.00001); 
       assertEquals(3.33,pes.meanToPass,0.00001); 

       Exam fq = new Exam("FQ",pes,25).save();
       pes.addExam(fq); 
       fq.addScore(10); 

       assertEquals(50,pes.percentageLeft); 
       assertEquals(10,pes.exams.get(1).score,0.00001); 
       assertEquals(5.0,pes.currentGrade,0.00001); 
       assertEquals(0,pes.meanToPass,0.00001); 


    }


    @Test
    public void testingYML(){
      Fixtures.loadModels("data.yml"); 


      assertEquals(1,User.count()); 
      assertEquals(2, Course.count());
      assertEquals(2, Exam.count());


      assertNotNull(User.exists("Axel", "lavida")); 


      List<Course> courses = Course.find("user.name","Axel").fetch(); 
      assertEquals(2, courses.size());
      
      List<Exam> exams = Exam.find("course.name","PES").fetch(); 
      assertEquals(1, exams.size());



      




     }
   

}
