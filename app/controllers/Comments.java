package controllers;

import play.mvc.With;

/**
 * @author honnix
 */
@Check("admin")
@With(Secure.class)
public class Comments extends CRUD {
}
