package id.levalapp.driverhigh;

import java.util.HashMap;
import java.util.Map;

public class DHRequest {
    private final Map<String, Object> queryParams = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private final String body;

    /**
     * Constructs a Request object with query parameters, headers, and a body.
     *
     * @param params  A map of query parameters
     * @param headers A map of headers
     * @param body    The request body as a string
     */
    public DHRequest(Map<String, Object> params, Map<String, String> headers, String body) {
        if (params != null) {
            this.queryParams.putAll(params);
        }
        if (headers != null) {
            this.headers.putAll(headers);
        }
        this.body = body;
    }

    /**
     * Retrieves a query parameter by name.
     *
     * @param name The name of the query parameter
     * @return The value of the query parameter, or null if not found
     */
    public Object getParam(String name) {
        return queryParams.get(name);
    }

    /**
     * Retrieves a header by name.
     *
     * @param name The name of the header
     * @return The value of the header, or null if not found
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * Retrieves the request body.
     *
     * @return The request body as a string
     */
    public String getBody() {
        return body;
    }

    /**
     * Retrieves all query parameters.
     *
     * @return A map of query parameter names to their values
     */
    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    /**
     * Retrieves all headers.
     *
     * @return A map of header names to their values
     */
    public Map<String, String> getHeaders() {
        return headers;
    }
}
