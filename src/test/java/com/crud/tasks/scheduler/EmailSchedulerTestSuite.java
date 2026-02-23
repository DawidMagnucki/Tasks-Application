package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTestSuite {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldSendInformationEmailWithSingularTask() {
        // Given
        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");

        // Używamy ArgumentCaptor, aby podejrzeć co dokładnie scheduler wysłał do serwisu mailowego
        ArgumentCaptor<Mail> mailArgumentCaptor = ArgumentCaptor.forClass(Mail.class);

        // When
        emailScheduler.sendInformationEmail();

        // Then
        verify(simpleEmailService, times(1)).send(mailArgumentCaptor.capture());
        Mail capturedMail = mailArgumentCaptor.getValue();

        assertEquals("admin@test.com", capturedMail.getMailTo());
        assertEquals("Tasks: Once a day email", capturedMail.getSubject());
        assertEquals("Currently in database you got: 1 task.", capturedMail.getMessage());
    }

    @Test
    void shouldSendInformationEmailWithPluralTasks() {
        // Given
        when(taskRepository.count()).thenReturn(5L);
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");

        ArgumentCaptor<Mail> mailArgumentCaptor = ArgumentCaptor.forClass(Mail.class);

        // When
        emailScheduler.sendInformationEmail();

        // Then
        verify(simpleEmailService, times(1)).send(mailArgumentCaptor.capture());
        Mail capturedMail = mailArgumentCaptor.getValue();

        assertEquals("Currently in database you got: 5 tasks.", capturedMail.getMessage());
    }
}
