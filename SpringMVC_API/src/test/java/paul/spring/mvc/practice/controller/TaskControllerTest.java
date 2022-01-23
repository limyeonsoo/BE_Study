//package paul.spring.mvc.practice.controller;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import paul.spring.mvc.practice.tasks.TaskService;
//
//@ExtendWith(MockitoExtension.class)
//class TaskControllerTest {
//
//    static String randomAlphaneumeric() {
//        return RandomStringUtils.randomAlphanumeric(10);
//    }
//
//    @InjectMocks
//    TaskController underTest;
//
//    @Mock
//    TaskService taskService;
//
//    private WebTestClient webTestClient;
//
//    @BeforeEach
//    void setUp() {
//        webTestClient = WebTestClient.bindToController(underTest).build();
//    }
//
//
//    @Test
//    void create() {
//        // given
//        final var details = randomAlphaneumeric();
//
//
//        // when
//
//        webTestClient.post().uri("tasks/")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$[0].id").isEqualTo("123");
//
//        // then
//    }
//}
