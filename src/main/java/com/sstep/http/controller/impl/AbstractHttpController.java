package com.sstep.http.controller.impl;

import com.sstep.http.Url;
import com.sstep.http.annotation.Get;
import com.sstep.http.controller.WebController;
import com.sstep.http.entity.FileContent;
import com.sstep.util.FileUtils;
import com.sstep.util.HttpUtilsKt;
import kotlin.Pair;

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
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


/**
 * @author sstepanov
 */
public abstract class AbstractHttpController implements WebController {

    protected final ConcurrentMap<String, Method> stringMethodMap;

    public AbstractHttpController() {
        stringMethodMap = Arrays.stream(getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Get.class))
                .map(method -> Arrays.stream(method.getAnnotation(Get.class).value())
                            .map(endpoint -> new Pair<>(endpoint, method))
                            .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toConcurrentMap(Pair::getFirst, Pair::getSecond));
    }

    @Override
    public FileContent getContent(final Url url) {
        final String path = url.getJoinedPath();
        if (!stringMethodMap.containsKey(path)) {
            return answerNotFound();
        }
        try {
            final String content = (String) stringMethodMap.get(path).invoke(this);
            if (FileUtils.isValidFile(content)) {
                return readFile(content);
            }
            return generateHtmlTemplate(content);
        } catch (final InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return answerNotFound();
    }

    private FileContent readFile(final String file) {
        final FileContent fileContent = doReadFile(file);
        return fileContent == null ? answerNotFound() : fileContent;
    }

    private FileContent answerNotFound() {
        final FileContent fileContent = doReadFile("not_found.html");
        if (fileContent != null) {
            return fileContent;
        }
        return generateHtmlTemplate("Something went wrong");
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

    private FileContent doReadFile(final String file) {
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
            return null;
        }
    }
}
