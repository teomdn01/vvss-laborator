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
        assert service.saveStudent(null, "victor", 935) == 1;
    }

    @Test
    public void addStudentSuccess(){
        assert service.saveStudent("5", "teo", 935) == 0;
    }

    @Test
    public void addStudentInvalidId() {
        assert service.saveStudent("-5", "simo", 935) == 1;
        assert service.saveStudent("2.5", "simo", 935) == 1;
    }

    @Test
    public void addStudentNotUniqueId(){
        assert service.saveStudent("1", "simo", 935) == 0;
        assert service.saveStudent("1", "teo", 935) == 1;
    }

    @Test
    public void addStudentEmptyName(){
        assert service.saveStudent("10", "", 935) == 1;
    }

    @Test
    public void addStudentNullName(){
        assert service.saveStudent("11", null, 935) == 1;
    }

    @Test
    public void addStudentWrongGroup(){
        assert service.saveStudent("12", "simo", 100) == 1;
        assert service.saveStudent("12", "simo", 1000) == 1;
    }
}
