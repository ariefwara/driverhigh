package id.levalapp.driverhigh;

public class Example {
    public static void main(String[] args) throws Exception {
        DriverHigh engine = new DriverHigh(8080);
        engine.get("/xxx", (request) -> {
            return new DHResponse(200, "xxx");
        });
        engine.start();
    }
}
