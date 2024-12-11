package io.ISSProject.game.model.userManagment;

public interface Observer {
    public void onUserAdded(User user);
    public void onUserNotAdded(User user);
    public void onUserUpdated(String oldUsername , User user);
    public void onUserRemoved(User user);
    public void onUserLogged(User user);
}
