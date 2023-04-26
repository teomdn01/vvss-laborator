import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.*;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

public class GradeServiceTest {
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
        service.saveStudent("5", "teo", 935);
        service.saveTema("5", "Tema nr 5", 10, 1);
    }

    @Test
    public void addGradeSuccess(){
        assert service.saveNota("5", "5", 9.5, 5, "Top") == 1;
    }

    @Test
    public void integrationFlowSuccessTest() {
        assert service.saveStudent("12", "moni", 935) == 0;
        assert service.saveTema("12", "Tema nr 12", 5, 2) == 0;
        assert service.saveNota("12", "12", 10, 3, "Top") == 1;
    }
}
