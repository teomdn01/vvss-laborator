import domain.Nota;
import domain.Pair;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.Silent.class)
public class GradeServiceTest {
    private Service service;

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

        serviceWithMocks = new Service(mockedStudentRepo, mockedTemaRepo, mockedNotaRepo);
    }

    @Test
    public void addGradeSuccess(){
        assert service.saveNota("5", "5", 9.5, 5, "Top") == 1;
    }

    @Test
    public void integrationFlowSuccessTest() {

        Student student = new Student("1", "1", 912);
        Tema tema = new Tema("1123230", "tema 1", 3, 1);
        Nota nota = new Nota(new Pair<>("1", "1123230"), 10, 3, "Top");
        doNothing().when(studentValidatorMock).validate(any());
        doNothing().when(temaValidatorMock).validate(any());
        doNothing().when(notaValidatorMock).validate(any());
        Mockito.when(mockedStudentRepo.exists("1")).thenReturn(false);
        Mockito.when(mockedTemaRepo.exists(tema.getID())).thenReturn(false);
        Mockito.when(mockedTemaRepo.exists(tema.getID())).thenReturn(false);
        Mockito.when(mockedStudentRepo.save(student)).thenReturn(student);
        Mockito.when(mockedTemaRepo.save(tema)).thenReturn(tema);
        Mockito.when(mockedTemaRepo.save(tema)).thenReturn(tema);
        Mockito.when(mockedStudentRepo.findOne(anyString())).thenReturn(student);
        Mockito.when(mockedTemaRepo.findOne(tema.getID())).thenReturn(tema);

        assert serviceWithMocks.saveStudent("1", "1", 912) == 0;
        assert serviceWithMocks.saveTema("1123230", "tema 1", 3, 1) == 0;
        assert serviceWithMocks.saveNota("1", "1123230", 10, 3, "Top") == 1;

    }
}
