package zhao.core.user;

import zhao.Conf;
import zhao.algorithmMagic.utils.ASStr;

import java.util.HashMap;

/**
 * 普通用户数据封装类对象
 *
 * @author zhao
 */
public class OrdinaryUser implements User {

    /**
     * 默认用户对象
     */
    public final static OrdinaryUser DEFAULT_USER = new OrdinaryUser("default", "default");
    /**
     * 用户信息映射表
     */
    private final static HashMap<String, User> USER_HASH_MAP = new HashMap<>();
    private final String name, password, trainDir, modelDir;

    private OrdinaryUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.trainDir = Conf.TRAIN_DIR + '/' + name;
        this.modelDir = Conf.MODEL_DIR + '/' + name;
    }

    /**
     * @return 当前磁盘中已经创建同时与服务器进行链接的用户空间数量。
     */
    public static int getUserLen() {
        return USER_HASH_MAP.size();
    }

    /**
     * @param nameAndPassword 用户的信息 包含名称和密码。
     * @return 用户对象信息。
     */
    public static User $(String nameAndPassword) {
        final String[] strings = ASStr.splitByChar(nameAndPassword, ':');
        return $(strings[0], strings[1]);
    }

    /**
     * 从系统中获取到唯一的用户对象，如果用户存在于系统中，则不进行重复实例化操作。
     *
     * @param name     需要被获取到的用户对象名称。
     * @param password 需要被获取到的用户对象密码。
     * @return 用户对象信息。
     */
    public static User $(String name, String password) {
        final User user = USER_HASH_MAP.get(name);
        if (user != null) {
            // 当前用户已经登录过，不需要重新实例化了 在这里判断一下密码是否正确就好
            if (password.equals(user.passWord())) {
                return user;
            } else {
                // 如果不一致就直接返回default用户身份
                return DEFAULT_USER;
            }
        } else {
            // 当前用户没有登录过，需要进行实例化
            final User user1 = new OrdinaryUser(name, password);
            USER_HASH_MAP.put(name, user1);
            return user1;
        }
    }

    /**
     * @return 当前用户的权限是否具有管理者权限。
     */
    @Override
    public boolean isManager() {
        return false;
    }

    /**
     * @return 当前用户对应的昵称
     */
    @Override
    public String name() {
        return this.name;
    }

    /**
     * @return 当前用户对应的密码。
     */
    @Override
    public String passWord() {
        return this.password;
    }

    /**
     * @return 当前用户对应的训练数据存储空间目录路径。
     */
    @Override
    public String getTrainDir() {
        return this.trainDir;
    }

    /**
     * @return 当前用户对应的模型数据存储空间目录路径。
     */
    @Override
    public String getModelDir() {
        return this.modelDir;
    }

    @Override
    public String toString() {
        return this.name + ':' + this.password;
    }
}
