package soc.network.search;


import common.interfaces.SearchInt;
import soc.network.User;
import soc.network.photo.Photo;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Search implements SearchInt {
    private static Set<User> usersSet = new HashSet<>();
    private static Set<Photo> photoSet = new HashSet<>();

    @Override
    public <T> boolean CheckUser(T user) {
        return usersSet.contains(user);
    }

    @Override
    public <T> boolean CheckPhoto(T photo) {
        return photoSet.contains(photo);
    }

    static {
        usersSet.add(new User("user1", 21));
        usersSet.add(new User("user2", 22));
        usersSet.add(new User("user3", 23));
        usersSet.add(new User("user4", 24));
        usersSet.add(new User("user5", 25));

        try {
            photoSet.add(new Photo(1, new URL("http://www.example.com/img1.jpg")));
            photoSet.add(new Photo(2, new URL("http://www.example.com/img2.jpg")));
            photoSet.add(new Photo(3, new URL("http://www.example.com/img3.jpg")));
            photoSet.add(new Photo(4, new URL("http://www.example.com/img4.jpg")));
            photoSet.add(new Photo(5, new URL("http://www.example.com/img5.jpg")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
