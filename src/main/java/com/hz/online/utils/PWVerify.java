package com.hz.online.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PWVerify {

    public static void main(String[] args) {
        String pw = "dfs5486<?!";
        String s = checkPwd(pw);
        if(s.equals("true")){
            System.out.println("成功"+s);
        }else
            System.out.println(s);
    }

    /***
     * 描述: 三个或者三个以上相同
     *
     * @param pwd 密码
     * @return String
     */
    public static String check3(String pwd) {

        String regx = "^.*(.)\\1{2}.*$";
        Matcher m = null;
        Pattern p = null;
        p = Pattern.compile(regx);
        m = p.matcher(pwd);
        if (m.matches()) {
            return "包含三个或者三个以上相同";
        } else {
            return "ok";

        }

    }

    /***
     * 描述: 验证生日 、身份证
     *
     * @param pwd 密码
     * @return String
     */
    public static String checkBirthday(String pwd) {
        String birthday = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";

        Pattern pattern = Pattern.compile(birthday);
        Matcher matcher = pattern.matcher(pwd);

        if (matcher.find()) {
            return "包含生日或者身份证";
        } else {
            return "ok";
        }

    }

    /***
     * 描述: 密码规则
     *
     * @param pwd 密码
     * @return String
     */
    public static String checkp(String pwd) {
        String str = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,30}$";
        if (!pwd.matches(str)) {
            return "口令至少由8位及以上大小写字母、数字及特殊字符等混合、随机组成(至少包括数字、小写字母、大写字母和特殊符号中的三种)";
        } else {
            return "ok";
        }

    }

    /***
     * 描述: 验证密码
     *
     * @param pwd 密码
     * @return String
     */
    public static String checkPwd(String pwd) {
        //密码规则
        String check = checkp(pwd);
        //键盘上连续3位或者以上
        String rsThree = validateKey(pwd);

        String repeat = checkRepeat(pwd);
        //包含生日
        String checkBirthday = checkBirthday(pwd);
        //三个或者三个以上相同
        String check3 = check3(pwd);
        //包含手机号
        String checkMobile = checkMobile(pwd);
        //包含固定电话
        String checkPhone = checkPhone(pwd);


        if (!"ok".equals(check)) {
            return check;
        } else if (!"ok".equals(rsThree)) {
            return rsThree;
        } else if (!"ok".equals(repeat)) {
            return repeat;
        } else if (!"ok".equals(checkBirthday)) {
            return checkBirthday;
        } else if (!"ok".equals(check3)) {
            return check3;
        } else if (!"ok".equals(checkMobile)) {
            return checkMobile;
        } else if (!"ok".equals(checkPhone)) {
            return checkPhone;
        } else {
            return "true";
        }

    }

    /***
     * 描述: 密码是否包含手机号
     *
     * @param sParam 密码
     * @return String
     */
    public static String checkMobile(String sParam) {

        if (sParam.length() <= 0)
            return "";
        Pattern pattern = Pattern.compile("(1|861)(3|4|5|6||7|8|9)\\d{9}$*");
        Matcher matcher = pattern.matcher(sParam);
        StringBuffer bf = new StringBuffer();
        while (matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        if (StringUtils.isNotBlank(bf.toString())) {
            return "包含手机号";
        } else {
            return "ok";
        }
    }

    /***
     * 描述: 包含固定电话
     *
     * @param content 电话
     * @return String
     */
    public static String checkPhone(String content) {
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile("\\d{3}-\\d{8}|\\d{4}-\\d{6}|\\d{7}|\\d{8}");
        Matcher matcher = p.matcher(content);
        while (matcher.find()) {
            String n = matcher.group(0).toString();
            list.add(n);
        }
        if (!list.isEmpty()) {
            return "包含固定电话";
        } else {
            return "ok";
        }
    }

    /***
     * 描述: 密码不得包含键盘上任意连续的三个字符或shift转换字符
     *
     * @param str 字符串
     * @return String
     */
    public static String validateKey(String str) {

        //定义横向穷举
        String[][] keyCode = {
                {"`~·", "1=", "2@@", "3#", "4$￥", "5%", "6^……", "7&", "8*", "9(（", "0）)", "-_", "=+"},
                {" ", "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "[{【", "]}】", "\\|、"},
                {" ", "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";:", "\'\"’“"},
                {" ", "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",《<", ".>》", "/?？"}
        };

        //找出给出的字符串，每个字符，在坐标系中的位置。
        char[] c = str.toCharArray();
        List<Integer> x = new ArrayList<Integer>();
        List<Integer> y = new ArrayList<Integer>();
        for (int i = 0; i < c.length; i++) {
            char temp = c[i];
            toHere:
            for (int j = 0; j < keyCode.length; j++) {
                for (int k = 0; k < keyCode[j].length; k++) {
                    String jk = keyCode[j][k];
                    if (jk.contains(String.valueOf(temp))) {
                        x.add(j);
                        y.add(k);
                        break toHere;
                    }
                }
            }
        }
        boolean flag = false;
        for (int i = 0; i < x.size() - 2; i++) {
            // 如果X一致，那么就是在一排
            if (x.get(i) == x.get(i + 1) && x.get(i + 1) == x.get(i + 2)) {//四者在同一行上
                if (y.get(i) > y.get(i + 2)) {
                    if (y.get(i) - 1 == y.get(i + 1) && y.get(i) - 2 == y.get(i + 2)) {
                        flag = true;
                        break;
                    }
                } else {
                    if (y.get(i) + 1 == y.get(i + 1) && y.get(i) + 2 == y.get(i + 2)) {
                        flag = true;
                        break;
                    }
                }

            } else if (x.get(i) != x.get(i + 1)
                    && x.get(i + 1) != x.get(i + 2)
                    && x.get(i) != x.get(i + 2)
            ) {//四者均不在同一行上,但是如果y相同，说明是一列
                if (y.get(i) == y.get(i + 1) && y.get(i + 1) == y.get(i + 2)) {
                    flag = true;
                    break;
                }
            }

        }
        if (flag) {
            return "不能连续三个或者三个以上字符";

        } else {
            return "ok";
        }
    }

    /**
     * 转码
     *
     * @param c 字符
     * @return 编码
     */
    public static int getUnicode(char c) {
        String returnUniCode = null;
        returnUniCode = String.valueOf((int) c);
        return Integer.parseInt(returnUniCode);
    }

    /***
     * 描述: 不能连续字符（如123、abc）连续3位或3位以上
     *
     * @param str 字符串
     * @return String
     */
    public static String checkRepeat(String str) {
        String[] arr = str.split("");
        boolean flag = false;
        for (int i = 1; i < arr.length - 1; i++) {
            int firstIndex = getUnicode(arr[i - 1].charAt(0));
            int secondIndex = getUnicode(arr[i].charAt(0));
            int thirdIndex = getUnicode(arr[i + 1].charAt(0));
            if ((thirdIndex - secondIndex == 1) && (secondIndex - firstIndex == 1)) {
                flag = true;
            }
        }
        if (flag) {
            return "不能连续3字母";

        } else {
            return "ok";
        }
    }
}
