package gobov.roma.mvpguide.security;

import gobov.roma.mvpguide.model.User;
import lombok.Data;

@Data
public class UserContext {
    private User user;
    private boolean isAuthenticated;
}