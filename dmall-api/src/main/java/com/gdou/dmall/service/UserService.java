package com.gdou.dmall.service;

import com.gdou.dmall.bean.User;

public interface UserService {

    int ComparisonUser(User user);

    int changePassword(User user);
}
