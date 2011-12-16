package controllers;

import java.util.List;

import models.Post;
import models.Tag;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author honnix
 */
@With(Secure.class)
public class Admin extends Controller {
    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullName);
        }
    }

    public static void index() {
        String user = Security.connected();
        List<Post> posts = Post.find("author.email", user).fetch();
        render(posts);
    }

    public static void form(Long id) {
        if (id != null) {
            Post post = Post.findById(id);
            render(post);
        }
        render();
    }

    public static void save(Long id, String title, String content, String tags) {
        Post post;

        if (id == null) {
            // Create post
            User author = User.find("byEmail", Security.connected()).first();
            post = new Post(author, title, content);
        } else {
            post = Post.findById(id);
            post.title = title;
            post.content = content;
            post.tags.clear();
        }

        // Set tags list
        for (String tag : tags.split("\\s+")) {
            if (tag.trim().length() > 0) {
                post.tags.add(Tag.findOrCreateByName(tag));
            }
        }
        // Validate
        validation.valid(post);
        if (validation.hasErrors()) {
            render("@form", post);
        }
        // Save
        post.save();
        index();
    }
}
