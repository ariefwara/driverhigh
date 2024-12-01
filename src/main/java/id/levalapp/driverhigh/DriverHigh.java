package id.levalapp.driverhigh;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DriverHigh {

    private final int port;
    private final Map<String, Function<DHDrift, Object>> getRoutes = new HashMap<>();
    private Server server;

    public DriverHigh(int port) {
        this.port = port;
    }

    public void get(String path, Function<DHDrift, Object> handler) {
        getRoutes.put(path, handler);
    }

    public void start() throws Exception {
        server = new Server(port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        getRoutes.forEach((path, func) -> {
            handler.addServletWithMapping(new ServletHolder(new HttpServlet() {
                @Override
                protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                    handleGetRequest(req, resp, func);
                }
            }), path);
        });

        server.start();
        System.out.println("DriverHigh is running on port " + port);
        server.join();
    }

    private void handleGetRequest(HttpServletRequest request, HttpServletResponse response,
                                  Function<DHDrift, Object> handler) throws IOException {
        Map<String, Object> queryParams = extractQueryParams(request);
        Map<String, String> headers = extractHeaders(request);

        DHRequest dhRequest = new DHRequest(queryParams, headers, null);
        DHDrift dhDrift = new DHDrift(dhRequest, new Object[]{});

        Object result = handler.apply(dhDrift);
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(result.toString());
    }

    private Map<String, Object> extractQueryParams(HttpServletRequest request) {
        Map<String, Object> queryParams = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            if (values.length == 1) {
                queryParams.put(key, values[0]);
            } else {
                queryParams.put(key, values);
            }
        });
        return queryParams;
    }

    private Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                headers.put(headerName, request.getHeader(headerName)));
        return headers;
    }

    public void stop() throws Exception {
        if (server != null) {
            server.stop();
            System.out.println("DriverHigh server stopped.");
        }
    }
}
