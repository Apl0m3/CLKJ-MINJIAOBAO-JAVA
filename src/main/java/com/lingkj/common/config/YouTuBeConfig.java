package com.lingkj.common.config;

public class YouTuBeConfig {
    public static String get_user_id_url="https://www.googleapis.com/youtube/v3/channels?access_token=#access_tokentoken&part=snippet&mine=true";
    public static String client_id="566905624934-u6n4684qplo0dvkhpojjc6jp91ptv0h9.apps.googleusercontent.com";
    public static String client_secret="LyJ-tmmFh6jp62GSHbOKrCcA";
    public static String redirect_uri="https://web.a1publicidad.eu/api/public/worthMentioning/getCode?type=3";
    public static String authorization_code="authorization_code";
    public static String get_code_url="https://accounts.google.com/o/oauth2/v2/auth?client_id=#client_id&redirect_uri=#redirect_uri&response_type=code&scope=https://www.googleapis.com/auth/youtube.readonly&include_granted_scopes=true";
    public static String get_token_url="https://www.googleapis.com/oauth2/v4/token";
}
