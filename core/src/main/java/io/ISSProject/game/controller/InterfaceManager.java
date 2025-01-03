package io.ISSProject.game.controller;

import io.ISSProject.game.model.userManagment.UserManager;

public interface InterfaceManager {
    void update(UserManager userManager);
    void showErrorNotification(String errorMessage);
}
