import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import repository.*;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AssignmentServiceTest {
    Service service;

    @Mock
    Validator<Student> studentValidatorMock;
    @Mock
    Validator<Tema> temaValidatorMock;
    @Mock
    Validator<Nota> notaValidatorMock;
    @Mock
    StudentRepository mockedStudentRepo;

    @Mock
    NotaRepository mockedNotaRepo;

    @Mock
    TemaRepository mockedTemaRepo;

    @InjectMocks
    Service serviceWithMocks;


    private String assignmentId = "123";
    private String assignmentDescription = "1233";
    private int assignmentDeadline = 3;
    private int assignmentStartLin = 2;

    @Before
    public void setup(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();


        StudentRepository studentRepository = new StudentRepository(studentValidator);
        NotaRepository notaRepository = new NotaRepository(notaValidator);
        TemaRepository temaRepository = new TemaRepository(temaValidator);

        this.service = new Service(studentRepository, temaRepository, notaRepository);

        serviceWithMocks = new Service(mockedStudentRepo, mockedTemaRepo, mockedNotaRepo);
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

    @Test
    public void addAssignmentIntegrationTest() {
        Student student = new Student("1", "1", 912);
        Tema tema = new Tema("1123230", "tema 1", 3, 1);
        doNothing().when(studentValidatorMock).validate(any());
        doNothing().when(temaValidatorMock).validate(any());
        Mockito.when(mockedStudentRepo.exists("1")).thenReturn(false);
        Mockito.when(mockedTemaRepo.exists(tema.getID())).thenReturn(false);
        Mockito.when(mockedStudentRepo.save(student)).thenReturn(student);
        Mockito.when(mockedTemaRepo.save(tema)).thenReturn(tema);
        assert serviceWithMocks.saveStudent("1", "1", 912) == 0;
        assert serviceWithMocks.saveTema("1123230", "tema 1", 3, 1) == 0;
    }
}