import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CancerServer {

    public static void main(String[] args) throws IOException {

        String portString = System.getenv("PORT");

        int port = (portString == null || portString.isBlank())
                ? 10000
                : Integer.parseInt(portString);

        HttpServer server = HttpServer.create(
                new InetSocketAddress("0.0.0.0", port), 0
        );

        // Simple test page
        server.createContext("/", exchange -> {

            addCors(exchange);

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                sendResponse(exchange, 204, "", "text/plain");
                return;
            }

            sendResponse(
                    exchange,
                    200,
                    "Breast Cancer Prediction API is running.",
                    "text/plain"
            );
        });

        // Prediction endpoint
        server.createContext("/predict", exchange -> {

            addCors(exchange);

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                sendResponse(exchange, 204, "", "text/plain");
                return;
            }

            if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {

                sendResponse(
                        exchange,
                        405,
                        "{\"error\":\"GET requests only\"}",
                        "application/json"
                );

                return;
            }

            try {

                Map<String, String> params =
                        parseQuery(exchange.getRequestURI().getRawQuery());

                int id = Integer.parseInt(
                        params.getOrDefault("id", "0")
                );

                double radius =
                        requiredDouble(params, "radius");

                double texture =
                        requiredDouble(params, "texture");

                double perimeter =
                        requiredDouble(params, "perimeter");

                double area =
                        requiredDouble(params, "area");

                double smoothness =
                        requiredDouble(params, "smoothness");

                double concavity =
                        requiredDouble(params, "concavity");

                double concavityPoints =
                        requiredDouble(params, "concavityPoints");

                Patient patient = new Patient(
                        id,
                        radius,
                        texture,
                        perimeter,
                        area,
                        smoothness,
                        concavity,
                        concavityPoints
                );

                int score =
                        CancerAnalysis.calculateScore(patient);

                String risk;

                if (score <= 1) {
                    risk = "LOW";
                }

                else if (score <= 3) {
                    risk = "MEDIUM";
                }

                else {
                    risk = "HIGH";
                }

                String json =
                        "{"
                                + "\"patientId\":" + id + ","
                                + "\"score\":" + score + ","
                                + "\"risk\":\"" + risk + "\""
                                + "}";

                sendResponse(
                        exchange,
                        200,
                        json,
                        "application/json"
                );

            }

            catch (Exception e) {

                sendResponse(
                        exchange,
                        400,
                        "{\"error\":\"Invalid or missing tumor measurements\"}",
                        "application/json"
                );
            }
        });

        server.start();

        System.out.println(
                "Cancer prediction server running on port " + port
        );
    }


    private static double requiredDouble(
            Map<String, String> params,
            String name
    ) {

        String value = params.get(name);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing parameter: " + name
            );
        }

        return Double.parseDouble(value);
    }


    private static Map<String, String> parseQuery(
            String query
    ) {

        Map<String, String> params =
                new HashMap<>();

        if (query == null || query.isBlank()) {
            return params;
        }

        for (String pair : query.split("&")) {

            String[] parts =
                    pair.split("=", 2);

            if (parts.length == 2) {

                params.put(

                        URLDecoder.decode(
                                parts[0],
                                StandardCharsets.UTF_8
                        ),

                        URLDecoder.decode(
                                parts[1],
                                StandardCharsets.UTF_8
                        )
                );
            }
        }

        return params;
    }


    private static void addCors(
            HttpExchange exchange
    ) {

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin",
                "*"
        );

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Methods",
                "GET, OPTIONS"
        );

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );
    }


    private static void sendResponse(
            HttpExchange exchange,
            int status,
            String response,
            String contentType
    ) throws IOException {

        byte[] bytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
                "Content-Type",
                contentType + "; charset=utf-8"
        );

        exchange.sendResponseHeaders(
                status,
                bytes.length
        );

        try (OutputStream os =
                     exchange.getResponseBody()) {

            os.write(bytes);
        }
    }
}
