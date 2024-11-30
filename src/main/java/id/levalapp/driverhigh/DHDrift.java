package id.levalapp.driverhigh;

import id.levalapp.drift.Drift;

public class DHDrift extends Drift {

    private final Request request;

    /**
     * Constructs a DHDrift instance with a Request and parameters.
     *
     * @param request The Request object to associate with this Drift
     * @param params  The parameters for Drift's functionality
     */
    public DHDrift(Request request, Object[] params) {
        super(params);
        this.request = request;
    }

    /**
     * Returns the Request object associated with this Drift.
     *
     * @return The Request object
     */
    public Request getRequest() {
        return request;
    }
}