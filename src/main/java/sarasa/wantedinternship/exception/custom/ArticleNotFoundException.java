package sarasa.wantedinternship.exception.custom;

import jakarta.persistence.EntityNotFoundException;

public class ArticleNotFoundException extends EntityNotFoundException {

    public ArticleNotFoundException(String message) {
        super(message);
    }

}
