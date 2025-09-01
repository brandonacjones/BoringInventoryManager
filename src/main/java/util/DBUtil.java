package util;

public class DBUtil {

    private static final String SUPABASE_URL = "SUPABASE_URL_HERE";
    private static final String SUPABASE_ANON_KEY = "SUPABASE_ANON_KEY_HERE";

    public static String getSupabaseUrl() {
        return SUPABASE_URL;
    }

    public static String getSupabaseKey() {
        return SUPABASE_ANON_KEY;
    }

}
