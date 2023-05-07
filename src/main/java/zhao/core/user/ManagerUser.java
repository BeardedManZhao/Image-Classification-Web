package zhao.core.user;

/**
 * 管理者用户类
 *
 * @author zhao
 */
public class ManagerUser extends OrdinaryUser {

    public ManagerUser(String name, String password) {
        super(name, password);
    }

    /**
     * @return 当前用户的权限是否具有管理者权限。
     */
    @Override
    public boolean isManager() {
        return true;
    }
}
