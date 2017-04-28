package com.ls.drupalcon.model.managers;

import com.ls.drupalcon.model.Model;
import com.ls.drupalcon.model.PreferencesManager;
import com.ls.drupalcon.model.dao.EventDao;
import com.ls.drupalcon.model.dao.FriendsFavoriteDao;
import com.ls.drupalcon.model.data.Event;
import com.ls.drupalcon.model.data.FriendsFavoriteItem;
import com.ls.utils.L;

import java.util.ArrayList;
import java.util.List;

public class FriendsFavoriteManager {

    private FriendsFavoriteDao mFriendsDao;

    public FriendsFavoriteManager() {
        mFriendsDao = new FriendsFavoriteDao();
    }

    public List<FriendsFavoriteItem> getAllFriendsFavorite() {
        List<FriendsFavoriteItem> allSafe = mFriendsDao.getAllSafe();
        return allSafe;
    }

    public ArrayList<Long> getFavoriteEventIds() {

        ArrayList<Long> favoriteEventIds = new ArrayList<>();
        for (FriendsFavoriteItem favorite : getAllFriendsFavorite()) {
            if (favorite.getSharedScheduleCode() == (Model.instance().getSharedScheduleManager().getCurrentScheduleId()))
                favoriteEventIds.add(favorite.getEventId());
        }
        L.e("getFavoriteEventIds = " + favoriteEventIds);
        return favoriteEventIds;
    }

    public ArrayList<Long> getMyEventIds() {

        ArrayList<Long> favoriteEventIds = new ArrayList<>();
        for (FriendsFavoriteItem favorite : getAllFriendsFavorite()) {
            if (favorite.getSharedScheduleCode() == (PreferencesManager.getInstance().getMyScheduleCode()))
                favoriteEventIds.add(favorite.getEventId());
        }
        L.e("getMyEventIds = " + favoriteEventIds);
        return favoriteEventIds;
    }

    public List<Event> getAllFriendsFavoriteEvent() {
        EventManager eventManager = Model.instance().getEventManager();
        EventDao eventDao = eventManager.getEventDao();
        List<Event> events = eventDao.selectEventsByIdsSafe(getFavoriteEventIds());
        return events;
    }

    public void saveFavorite(long id) {
        SharedScheduleManager sharedScheduleManager = Model.instance().getSharedScheduleManager();
        long currentScheduleId = sharedScheduleManager.getCurrentScheduleId();
        mFriendsDao.saveDataSafe(new FriendsFavoriteItem(id, currentScheduleId));
    }

    public void saveFavoritesSafe(ArrayList<FriendsFavoriteItem> items) {
        mFriendsDao.saveDataSafe(items);
    }

    public void saveFavoriteSafe(FriendsFavoriteItem item) {
        L.e("saveFavoriteSafe = " + item);
        mFriendsDao.saveDataSafe(item);
    }


    public List<FriendsFavoriteItem> getAllFavoritesSafe() {
        return mFriendsDao.getAllSafe();
    }

//    public FriendsFavoriteDao getFriendsDao() {
//        return mFriendsDao;
//    }
}
