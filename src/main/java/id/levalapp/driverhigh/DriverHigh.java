package id.levalapp.driverhigh;

import id.levalapp.drift.Drift;
import id.levalapp.driverhigh.DHDrift;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * Registers a GET route with the specified path and handler.
     *
     * @param path    The endpoint path (e.g., "/example")
     * @param handler The function to handle the request using DHDrift
     */
    public void get(String path, Function<DHDrift, Object> handler) {
        getRoutes.put(path, handler);
    }

    /**
     * Starts the Jetty server and listens for incoming requests.
     *
     * @throws Exception if the server fails to start
     */
    public void ignite() throws Exception {
        server = new Server(port);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                if ("GET".equalsIgnoreCase(request.getMethod())) {
                    handleGetRequest(target, request, response);
                    baseRequest.setHandled(true);
                }
            }
        });
        server.start();
        System.out.println("DriverHigh is running on port " + port);
        server.join();
    }

    /**
     * Handles a GET request by matching it to the registered route.
     *
     * @param target   The requested path
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     */
    private void handleGetRequest(String target, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Function<DHDrift, Object> handler = getRoutes.get(target);
        if (handler != null) {
            // Create a DHDrift object with a Request and raw params
            Request dhRequest = new Request(new Object[]{request.getParameterMap(), extractHeaders(request)});
            DHDrift dhDrift = new DHDrift(dhRequest, new Object[]{});

            // Execute the handler and generate a response
            Object result = handler.apply(dhDrift);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(result.toString());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("404 Not Found");
        }
    }

    /**
     * Extracts headers from the HttpServletRequest into a Map.
     *
     * @param request The HttpServletRequest object
     * @return A map of header names to their values
     */
    private Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                headers.put(headerName, request.getHeader(headerName)));
        return headers;
    }

    /**
     * Stops the Jetty server.
     *
     * @throws Exception if the server fails to stop
     */
    public void stop() throws Exception {
        if (server != null) {
            server.stop();
            System.out.println("DriverHigh server stopped.");
        }
    }
}
