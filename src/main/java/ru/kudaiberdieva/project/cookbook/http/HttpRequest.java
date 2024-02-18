package ru.kudaiberdieva.project.cookbook.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequest {
    private String rawRequest;
    private String uri;
    private String body;
    private HttpMethod method;
    private Map<String, String> parameters;

    public HttpRequest(String get, String s, String s1) {
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public String getRoute() {
        return method + " " + uri;
    }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        parseRequestLine();

        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            List<String> lines = rawRequest.lines().collect(Collectors.toList());
            int splitLine = -1;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).isEmpty()) {
                    splitLine = i;
                    break;
                }
            }
            if (splitLine > -1) {
                StringBuilder bodyBuilder = new StringBuilder();
                for (int i = splitLine + 1; i < lines.size(); i++) {
                    bodyBuilder.append(lines.get(i));
                }
                this.body = bodyBuilder.toString();
            }
        }
        System.out.println(rawRequest);
    }

    private void parseRequestLine() {
        if (rawRequest == null) {
            return;
        }
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        this.parameters = new HashMap<>();
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            this.uri = elements[0];
            String[] keysValues = elements[1].split("&");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                this.parameters.put(keyValue[0], keyValue[1]);
            }
        }
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public void printInfo(boolean showRawRequest) {
        if (showRawRequest) {
            System.out.println(rawRequest);
        }
        System.out.println("URI: " + uri);
        System.out.println("HTTP METHOD: " + method);
    }
}
