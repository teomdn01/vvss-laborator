import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.*;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

public class StudentServiceTest {
    private Service service;
    @Before
    public void setup(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");
        StudentRepository studentRepository = new StudentRepository(studentValidator);
        NotaRepository notaRepository = new NotaRepository(notaValidator);
        TemaRepository temaRepository = new TemaRepository(temaValidator);

        this.service = new Service(studentRepository, temaRepository, notaRepository);
    }

    @Test
    public void addStudentThrowsException(){
        assert service.saveStudent(null, "victor", 935) == 0;
    }

    @Test
    public void addStudentSuccess(){
        assert service.saveStudent("5", "teo", 935) == 1;
    }
}
