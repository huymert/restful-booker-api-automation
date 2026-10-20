package utils;

/**
 * Stores shared data between API tests for Request Chaining.
 * - authToken: from POST /auth
 * - bookingId: from POST /booking
 */
public class ApiDataStore {
    private static String authToken;
    private static int bookingId;
    private static String firstname;
    private static String lastname;

    public static String getAuthToken() { return authToken; }
    public static void setAuthToken(String token) { authToken = token; }

    public static int getBookingId() { return bookingId; }
    public static void setBookingId(int id) { bookingId = id; }

    public static String getFirstname() { return firstname; }
    public static void setFirstname(String name) { firstname = name; }

    public static String getLastname() { return lastname; }
    public static void setLastname(String name) { lastname = name; }

    public static void clear() {
        authToken = null;
        bookingId = 0;
        firstname = null;
        lastname = null;
    }
}
