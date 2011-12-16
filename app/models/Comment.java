package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * @author honnix
 */
@Entity
public class Comment extends Model {
    @Required
    public String author;

    @Required
    public Date postedAt;

    @Lob
    @Required
    @MaxSize(500)
    public String content;

    @ManyToOne
    @Required
    public Post post;

    public Comment(Post post, String author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
}
