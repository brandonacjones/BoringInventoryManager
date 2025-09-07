package util;

public class DBUtil {

    private static final String SUPABASE_URL = System.getenv("SUPABASE_URL");
    private static final String SUPABASE_ANON_KEY = System.getenv("SUPABASE_ANON_KEY");

    public static String getSupabaseUrl() {
        return SUPABASE_URL;
    }

    public static String getSupabaseKey() {
        return SUPABASE_ANON_KEY;
    }

}
