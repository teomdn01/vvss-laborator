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

import static org.junit.Assert.assertEquals;

public class AssignmentServiceTest {
    private Service service;

    private String assignmentId = "123";
    private String assignmentDescription = "1233";
    private int assignmentDeadline = 3;
    private int assignmentStartLin = 2;


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
    public void addAssignmentSuccess(){
        assert service.saveTema("1123230", "tema 1", 3, 1) == 0;
    }

    @Test
    public void addAssignmentThrowsException(){
        assert service.saveTema("1323213", "tema 1", 1, 3) == 1;
    }


    @Test
    public void saveTema_wrong_nullId() {
        int result = service.saveTema(null, assignmentDescription, assignmentDeadline, assignmentStartLin);
        assertEquals(1, result);
    }

    @Test
    public void saveTema_wrong_nullDescriere() {
        int result = service.saveTema(assignmentId, null, assignmentDeadline, assignmentStartLin);
        assertEquals(1, result);
    }

    @Test
    public void saveTema_wrong_zeroDeadline() {
        int result = service.saveTema(assignmentId, assignmentDescription, 0, assignmentStartLin);
        assertEquals(1, result);
    }

    @Test
    public void saveTema_wrong_zeroStartline() {
        int result = service.saveTema(assignmentId, assignmentDescription, assignmentDeadline, 0);
        assertEquals(1, result);
    }

    @Test
    public void saveTema_wrong_duplicate() {
        service.saveTema(assignmentId, assignmentDescription, assignmentDeadline, assignmentStartLin);
        int result = service.saveTema(assignmentId, assignmentDescription, assignmentDeadline, assignmentStartLin);
        assertEquals(1, result);
    }
}