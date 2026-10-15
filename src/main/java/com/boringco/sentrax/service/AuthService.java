package com.boringco.sentrax.service;
import com.boringco.sentrax.dao.UserDAO;
import com.boringco.sentrax.model.AppUser;
import com.boringco.sentrax.util.PasswordUtil;
import java.util.Optional;
public class AuthService {
    private final UserDAO userDao = new UserDAO();
    public Optional<AppUser> login(String username, String password) {
        return userDao.findByUsername(username).filter(u -> PasswordUtil.verify(password, u.getPasswordHash()));
    }
}
