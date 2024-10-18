//package kg.megacom.diplomaprojectschoolmegalab.controller;
//
//import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
//import kg.megacom.diplomaprojectschoolmegalab.dto.SubjectsDto;
//import kg.megacom.diplomaprojectschoolmegalab.service.impl.SubjectServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//public class SubjectControllerTest {
//
//    @InjectMocks
//    private SubjectController subjectController;
//
//    @Mock
//    private SubjectServiceImpl subjectService;
//
//    private SubjectsDto subjectDto;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        subjectDto = new SubjectsDto();
//        subjectDto.setId(1L);
////        subjectDto.setName("Mathematics");
//    }
//
//    @Test
//    public void testCreateSubject() {
//        // Mocking the service method
//        doNothing().when(subjectService).create(any(SubjectsDto.class));
//
//        ResponseEntity<Response<SubjectsDto>> response = subjectController.create(subjectDto);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("Subject is created successfully", response.getBody().getMessage());
//        assertEquals(subjectDto, response.getBody().getData());
//    }
//
//    @Test
//    public void testGetSubjectById() {
//        when(subjectService.getById(1L)).thenReturn(new Response<>("Subject found", subjectDto));
//
//        ResponseEntity<Response<SubjectsDto>> response = subjectController.getById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Subject found", response.getBody().getMessage());
//        assertEquals(subjectDto, response.getBody().getData());
//    }
//
//    @Test
//    public void testGetAllSubjects() {
//        List<SubjectsDto> subjectsList = Arrays.asList(subjectDto);
//        when(subjectService.getAll()).thenReturn(new Response<>("Subjects found", subjectsList));
//
//        ResponseEntity<Response<List<SubjectsDto>>> response = subjectController.getAll();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Subjects found", response.getBody().getMessage());
//        assertEquals(subjectsList, response.getBody().getData());
//    }
//
//    @Test
//    public void testUpdateSubject() {
//        when(subjectService.update(any(SubjectsDto.class), eq(1L)))
//                .thenReturn(new Response<>("Subject updated", subjectDto));
//
//        ResponseEntity<Response<SubjectsDto>> response = subjectController.update(subjectDto, 1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Subject updated", response.getBody().getMessage());
//        assertEquals(subjectDto, response.getBody().getData());
//    }
//
//    @Test
//    public void testDeleteSubject() {
//        doNothing().when(subjectService).delete(1L);
//
//        ResponseEntity<Response<String>> response = subjectController.delete(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Subject deleted successfully!", response.getBody().getMessage());
//        assertEquals("ID: 1", response.getBody().getData());
//    }
//}
