package web.controller;

import com.jalizadeh.todocial.system.service.CommonServices;
import com.jalizadeh.todocial.system.service.PasswordResetTokenService;
import com.jalizadeh.todocial.system.service.TokenService;
import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.web.controller.LoginSignupController;
import utils.TestUtils;
import com.jalizadeh.todocial.web.model.FlashMessage;
import com.jalizadeh.todocial.web.model.PasswordResetToken;
import com.jalizadeh.todocial.web.model.SecurityQuestionDefinition;
import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.registration.OnPasswordResetEvent;
import com.jalizadeh.todocial.web.repository.SecurityQuestionDefinitionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("User access management tests")
@Tag("unit_test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class LoginSignupControllerTest {

    @Mock
    UserService userService;

    @Mock
    PasswordResetTokenService prtService;

    @Mock
    TokenService tokenService;

    @Mock
    SecurityQuestionDefinitionRepository sqdRepo;

    @Mock
    ApplicationEventPublisher eventPublisher;

    @Mock
    CommonServices utilites;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RedirectAttributes redirectAttributes;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @Mock
    SecurityContextHolder securityContextHolder;

    @Mock
    WebRequest webRequest;

    @InjectMocks
    LoginSignupController loginSignupController;

    ModelMap modelMap;

    @BeforeEach
    void setUp() {
        modelMap = new ModelMap();
    }

    @Test
    @DisplayName("If the user is anonymous (not logged in), is navigated to /login page")
    void testLoginSignupController_whenNotLoggedIn_returnsLoginPageViewName() {
        when(utilites.isUserAnonymous()).thenReturn(true);
        String viewName = loginSignupController.showLoginPage(modelMap, request);

        assertEquals("login", viewName);
        //TODO: how should be checked?
        //assertEquals(settings, modelMap.get("settings"));
        //assertEquals(?, modelMap.get("user")
        assertEquals("Log in", modelMap.get("PageTitle"));
        assertNull(userService.GetAuthenticatedUser());
    }

    @Test
    @DisplayName("If the user is already logged in and navigates to /login page, he is redirected to homepage")
    void testLoginSignupController_whenAlreadyLoggedIn_returnsRedirectToHomepage() {
        when(utilites.isUserAnonymous()).thenReturn(false);
        String viewName = loginSignupController.showLoginPage(modelMap, request);

        assertEquals("redirect:/", viewName);
    }

    @Test
    @Disabled("SecurityContextHolder.getContext() is static method and Mockito cant handle")
    void logout() {
        // Mocking the behavior of SecurityContextHolder.getContext().getAuthentication()
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        doNothing().when(new SecurityContextLogoutHandler()).logout(request, response, authentication);

        ModelAndView modelAndView = loginSignupController.logout(request, response, redirectAttributes);

        assertEquals("redirect:/login", modelAndView.getViewName());

        verify(redirectAttributes).addFlashAttribute("message", new FlashMessage("You logged out successfully.", FlashMessage.Status.success));
        verify(securityContext).setAuthentication(null);

        //verify(Log.info("User: " + authentication.getName() + " signed out successfully"));
    }

    @Test
    @DisplayName("Forgot password page showed")
    void testLoginSignupController_showForgotPasswordPage() {
        String viewName = loginSignupController.showForgotPasswordPage(modelMap);
        assertEquals("forgot-password", viewName);
    }

    @Test
    @DisplayName("Forgot password is completed successfully for existing user")
    void testLoginSignupController_whenUserExists_redirectsWithSuccessMsg() {
        User existingUser = new User();
        when(userService.findByEmail(any(String.class))).thenReturn(existingUser);
        doNothing().when(prtService).saveNewToken(any(PasswordResetToken.class));
        doNothing().when(eventPublisher).publishEvent(any(OnPasswordResetEvent.class));

        String viewName = loginSignupController.forgotPasswordSubmit(modelMap, "existing@mail.com", webRequest, redirectAttributes);
        assertEquals("redirect:/forgot-password", viewName);
        verify(redirectAttributes).addFlashAttribute("flash", new FlashMessage("An email is sent to you", FlashMessage.Status.success));
    }

    @Test
    @DisplayName("Forgot password is skipped for not-existing user")
    void testLoginSignupController_whenUserNotExists_redirectsWithErrorMsg() {
        when(userService.findByEmail(any(String.class))).thenReturn(null);

        String viewName = loginSignupController.forgotPasswordSubmit(modelMap, "email@mail.com", webRequest, redirectAttributes);
        assertEquals("redirect:/forgot-password", viewName);
        verify(redirectAttributes).addFlashAttribute("flash",
                new FlashMessage("The email is not registered", FlashMessage.Status.danger));
    }

    @Test
    @Disabled("")
    void testLoginSignupController_whenTokenIsValid_navigatedToChangePasswordPage() {
        when(tokenService.validateVerificationToken(any(String.class), any(String.class))).thenReturn("valid");

        User user = new User();
        when(prtService.findUserByToken(any(String.class))).thenReturn(user);

        when(new UsernamePasswordAuthenticationToken(user, null, TestUtils.createMockAuthorities("ROLE_USER")))
                .thenReturn((UsernamePasswordAuthenticationToken) authentication);

        String viewName = loginSignupController.showResetPasswordConfirmed("Token123", modelMap, redirectAttributes);

        assertEquals("change-password", viewName);
    }

    @Test
    @Disabled
    void changePassword() {
    }

    @Test
    @DisplayName("Show sign up page to anonymous users")
    void testLoginSignupController_whenUserIsAnonymous_showSignupPage() {
        when(utilites.isUserAnonymous()).thenReturn(true);
        when(sqdRepo.findAll()).thenReturn(createMockedSecurityQuestions());

        String viewName = loginSignupController.showSignupPage(modelMap);

        assertEquals("signup", viewName);
        //TODO: test settings
        assertEquals("Sign up", modelMap.get("PageTitle"));
        // TODO
        // assertEquals(new User(), modelMap.getAttribute("user"));
        assertEquals(createMockedSecurityQuestions(), modelMap.get("securityQuestions"));
    }

    @Test
    @DisplayName("Redirect to home page when logged in users open sign up page")
    void testLoginSignupController_whenUserIsLoggedIn_redirectToHomepageInsteadofSignupPage(){
        when(utilites.isUserAnonymous()).thenReturn(false);

        String viewName = loginSignupController.showSignupPage(modelMap);

        assertEquals("redirect:/", viewName);
    }

    @Test
    @Disabled
    void registerUserAccount() {
    }

    @Test
    @Disabled
    void confirmRegistration() {
    }


    private List<SecurityQuestionDefinition> createMockedSecurityQuestions() {
        List<SecurityQuestionDefinition> questions = new ArrayList<>();

        questions.add(new SecurityQuestionDefinition(1L, "What was the name of your first toy?"));
        questions.add(new SecurityQuestionDefinition(2L, "At what age you first met your love?"));

        return questions;
    }
}