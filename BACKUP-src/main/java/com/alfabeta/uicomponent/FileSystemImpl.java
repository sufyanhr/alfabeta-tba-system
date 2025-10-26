package com.alfabeta.uicomponent;

import com.alfabeta.model.component.ServerJs;
import com.alfabeta.repository.ServerJsRepository;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.SeekableInMemoryByteChannel;
import org.graalvm.polyglot.io.FileSystem;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.alfabeta.repository.specifications.ServerJsSpecification.getByName;

/**
 * ----------------------------------------------------------
 *  Alfabeta File System Implementation
 * ----------------------------------------------------------
 *  Custom implementation of GraalVM's FileSystem interface.
 *  Loads and serves JavaScript code stored in the database
 *  via {@link ServerJsRepository}.
 *
 *  This allows GraalVM to treat database-stored JS code as
 *  in-memory files, enabling dynamic script execution inside
 *  the Alfabeta TBA System.
 * ----------------------------------------------------------
 *
 *  @author  Alfabeta Software
 *  @version 1.1
 *  @since   2025-10
 */
@Slf4j
@Component
public class FileSystemImpl implements FileSystem {

    @com.alfabeta.uicomponent.Inject
    private ServerJsRepository serverJsRepository;

    // ------------------------------------------------------------
    // Path Parsing
    // ------------------------------------------------------------

    @Override
    public Path parsePath(URI uri) {
        return uri != null ? Paths.get(uri.getPath()) : null;
    }

    @Override
    public Path parsePath(String path) {
        return Paths.get(path);
    }

    // ------------------------------------------------------------
    // Access and Directory Management (not used yet)
    // ------------------------------------------------------------

    @Override
    public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) {
        // Future enhancement: Add access validation if needed
    }

    @Override
    public void createDirectory(Path dir, FileAttribute<?>... attrs) {
        // Future enhancement: Could support runtime directory creation
    }

    @Override
    public void delete(Path path) {
        // Not required for in-memory FS
    }

    // ------------------------------------------------------------
    // Core Method — Provide ByteChannel from Database
    // ------------------------------------------------------------

    @Override
    public SeekableByteChannel newByteChannel(
            Path path,
            Set<? extends OpenOption> options,
            FileAttribute<?>... attrs) throws IOException {

        if (path == null) {
            throw new IOException("Invalid path: null");
        }

        try {
            Optional<ServerJs> optional = serverJsRepository.findOne(getByName(path.toString()));

            ServerJs jsCode = optional.orElseThrow(() ->
                    new IOException("No script found in DB for path: " + path));

            String code = jsCode.getCode();
            if (code == null || code.isBlank()) {
                throw new IOException("Empty script for path: " + path);
            }

            return new SeekableInMemoryByteChannel(code.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            log.error("Error loading JS code for path '{}': {}", path, e.getMessage());
            throw new IOException("Error reading script from DB: " + path, e);
        }
    }

    // ------------------------------------------------------------
    // Other GraalVM FS methods
    // ------------------------------------------------------------

    @Override
    public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) {
        // Not needed for DB-based virtual FS
        return null;
    }

    @Override
    public Path toAbsolutePath(Path path) {
        return path != null ? path.toAbsolutePath() : null;
    }

    @Override
    public Path toRealPath(Path path, LinkOption... linkOptions) {
        return path;
    }

    @Override
    public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) {
        return Map.of(); // Empty attributes for virtual files
    }
}
