package utils;

/**
 * Stores shared data between API tests for Request Chaining.
 * - authToken: from POST /auth
 * - bookingId: from POST /booking
 */
public class ApiDataStore {
    private static String authToken;
    private static int bookingId;

    public static String getAuthToken() { return authToken; }
    public static void setAuthToken(String token) { authToken = token; }

    public static int getBookingId() { return bookingId; }
    public static void setBookingId(int id) { bookingId = id; }

    public static void clear() {
        authToken = null;
        bookingId = 0;
    }
}
