package com.alfabeta.service.system;

import com.alfabeta.model.system.SystemErrorLog;
import com.alfabeta.repository.system.SystemErrorLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.io.StringWriter;

@Service
public class ErrorTrackerService {

    private final SystemErrorLogRepository repo;

    public ErrorTrackerService(SystemErrorLogRepository repo) {
        this.repo = repo;
    }

    public void trackException(Exception ex, HttpServletRequest request, String errorCode) {
        SystemErrorLog log = new SystemErrorLog();
        log.setErrorCode(errorCode);
        log.setMessage(ex.getMessage());
        log.setExceptionType(ex.getClass().getSimpleName());
        log.setEndpoint(request != null ? request.getRequestURI() : "N/A");
        log.setRequestMethod(request != null ? request.getMethod() : "N/A");
        log.setClientIp(request != null ? request.getRemoteAddr() : "N/A");

        // Capture full stack trace
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        log.setStackTrace(sw.toString());

        // TODO: set logged-in user (if security context available)
        log.setUserEmail("anonymous");

        repo.save(log);
    }
}
