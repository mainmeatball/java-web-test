package com.sstep.http.controller.impl;

import com.sstep.http.Url;
import com.sstep.http.annotation.Get;
import com.sstep.http.controller.interfaces.WebController;
import com.sstep.http.entity.FileContent;
import com.sstep.util.HttpUtilsKt;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author sstepanov
 */
public abstract class AbstractHttpController implements WebController {

    protected final Map<String, Method> stringMethodMap;

    public AbstractHttpController() {
        stringMethodMap = Arrays.stream(getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Get.class))
                .collect(Collectors.toMap(m -> m.getAnnotation(Get.class).value(), Function.identity()));
    }

    @Override
    public FileContent getContent(final Url url) {
        final String path = url.getJoinedPath();
        if (!stringMethodMap.containsKey(path)) {
            return answerNotFound();
        }
        try {
            final String content = (String) stringMethodMap.get(path).invoke(this);
            if (HttpUtilsKt.isValidFile(content)) {
                return readFile(content);
            }
            return generateHtmlTemplate(content);
        } catch (final InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return answerNotFound();
    }

    private FileContent readFile(final String file) {
        try {
            final URL resources = getClass().getClassLoader().getResource("static/" + file);
            if (resources == null) {
                throw new IllegalArgumentException("The file in URL path is invalid.");
            }
            final Path path = Paths.get(resources.getFile());
            final String fileContent = Files.lines(path).collect(Collectors.joining("\n"));
            final LocalDateTime lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneId.of("GMT"));
            return new FileContent(fileContent, lastModified);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return answerNotFound();
    }

    private static FileContent generateHtmlTemplate(final Object header, final Object... params) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html lang=\"en\"><body>");
        if (header != null) {
            sb.append("<h1>").append(header.toString()).append("</h1>");
        }
        for (final Object param : params) {
            sb.append("<p>").append(param.toString()).append("</p>");
        }
        sb.append("</body></html>");
        return new FileContent(sb.toString(), LocalDateTime.now());
    }

    private FileContent answerNotFound() {
        try {
            final URL resources = getClass().getClassLoader().getResource("static/not_found.html");
            if (resources == null) {
                throw new IllegalArgumentException("The file in URL path is invalid.");
            }
            final Path path = Paths.get(resources.getFile());
            final String fileContent = Files.lines(path).collect(Collectors.joining("\n"));
            final LocalDateTime lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneId.of("GMT"));
            return new FileContent(fileContent, lastModified);
        } catch (final IOException e) {
            return new FileContent(e.getLocalizedMessage());
        }
    }
}
