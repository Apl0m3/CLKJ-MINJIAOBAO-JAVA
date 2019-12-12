package com.lingkj.common.utils;

import com.lingkj.config.SystemParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * RunShellUtil
 *
 * @author chen yongsong
 * @className RunShellUtil
 * @date 2019/8/29 11:19
 */

public class RunShellUtil {

    public static void main(String[] args) {
        runShell();
    }

    public static void runShell() {
        try {
            String shellPath = SystemParams.shellPath;
            System.out.println(shellPath);
            Process ps = Runtime.getRuntime().exec(shellPath);
            ps.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
