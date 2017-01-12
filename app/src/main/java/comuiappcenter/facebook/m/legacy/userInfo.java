package comuiappcenter.facebook.m.legacy;

/**
 * Created by Administrator on 2017-01-03.
 */
public class userInfo
{
    //프리퍼런스의 이름은 "UserInfo" 이고 내부적으로 이곳에 사용자 정보가 저장됩니다.
    //2017-01-11 이 클래스를 액티비티로 바꿔서, 사용자가 자기 정보를 확인할 수 있도록 만들자.
    public static String StudentID;
    public static String Password;
    public static String NickName;

    public static String Name;
    public static String Year; //junior, senior, sophomore, freshman...
    public static String Major;
    public static String SecondMajor;

    public static String[] InterestedClass = new String[10];
    public static boolean isOnline = false;
}
