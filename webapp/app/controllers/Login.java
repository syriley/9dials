package controllers;

import models.User;
import dtos.UserDto;

public class Login extends LoggedInController{
    public static void success() {
        User user = getCurrentUser();
        UserDto dto = user.getDto(); 
        render(dto);
    }
}
