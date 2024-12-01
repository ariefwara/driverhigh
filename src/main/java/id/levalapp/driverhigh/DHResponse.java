package id.levalapp.driverhigh;

public class DHResponse {
    private final int status;
    private final String body;

    /**
     * Constructs a DHResponse object with a status and a body.
     *
     * @param status The HTTP status code
     * @param body   The response body as a string
     */
    public DHResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    /**
     * Retrieves the HTTP status code.
     *
     * @return The HTTP status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Retrieves the response body.
     *
     * @return The response body as a string
     */
    public String getBody() {
        return body;
    }
}
