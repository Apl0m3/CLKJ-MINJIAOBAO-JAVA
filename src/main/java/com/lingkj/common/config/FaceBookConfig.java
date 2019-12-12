package com.lingkj.common.config;

/**
 * faceBookConfig
 *
 * @author chen yongsong
 * @className faceBookConfig
 * @date 2019/9/16 14:56
 */

public class FaceBookConfig {
    /**
     * 应用编号
     */
//    public static String client_id = "应用编号";
    public static String client_id = "1415824958585011";
    /**
     * 应用秘钥
     */
//    public static String client_secret = "应用秘钥";
    public static String client_secret = "cd1ba2e6f2baca633d6e4f6473829719";
    /**
     * 表示取得的用户信息的权限范围
     */

    public static String scope = "user_about_me,email,read_stream";
    /**
     * 回调地址
     */
    public static String redirect_url = "https://web.a1publicidad.eu/api/public/worthMentioning/getCode?type=1";
    /**
     * 获取临时口令
     */
    public static String code_url = "https://www.facebook.com/v2.8/dialog/oauth";
    /**
     * 获取访问口令
     */
    public static String token_url = "https://graph.facebook.com/v2.8/oauth/access_token";
    /**
     * 获取用户信息
     */
    public static String user_url = "https://graph.facebook.com/me";
    /**
     * 验证口令
     */

    public static String verify_url = "https://graph.facebook.com/debug_token";
    /**
     * 获取应用口令
     */

    public static String app_url = "https://graph.facebook.com/v2.8/oauth/access_token";

    public static String get_code_url="https://www.facebook.com/v4.0/dialog/oauth?client_id=#client_id&redirect_uri=#redirect_uri";

}
