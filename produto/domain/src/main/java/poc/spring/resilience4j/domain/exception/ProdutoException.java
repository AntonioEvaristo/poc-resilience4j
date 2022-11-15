package poc.spring.resilience4j.domain.exception;

public class ProdutoException extends Exception {
    public ProdutoException(String msg) {
        super(msg);
    }
}
